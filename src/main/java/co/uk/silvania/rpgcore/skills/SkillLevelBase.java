package co.uk.silvania.rpgcore.skills;

import co.uk.silvania.rpgcore.RegisterSkill;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class SkillLevelBase {
	
	public int xp;
	public ResourceLocation skillIcon;
	public int iconX;
	public int iconZ;
	public String skillName;
	public String skillId;
	
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
			
			if (skill.skillId.equals(skillId)) {
				return skill;
			}
		}
		return null;
	}
}
