package co.uk.silvania.rpgcore.skills;

import co.uk.silvania.rpgcore.InventoryCustomPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class SkillLevelBase {
	
	public int xp;
	public String skillName;
	
	public SkillLevelBase() {
		this.xp = 0;
	}
	
	public void addXP(int xpAdd) {
		xp += xpAdd;
	}
	
	public int getXP() {
		return xp;
	}
	
	public void setXP(int xpSet) {
		xp = xpSet;
	}
}
