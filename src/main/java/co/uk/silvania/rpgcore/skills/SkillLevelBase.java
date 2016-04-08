package co.uk.silvania.rpgcore.skills;

import java.util.ArrayList;
import java.util.List;

import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.RegisterSkill;
import co.uk.silvania.rpgcore.network.EquippedSkillsPacket;
import co.uk.silvania.rpgcore.network.LevelPacket;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class SkillLevelBase {
	
	public float xp;
	public int level;
	public ResourceLocation skillIcon;
	public int iconX;
	public int iconZ;
	public String skillName = "";
	public String skillId;
	public boolean hasGui;
	public double levelMultiplier = 1.0; //Higher value = slower levelling.
	
	public int unlockedLevel;
	public int levelCap = -1;
	
	public ArrayList<String> incompatibleSkills = new ArrayList<String>();
	public ArrayList<String> requiredSkills = new ArrayList<String>();
	
	public int firstReqSkillLevel = -1;
	public int secondReqSkillLevel = -1;
	public int thirdReqSkillLevel = -1;
	
	public String nameFormat = "";
	
	public List description = new ArrayList();
	
	public boolean canGainXP = true;
	
	public SkillLevelBase() {
		this.xp = 0;
	}
	
	public void addXP(float xpAdd, EntityPlayer player) {
		if (canGainXP) {
			EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get(player);
			//We check on your behalf to make sure the skill is equipped before allowing the XP to be added.
			if (isSkillEquipped(player, skillId)) {
				//We'll also make sure they've not equipped armour into the slot shared by a skill
				if (!skillArmourConflict(equippedSkills, skillId, player)) {
					xp += xpAdd;
	
					//Every time a skill gains XP, the global level also gets 10% of that XP.
					GlobalLevel glevel = (GlobalLevel) GlobalLevel.get(player);
					glevel.xpGlobal += (xpAdd/10.0);
				}
			}
		}
	}
	
	public boolean isSkillEquipped(EntityPlayer player, String skillId) {
		EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get(player);
		if (equippedSkills.isSkillEquipped(skillId)) {
			return true;
		}
		return false;
	}
	
	public void forceAddXP(int xpAdd) {
		xp += xpAdd;
	}
	
	public static String addXPToSkill(int xpAdd, EntityPlayer player, String skillId) {
		SkillLevelBase skill = getSkillFromId(skillId, player);
		if (skill != null) {
			skill.forceAddXP(xpAdd);
			RPGCore.network.sendTo(new LevelPacket(skill.getXP(), skill.skillId), (EntityPlayerMP) player);
			return "\u00A7aAdded " + xpAdd + " xp to " + skill.skillName;
		} else if (skillId.equalsIgnoreCase("globalLevel")) {
			GlobalLevel glevel = (GlobalLevel) GlobalLevel.get(player);
			glevel.xpGlobal += xpAdd;
			RPGCore.network.sendTo(new LevelPacket((int)(glevel.getXPGlobal()*10), glevel.skillId), (EntityPlayerMP) player);
			return "\u00A7aAdded " + xpAdd + " xp to global level.";
		}
		return "\u00A7cFailed to add xp!";
	}	
	
	public static SkillLevelBase getSkillFromId(String skillId, EntityPlayer player) {
		for (int i = 0; i < RegisterSkill.skillList.size(); i++) {
			SkillLevelBase skillBase = RegisterSkill.skillList.get(i);
			SkillLevelBase skill = (SkillLevelBase) skillBase.get(player, skillId);
			if (skill != null && skill.skillId.equals(skillId)) {
				return skill;
			}
		}
		return null;
	}
	
	public boolean skillArmourConflict(EquippedSkills equippedSkills, String skillId, EntityPlayer player) {
		int slot = equippedSkills.findSkillSlot(skillId);
		boolean removedSkill = false;
		
		if (slot == 4 && player.inventory.armorItemInSlot(0) != null) { equippedSkills.skillId4 = ""; removedSkill = true; }
		if (slot == 5 && player.inventory.armorItemInSlot(0) != null) { equippedSkills.skillId5 = ""; removedSkill = true; }
		if (slot == 7 && player.inventory.armorItemInSlot(0) != null) { equippedSkills.skillId7 = ""; removedSkill = true; }
		if (slot == 8 && player.inventory.armorItemInSlot(0) != null) { equippedSkills.skillId8 = ""; removedSkill = true; }
		
		if (removedSkill) {
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
	
	public float getXP() {
		return xp;
	}
	
	public String getXPForPrint() {
		return (int) getXP() + " / " + getXpForLevel(getLevel()+1);
	}
	
	public int getLevel() {
		int base = 83;
		int previousXp = 83;
		int level = 1;
		
		while (xp >= previousXp) {
			//Level curve algorithm
			//Takes the base value (83; XP required to reach level 2)
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
			previousXp += base + ((base / 100.0) * ((level*levelMultiplier) * (35 + ((level/10)*10))));
			level++;
		}
		if (level >= levelCap && levelCap > 0) {
			return levelCap;
		}
		return level;
		
	}
	
	public int getXpForLevel(int level) {
		int base = 83;
		int xpForLevel = 83;
		
		for (int i = 1; i < level; i++) {
			xpForLevel += base + ((base / 100.0) * ((i*levelMultiplier) * (35 + ((i/10)*10))));
		}
		return xpForLevel;
	}
	
	public float xpToNextLevel() {		
		return (getXpForLevel(getLevel())) - getXP();
	}
	
	public void setLevel() {
		level = (int) Math.round(levelMultiplier * Math.sqrt(xp));
		System.out.println("Level: " + level);
	}
	
	public void setXP(float xpSet) {
		xp = xpSet;
	}
	
	public static IExtendedEntityProperties get(EntityPlayer player, String skillId) {
		return player.getExtendedProperties(skillId);
	}
	
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		((SkillLevelBase) SkillLevelBase.get(event.entityPlayer, skillId)).copy((SkillLevelBase) SkillLevelBase.get(event.original, skillId));
	}
	
	public void copy(SkillLevelBase properties) {
		xp = properties.xp;
	}
	
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
	
	public boolean isSkillUnlocked(EntityPlayer player) {
		GlobalLevel glevel = (GlobalLevel) GlobalLevel.get(player);
		if (glevel.getLevel() >= unlockedLevel) {
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
	
	public boolean canSkillBeEquipped(EntityPlayer player) {
		if (!isSkillUnlocked(player)) {
			return false;
		}
		
		if (hasUnequippedRequirements(player)) {
			return false;
		}
		
		return true;
	}
	
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
	
	public void openGui() {}
}
