package co.uk.silvania.rpgcore.skills;

import java.util.ArrayList;
import java.util.List;

import co.uk.silvania.rpgcore.HandlerOfEvents;
import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.RPGCoreConfig;
import co.uk.silvania.rpgcore.RegisterSkill;
import co.uk.silvania.rpgcore.network.EquippedSkillsPacket;
import co.uk.silvania.rpgcore.network.LevelPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public abstract class SkillLevelBase {
	
	public float xp;
	public int level;
	
	HandlerOfEvents hoe = new HandlerOfEvents();

	public static String staticId;
	public String skillId;
	
	public ArrayList<String> incompatibleSkills = new ArrayList<String>();
	public ArrayList<String> requiredSkills = new ArrayList<String>();
	
	public ArrayList<String> equipIssues = new ArrayList<String>();
	
	public int firstReqSkillLevel = -1;
	public int secondReqSkillLevel = -1;
	public int thirdReqSkillLevel = -1;
	
	public List description = new ArrayList();
	
	RPGCoreConfig config = new RPGCoreConfig();
	
	
	
	public SkillLevelBase(String skillID) {
		this.xp = 0;
		this.skillId = skillID;
		this.staticId = skillID;
		addRequirements();
		addIncompatibilities();
		addDescription();
		addEquipIssues();
	}
	
	/**
	 * Add's XP to the skill. Checks if cainGainXP() is true, the skill is equipped, and that it's not conflicting with armour before adding the XP.
	 * Doesn't notify the client; if needed use addXPWithUpdate for that (But only if you need to!)
	 * @param xpAdd
	 * @param player
	 */
	public void addXP(float xpAdd, EntityPlayer player) {
		if (canGainXP() && !player.capabilities.isCreativeMode) {
			EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get(player);
			//We check on your behalf to make sure the skill is equipped before allowing the XP to be added.
			if (isSkillEquipped(player, skillId)) {
				//We'll also make sure they've not equipped armour into the slot shared by a skill
				if (!skillArmourConflict(equippedSkills, skillId, player)) {
					if ((xpAdd+getXP()) >= getXpForLevel(getLevel())) { levelUp(player, xpAdd); }
					
					xp += xpAdd;
					
					hoe.setXpForRender(xpAdd);					
					
					//Every time a skill gains XP, the global level also gets 10% of that XP.
					GlobalLevel glevel = (GlobalLevel) GlobalLevel.get(player);
					glevel.xpGlobal += (xpAdd/10.0);
					
					xpGained(skillId, xpAdd, player);
				}
			}
		}
	}
	
	/**
	 * Add XP, and send a packet to the client telling them XP was added.
	 * Use for any event checks etc where the client doesn't know (eg generic Block Breaking)
	 * @param xpAdd
	 * @param player
	 */
	public void addXPWithUpdate(float xpAdd, EntityPlayer player) {
		addXP(xpAdd, player);
		if (!player.worldObj.isRemote) {
			RPGCore.network.sendTo(new LevelPacket(getXP(), -1, skillId), (EntityPlayerMP) player);
		}
	}
	
	/**
	 * Called when the player has levelled up. Do anything you want upon levelling up here (eg notifying of unlocks.)
	 */
	public void levelUp(EntityPlayer player, float xpAdd) {
		if (!player.worldObj.isRemote) {
			player.addChatComponentMessage(new ChatComponentText(nameFormat() + "Level up! " + skillName() + " is now level " + getLevelFromXP(getXP() + xpAdd)));
		}
	}
	
	/**
	 * Forces a levelUp by force-adding the amount of XP remaining to the next level.
	 */
	public void forceLevelUp(EntityPlayer player) {
		forceAddXP(xpToNextLevel()+1, player);
	}
	
	/**
	 * Checks if the skill is currently equipped.
	 * @param player
	 * @param skillId
	 * @return true if equipped, false if not.
	 */
	public boolean isSkillEquipped(EntityPlayer player, String skillId) {
		EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get(player);
		if (equippedSkills.isSkillEquipped(skillId)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Force XP adding, even if the skill is not equipped or even unlocked.
	 * @param xpAdd
	 */
	public void forceAddXP(float xpAdd, EntityPlayer player) {
		System.out.println("force-add XP");
		System.out.println("xpAdd: " + xpAdd + ", xp: " + getXP() + " (" + (xpAdd+getXP()) + ") ? >= "  + getXpForLevel(getLevel()));
		if ((xpAdd+getXP()) >= getXpForLevel(getLevel())) {
			System.out.println("Player levelled up!");
			levelUp(player, xpAdd);
		} else {
			System.out.println("xpAdd: " + xpAdd + ", xp: " + xp + ", getXpForLevel: " + getXpForLevel(getLevel()));
		}
		xp += xpAdd;
	}
	
	/**
	 * Add XP to the specified skill. Forces addition even if skill isn't equipped/unlocked.
	 * Used by command currently; could be good for quest rewards etc.
	 * @param xpAdd
	 * @param player
	 * @param skillId
	 * @return String to be shown to player, if applicable, confirming whether or not XP was added.
	 */
	public static String addXPToSkill(int xpAdd, EntityPlayer player, String skillId) {
		SkillLevelBase skill = getSkillByID(skillId, player);
		if (skill != null) {
			skill.forceAddXP(xpAdd, player);
			RPGCore.network.sendTo(new LevelPacket(skill.getXP(), -1, skill.skillId), (EntityPlayerMP) player);
			return "\u00A7aAdded " + xpAdd + " xp to " + skill.skillName();
		} else if (skillId.equalsIgnoreCase("globalLevel")) {
			GlobalLevel glevel = (GlobalLevel) GlobalLevel.get(player);
			glevel.forceAddXP(xpAdd, player);
			RPGCore.network.sendTo(new LevelPacket((int)(glevel.getXPGlobal()*10), glevel.getSkillPoints(), glevel.skillId), (EntityPlayerMP) player);
			return "\u00A7aAdded " + xpAdd + " xp to global level.";
		}
		return "\u00A7cFailed to add xp!";
	}
	
	/**
	 * Checks if there is a registered skill with the provided skillId
	 * @param skillId
	 * @param player
	 * @return the SkillLevelBase of the skill, or null.
	 */
	public static SkillLevelBase getSkillByID(String skillId, EntityPlayer player) {
		for (int i = 0; i < RegisterSkill.skillList.size(); i++) {
			SkillLevelBase skillBase = RegisterSkill.skillList.get(i);
			SkillLevelBase skill = (SkillLevelBase) skillBase.get(player, skillBase.skillId);
			
			if (skill != null && skill.skillId.equals(skillId)) {
				return skill;
			}
		}
		return null;
	}
	


	/**
	 * Checks if equipped skill is in a slot shared with armour. Called in various places, as there's no "onArmourEquipped" event.
	 * If there's a conflict, the skill is removed instantly.
	 * @param equippedSkills
	 * @param skillId
	 * @param player
	 * @return true if there's a conflict and it was resolved. False otherwise.
	 */
	public boolean skillArmourConflict(EquippedSkills equippedSkills, String skillId, EntityPlayer player) {
		int slot = equippedSkills.findSkillSlot(skillId);
		boolean removedSkill = false;
		
		if (slot == 4 && player.inventory.armorItemInSlot(0) != null) { equippedSkills.skillId4 = ""; removedSkill = true; }
		if (slot == 5 && player.inventory.armorItemInSlot(0) != null) { equippedSkills.skillId5 = ""; removedSkill = true; }
		if (slot == 7 && player.inventory.armorItemInSlot(0) != null) { equippedSkills.skillId7 = ""; removedSkill = true; }
		if (slot == 8 && player.inventory.armorItemInSlot(0) != null) { equippedSkills.skillId8 = ""; removedSkill = true; }
		
		if (removedSkill && !player.worldObj.isRemote) {
			System.out.println("Armour and skill slot conflict! Removing skill and telling client..");
			RPGCore.network.sendTo(new EquippedSkillsPacket(
				equippedSkills.getSkillInSlot(0), 
				equippedSkills.getSkillInSlot(1), 
				equippedSkills.getSkillInSlot(2), 
				equippedSkills.getSkillInSlot(3), 
				equippedSkills.getSkillInSlot(4), 
				equippedSkills.getSkillInSlot(5), 
				equippedSkills.getSkillInSlot(6),
				equippedSkills.getSkillInSlot(7), 
				equippedSkills.getSkillInSlot(8), 
				equippedSkills.getSkillInSlot(9), 
				equippedSkills.getSkillInSlot(10), 
				equippedSkills.getSkillInSlot(11)), (EntityPlayerMP) player);
		}
		
		return removedSkill;
	}
	
	/**
	 * @return how much XP this skill has.
	 */
	public float getXP() {
		return xp;
	}
	
	/** 
	 * Gets an integer version of XP (Rounded down) as a fraction of the XP for the next level.
	 * Used anywhere you want to show XP relative to next level.
	 * @return string with XP/XP for next level
	 */
	public String getXPTotalForPrint() {
		return getXPProgressForPrint() + " (" + (int) getXP() + ")";
	}
	
	/** 
	 * Same as getXPForPrint, but shows it relevant to the current level/next level instead of total XP's
	 * @return string with XP/XP for next level
	 */
	public String getXPProgressForPrint() {
		return (int) (getXP() - getXpForLevel(getLevel()-1)) + "/" + (getXpForLevel(getLevel()) - getXpForLevel(getLevel()-1));
	}
	
	/**
	 * Gets a percentage of the skills progression to next level.
	 * @return percentage xp gained
	 */
	public String getXPTotalAsPercentage() {
		return (int) Math.round((getXP()/getXpForLevel(getLevel()+1))*100) + "%";
	}
	
	/**
	 * Same as getXPAsPercentage, but relevant only to current level/next level instead of total XP's
	 * @return percentage xp gained
	 */
	public String getXPProgressAsPercentage() {
		return (int) Math.round((getXP() - getXpForLevel(getLevel()-1))/(getXpForLevel(getLevel()) - getXpForLevel(getLevel()-1))*100) + "%";
	}
	
	/**
	 * Calculates a level based on how much XP the player has.
	 * @return level, as an integer
	 */
	public int getLevel() {
		return getLevelFromXP(getXP());
	}
	
	public int getLevelFromXP(float xp) {
		int base = config.baseXp;
		int previousXp = config.baseXp;
		int level = 1;
		
		while (xp >= previousXp) {
			//Level curve algorithm
			//Takes the base value (83 by default; XP required to reach level 2)
			//Then adds a percentage based on the skill multiplier and their current level.
			//The last part (level/10*10) plays well with the fact it's an integer.
			//Dividing int by int will only give a whole number; so 1-10 / 10 will always give 0,
			// 11-20 / 10 = 1, 21-30/10 = 2 and so on.
			//So, for every level you go up, it gets a little harder - but every 10 levels it jumps an extra bit.
			//levelMultiplier can be set by each skill to adjust the rate it increases. Higher numbers are slower.
			//I personally recommend 2.0, it works well - but it's up to the developer.
			//For comparison, 50,000 XP at 1.0 gives level 43 (almost 44), and at 2.0 gives mid-39.
			//Printout of levels up to above 50k XP at 1.0 and 2.0: http://pastebin.com/LxrtsLAf
			//
			//Of course you can use any number from 0.0001 to Float.MAX_VALUE - but the latter will be near impossible to level.
			//Finally, I recommend developers make the value configurable. Servers have varying difficulty, and admins may want to tweak it to match.
			previousXp += base + ((base / 100.0) * ((level*levelMultiplier()) * (35 + ((level/10)*10))));
			level++;
		}
		if (level >= levelCap() && levelCap() > 0) {
			return levelCap();
		}
		return level;
	}
	
	/**
	 * @param level
	 * @return how much XP is required for the given level.
	 */
	public int getXpForLevel(int level) {
		if (level < 1) {
			return 0;
		}
		int base = config.baseXp;
		int xpForLevel = config.baseXp;
		
		for (int i = 1; i < level; i++) {
			xpForLevel += base + ((base / 100.0) * ((i*levelMultiplier()) * (35 + ((i/10)*10))));
		}
		
		return xpForLevel;
	}
	
	/**
	 * @return how much experience to the next level-up.
	 */
	public float xpToNextLevel() {		
		return (getXpForLevel(getLevel())) - getXP();
	}
	
	/**
	 * Sets the experience to the given amount, regardless of what it currently is.
	 * Should very rarely be used, if ever - maybe only for resetting skills to zero. 
	 * @param xpSet
	 */
	public void setXP(float xpSet) {
		xp = xpSet;
	}
	
	/**
	 * Gets the IEEP of the provided skillId. Used to get the actual skill you're working with, instead of just SkillLevelBase.
	 * @param player
	 * @param skillId
	 * @return
	 */
	public static IExtendedEntityProperties get(EntityPlayer player, String skillId) {
		return player.getExtendedProperties(skillId);
	}
	
	/**
	 * Clones the skill's properties, used on player death etc to persist skills through death.
	 * Should be overriden if you save anything more than XP in your skill (eg cooldowns)
	 * @param properties
	 *//*
	public void copy(SkillLevelBase properties) {
		xp = properties.xp;
	}/*
	
	/** 
	 * Checks if the skill is unlocked, based off the global level.
	 * If you want custom skill requirements, override skillUnlocked() instead!
	 * @param player
	 * @return
	 */
	public boolean isSkillUnlocked(EntityPlayer player) {
		GlobalLevel glevel = (GlobalLevel) GlobalLevel.get(player);
		if (glevel.getLevel() >= unlockedLevel() && skillUnlocked()) {
			for (int i = 0; i < requiredSkills.size(); i++) {
				String requiredSkillId = requiredSkills.get(i);
				SkillLevelBase skill = (SkillLevelBase) SkillLevelBase.get(player, requiredSkillId);
				EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get(player);
				
				//Check first three listed skills against required levels. 
				if (i == 0) { if (skill.getLevel() < firstReqSkillLevel) { return false; }}
				if (i == 1) { if (skill.getLevel() < secondReqSkillLevel) { return false; }}
				if (i == 2) { if (skill.getLevel() < thirdReqSkillLevel) { return false; }}
			}
		} else {
			return false;
		}
		
		return true;
	}
	
	/**
	 * True if skill can be equipped, false if not.
	 * Remember to call super if you override, else it won't check for skill requirements or unlock level.
	 * Use addEquipIssues() to show on the skill list tooltip WHY they can't equip the skill - if you want.
	 * You could always keep it a secret!
	 * @param player
	 * @return
	 */
	public boolean canSkillBeEquipped(EntityPlayer player) {
		if (!isSkillUnlocked(player) || hasUnequippedRequirements(player)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Check if the skill has requirements which the player doesn't have.
	 * Doesn't tell you WHAT those requirements are, just that they're a thing.
	 * @param player
	 * @return true if they have unequipped requirements, false if everything is OK.
	 */
	public boolean hasUnequippedRequirements(EntityPlayer player) {
		for (int i = 0; i < requiredSkills.size(); i++) {
			String requiredSkillId = requiredSkills.get(i);
			SkillLevelBase skill = (SkillLevelBase) SkillLevelBase.get(player, requiredSkillId);
			EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get(player);
			if (!equippedSkills.isSkillEquipped(requiredSkillId)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks the skill is compatible with all currently equipped skills.
	 * @param player
	 * @return true if compatible, false if not.
	 */
	public boolean isSkillCompatable(EntityPlayer player) {
		for (int i = 0; i < incompatibleSkills.size(); i++) {
			String requiredSkillId = incompatibleSkills.get(i);
			SkillLevelBase skill = (SkillLevelBase) SkillLevelBase.get(player, requiredSkillId);
			EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get(player);
			if (equippedSkills.isSkillEquipped(requiredSkillId)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Get the colour of the XP bar, as an RGBA integer (Same as used in fontRender.drawString. 
	 * RPGCore only- not used for MCSAO. 
	 * https://www.shodor.org/stella2java/rgbint.html can give ints from RGB values.
	 * @return
	 */
	public int xpBarColour() {
		return 25600;
	}
	
	/**
	 * Should this skill be totally hidden from the skill list until it's unlocked?
	 * (EG Kirito's Dual Blades skill from SAO)
	 * @return whether or not skill is hidden.
	 */
	public boolean secretSkill() {
		return false;
	}
	
    /**
     * Is this skill unlocked? If this is true and unlockedLevel > 1, it will still be locked until global level target is reached.
     * Set to false to use your own methods to unlock the skill. Make sure you use an if/else here, so once your criteria are met it returns true.
     * @return true for unlocked/use global level, false for custom implementation.
     */
	public boolean skillUnlocked() {
		return true;
	}
	
	/**
     * Should this skill have a Global Level target? Returning anything above 2 here will make this a requirement to unlock the skill, 
     * regardless of "skillUnlocked". Set to 1 or below to effectively disable (as globalLevel will always be > 1)
     * @return level at which skill is unlocked.
     */
	public int unlockedLevel() {
		return -1;
	}
	
	/**
     * Does this Skill have a custom GUI screen, for unlocks, details etc?
     * @return Whether or not the skill has a GUI
     */
	public abstract boolean hasGui();
	
	/**
	 * Can be localized, configurable etc if you want. Unless you do something bad like use it in NBT, this is only used for display.
     * @return The name of your skill.
     */
	public abstract String skillName();
	
	/**
	 * Shorthand version of skill name, for example STR instead of Strength. Used in places that size matters, such as XP bars (If selected to)
	 * @return
	 */
	public abstract String shortName();
	
	/**
	 * Any text formatting you want to be applied to your skill's name. Not always used.
	 * Kept separate so you can choose whether or not to allow users to change it.
	 * Formatting can be applied with EnumChatFormatting, or simply a string: "\u00A7#"
	 * the # should be a number 0-9 or letter a-f for colour, or k-r for formatting
	 * see http://minecraft.gamepedia.com/Formatting_codes for details.
     * @return Chat formatting for skill name
     */
	public String nameFormat() {
		return "";
	}
	
	/**
     * Open the GUI, if you have one.
     */
	public abstract void openGui();
	
	/**
     * Add skills that are required to be equipped (and therefore by extension, unlocked) in order to use this skill.
     * Skills are added using requiredSkills.add("skillId");
     * Invalid/not found skills are ignored. Leave empty for no requirements.
     * 
     * You can also require a level for the first three skills you list here.
     * Give a value to the three following ints and it will match those to the skills in the listed order, and require them to be that level.
     * firstReqSkillLevel
     * secondReqSkillLevel
     * thirdReqSkillLevel
     */
	public void addRequirements() {}
	
	/**
     * Add skills that are incompatible with this skill.
     * Skills are added using incompatibleSkills.add("skillId");
     * Invalid/not found skills are ignored. Leave empty for compatability with all skills.
     * Equipping this skill will automatically remove anything on this list.
     */
	public void addIncompatibilities() {}
	
	/**
	 * Add a SHORT description of your skill; try and keep it to 4-5 lines plus the title.
	 * For uniformity, the first line should be:
	 * description.add(nameFormat() + "\u00A7l" + skillName());
	 * After that, description.add("string"); will add a new line. Don't make them too long or it'll look ugly in the GUIs.
	 * Rendered as a tooltip when hovering over icons in the Skill Selection gui.
	 */
	public abstract void addDescription();
	
	/**
	 * Add text to describe any non-standard reasons a skill may be locked, for example it requires a quest to be completed.
	 * Add new entries using equipIssues.add("");
	 */
	public void addEquipIssues() {}
	
	/**
     * Adjust the speed in which this skill levels up.
     * Higher values mean larger XP requirements for each level.
     * I suggest 1.0 as a good default.
     * @return the multiplier for levelling speed.
     */
	public double levelMultiplier() {
		return 1.0;
	}
	
	/**
	 * Whether or not the player can ever gain XP using this skill. More used for formatting, but does get checked for XP gain too.
	 * Unless your skill is literally just a single-use skill with no progression, you almost always want this to be true.
	 * @return whether or not this skill can accumulate XP and level up
	 */
	public boolean canGainXP() {
		return true;
	}
	
	/**
     * Prevent gaining levels once this number is reached. XP will still be gained, unless you implictely block it.
     * Optional, set to -1 to allow unlimited levels (up to Integer.MAX_VALUE - which no one will sanely reach without cheating.)
     * @return the maximum attainable level (or -1 for unlimited)
     */
	public int levelCap() {
		return -1;
	}
	
	/**
	 * Called when XP is gained on -ANY- equipped skill!
	 * Useful to check if a required skill gained XP, or maybe if you want a global addition where this skill gains XP when anything else does.
	 * @param skillId, xp added, player
	 */
	public void xpGained(String skillId, float xpAdd, EntityPlayer player) {}
	
	/**
     * The resource location of the file with your icons in. File should be 256x256 (or a double of).
     * You should have two icons; one 30x30 between Y0-127, and another 15x15 exactly 128 pixels below it.
     * Return null to use the "Missing Icon" textuers; a purple/black grid with a ?. It's very ugly, stop being lazy, make an icon! :D
     * @return the ResourceLocation of your icons
     */
	public abstract ResourceLocation skillIcon();
	
	/**
	 *  What happens when you activate the skill? Define it here! Providing player and world currently, may provide others if needed.
	 *  You can also call this yourself if you want to, else it's handled by the skill activation system (not yet made).
	 */
	public abstract void activateSkill(EntityPlayer player, World world);
	
	/**
     * @return the X position of the top-left of your 30x30 icon
     */
	public abstract int iconX();
	
	/**
     * @return the Y position of the top-left of your 30x30 icon
     */
	public abstract int iconZ();
}
