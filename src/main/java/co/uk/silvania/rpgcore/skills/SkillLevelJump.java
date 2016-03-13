package co.uk.silvania.rpgcore.skills;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class SkillLevelJump extends SkillLevelBase implements IExtendedEntityProperties {
	
	public static String skillId = "skillJump";
	private final EntityPlayer player;

	public String nbtSkillId = "";
	
	public SkillLevelJump(EntityPlayer player, String skillID) {
		skillName = "Jump";
		this.player = player;
		this.xp = 0;
		//this.skillId = skillID;
	}
	
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(SkillLevelJump.skillId, new SkillLevelJump(player, skillId));
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger(nbtSkillId, xp);
		compound.setTag(skillId, nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = (NBTTagCompound) compound.getTag(skillId);
		xp = nbt.getInteger(nbtSkillId);		
	}

	@Override public void init(Entity entity, World world) {}
		
	public static SkillLevelJump get(EntityPlayer player) {
		return (SkillLevelJump) player.getExtendedProperties(skillId);
	}
	
	public void copy(SkillLevelJump properties) {
		xp = properties.xp;
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties(skillId, new SkillLevelJump((EntityPlayer)event.entity, skillId));
		}
	}
	
	@SubscribeEvent
	public void onJump(LivingJumpEvent event) {
		//TODO Check if player has this skill equipped
		if (event.entity instanceof EntityPlayer) {
			SkillLevelJump skill = SkillLevelJump.get((EntityPlayer) event.entity);
			skill.addXP(1);
			System.out.println("It jumped! XP: " + skill.getXP());
		}
	}
	
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		SkillLevelJump.get(event.entityPlayer).copy(SkillLevelJump.get(event.original));
	}
}
