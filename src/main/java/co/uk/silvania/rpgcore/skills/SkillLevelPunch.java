package co.uk.silvania.rpgcore.skills;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class SkillLevelPunch extends SkillLevelBase implements IExtendedEntityProperties {
	
	public static String skillId = "skillPunch";
	private final EntityPlayer player;

	public String nbtSkillId = "";
	
	public SkillLevelPunch(EntityPlayer player, String skillID) {
		skillName = "Punch";
		this.player = player;
		this.xp = 0;
		//this.skillId = skillID;
	}
	
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(SkillLevelPunch.skillId, new SkillLevelPunch(player, skillId));
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
		
	public static SkillLevelPunch get(EntityPlayer player) {
		return (SkillLevelPunch) player.getExtendedProperties(skillId);
	}
	
	public void copy(SkillLevelPunch properties) {
		xp = properties.xp;
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties(skillId, new SkillLevelPunch((EntityPlayer)event.entity, skillId));
		}
	}
	
	@SubscribeEvent
	public void onPunch(LivingAttackEvent event) {
		//TODO Check if player has this skill equipped
		if (event.source.getEntity() instanceof EntityPlayer) {
			SkillLevelPunch skill = SkillLevelPunch.get((EntityPlayer) event.source.getEntity());
			skill.addXP(1);
			System.out.println("It Punched! XP: " + skill.getXP());
		}
	}
	
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		SkillLevelPunch.get(event.entityPlayer).copy(SkillLevelPunch.get(event.original));
	}
}
