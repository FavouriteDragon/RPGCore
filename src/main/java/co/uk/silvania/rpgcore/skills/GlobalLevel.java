package co.uk.silvania.rpgcore.skills;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

public class GlobalLevel extends SkillLevelBase implements IExtendedEntityProperties {
	
	public static String staticSkillId;
	
	public String skillName;
	public String skillId;
	
	public float xpGlobal;
	public int skillPoints;
	
	public GlobalLevel(EntityPlayer player, String skillID) {
		super(skillID);
		skillId = skillID;
		staticSkillId = skillID;
		this.xpGlobal = 0;
		this.skillPoints = 0;
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat(skillId + "xp", xpGlobal);
		nbt.setInteger("skillPoints", skillPoints);
		compound.setTag(skillId, nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = (NBTTagCompound) compound.getTag(skillId);
		xpGlobal = nbt.getFloat(skillId + "xp");
		skillPoints = nbt.getInteger("skillPoints");
	}
	
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(GlobalLevel.staticSkillId, new GlobalLevel(player, staticSkillId));
	}

	@Override public void init(Entity entity, World world) {}
	
	public String getXPTotalForPrint() {
		return getXPProgressForPrint() + " (" + (int) getXPGlobal() + ")";
	}

	public String getXPProgressForPrint() {
		return (int) (getXPGlobal() - getXpForLevel(getLevel()-1)) + "/" + (getXpForLevel(getLevel()) - getXpForLevel(getLevel()-1));
	}
	
	public int slotUnlockedLevel(int slot) {
		if ((slot >= 3 && slot <= 9)) {
			return -1;
		}
		
		if (slot == 0)  { return 10; }
		if (slot == 1)  { return 20; }
		if (slot == 11) { return 30; }
		if (slot == 2)  { return 40; }
		if (slot == 10) { return 50; }
		
		return 999;
	}
	
	public int getLevel() {
		int base = config.baseXp;
		int previousXp = config.baseXp;
		int level = 1;
		
		while (xpGlobal >= previousXp) {
			previousXp += base + ((base / 100.0) * ((level*5) * (35 + ((level/10)*10))));
			level++;
		}
		return level;
		
	}
	
	public int getXpForLevel(int level) {
		if (level < 1) {
			return 0;
		}
		int base = config.baseXp;
		int xpForLevel = config.baseXp;
		
		for (int i = 1; i < level; i++) {
			xpForLevel += base + ((base / 100.0) * ((i*5) * (35 + ((i/10)*10))));
		}
		return xpForLevel;
	}
	
	public float xpToNextLevel() {		
		return (getXpForLevel(getLevel())) - (int)getXPGlobal();
	}
	
	public void levelUpGlobal(EntityPlayer player, float xpAdd) {
		ChatStyle cht = new ChatStyle();
		player.addChatMessage(new ChatComponentText("Level up! Your Global Level is now " + getLevelFromXP(getXPGlobal() + xpAdd)).setChatStyle(cht.setColor(EnumChatFormatting.GOLD)));
		player.addChatMessage(new ChatComponentText("You currently have " + (getSkillPoints()+config.skillPointsPerLevel) + " Skill Points available.").setChatStyle(cht.setColor(EnumChatFormatting.GOLD)));
		System.out.println("Level up! " + skillName() + " is now level " + getLevel());
		skillPoints += config.skillPointsPerLevel;
	}

	public void setXP(float xpSet) {
		xpGlobal = xpSet;
	}
	
	public float getXPGlobal() {
		return xpGlobal;
	}
	
	public void setSkillPoints(int points) {
		skillPoints = points;
	}
	
	public int getSkillPoints() {
		return skillPoints;
	}
	
	@Override
	public void forceAddXP(float xpAdd, EntityPlayer player) {
		if ((xpAdd+getXPGlobal()) >= getXpForLevel(getLevel())) {
			levelUpGlobal(player, xpAdd);
		}
		xpGlobal += xpAdd;
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties(skillId, new GlobalLevel((EntityPlayer)event.entity, skillId));
		}
	}
	
	public static IExtendedEntityProperties get(EntityPlayer player) {
		return player.getExtendedProperties(staticSkillId);
	}

	@Override
	public boolean hasGui() {
		return false;
	}

	@Override
	public String skillName() {
		return "Global Level";
	}

	@Override public void openGui() {}

	@Override public void addDescription() {}

	@Override
	public ResourceLocation skillIcon() {
		return null;
	}

	@Override public void activateSkill(EntityPlayer player, World world) {}

	@Override
	public int iconX() {
		return 0;
	}

	@Override
	public int iconZ() {
		return 0;
	}

	@Override
	public String shortName() {
		return "Global";
	}
}
