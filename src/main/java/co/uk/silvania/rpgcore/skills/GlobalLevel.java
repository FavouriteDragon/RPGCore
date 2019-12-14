package co.uk.silvania.rpgcore.skills;

import co.uk.silvania.rpgcore.RPGCoreConfig;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GlobalLevel extends SkillLevelBase implements ICapabilitySerializable<NBTTagCompound> {

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

	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(GlobalLevel.staticSkillId, new GlobalLevel(player, staticSkillId));
	}

	public static IExtendedEntityProperties get(EntityPlayer player) {
		return player.getExtendedProperties(staticSkillId);
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

	@Override
	public void init(Entity entity, World world) {
	}

	public String getXPTotalForPrint() {
		return getXPProgressForPrint() + " (" + (int) getXPGlobal() + ")";
	}

	public String getXPProgressForPrint() {
		return (int) (getXPGlobal() - getXpForLevel(getLevel() - 1)) + "/" + (getXpForLevel(getLevel()) - getXpForLevel(getLevel() - 1));
	}

	public int slotUnlockedLevel(int slot) {
		if ((slot >= 3 && slot <= 9)) {
			return -1;
		}

		if (slot == 0) {
			return 3;
		}
		if (slot == 1) {
			return 10;
		}
		if (slot == 11) {
			return 15;
		}
		if (slot == 2) {
			return 25;
		}
		if (slot == 10) {
			return 50;
		}

		return 999;
	}

	public int getLevel() {
		int base = RPGCoreConfig.baseXp;
		int previousXp = RPGCoreConfig.baseXp;
		int level = 1;

		while (xpGlobal >= previousXp) {
			previousXp += base + ((base / 100.0) * ((level * 5) * (35 + ((level / 10) * 10))));
			level++;
		}
		return level;
	}

	public int getXpForLevel(int level) {
		if (level < 1) {
			return 0;
		}
		int base = RPGCoreConfig.baseXp;
		int xpForLevel = RPGCoreConfig.baseXp;

		for (int i = 1; i < level; i++) {
			xpForLevel += base + ((base / 100.0) * ((i * 5) * (35 + ((i / 10) * 10))));
		}
		return xpForLevel;
	}

	public float xpToNextLevel() {
		return (getXpForLevel(getLevel())) - (int) getXPGlobal();
	}

	public void levelUpGlobal(EntityPlayer player, float xpAdd) {
		Style style = new Style();
		int lvl = getLevelFromXP(getXPGlobal() + xpAdd);
		player.sendMessage(new TextComponentString("Level up! Your Global Level is now " + lvl).setStyle(style.setColor(TextFormatting.GOLD).setBold(true)));
		player.sendMessage(new TextComponentString("You currently have " + (getSkillPoints() + RPGCoreConfig.skillPointsPerLevel) + " Skill Points available.")
				.setStyle(style.setColor(TextFormatting.GOLD)));
		skillPoints += RPGCoreConfig.skillPointsPerLevel;
		verbose(player.getDisplayName() + " reached Global Level " + lvl + " and now has " + getSkillPoints() + " skill points.");

		List<EntityPlayerMP> players =  FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers();
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i) != player) {
				players.get(i).sendMessage(new TextComponentString(player.getDisplayName() + " is now Level " + lvl + "!").setStyle(style.setColor(TextFormatting.GOLD)));
			}
		}
	}

	public void setXP(float xpSet) {
		xpGlobal = xpSet;
	}

	public float getXPGlobal() {
		return xpGlobal;
	}

	public int getSkillPoints() {
		return skillPoints;
	}

	public void setSkillPoints(int points) {
		skillPoints = points;
	}

	@Override
	public void forceAddXP(float xpAdd, EntityPlayer player) {
		if ((xpAdd + getXPGlobal()) >= getXpForLevel(getLevel())) {
			levelUpGlobal(player, xpAdd);
		}
		xpGlobal += xpAdd;
	}

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties(skillId, new GlobalLevel((EntityPlayer) event.entity, skillId));
		}
	}

	@Override
	public boolean hasGui() {
		return false;
	}

	@Override
	public String skillName() {
		return "Global Level";
	}

	@Override
	public void openGui() {
	}

	@Override
	public void addDescription() {
	}

	@Override
	public void activateSkill(EntityPlayer player, World world) {
	}

	@Override
	public ResourceLocation skillIcon() {
		return null;
	}

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

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return false;
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		return null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return null;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {

	}
}
