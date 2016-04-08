package co.uk.silvania.rpgcore.skills;

import co.uk.silvania.rpgcore.RPGCore;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

public class SkillLevelSwords extends SkillLevelBase implements IExtendedEntityProperties {
	
	public static String staticSkillId;
	
	public SkillLevelSwords(EntityPlayer player, String skillID) {
		skillName = "Swords";
		skillId = skillID;
		staticSkillId = skillID;
		this.xp = 0;
		skillIcon = new ResourceLocation(RPGCore.MODID, "textures/gui/skills.png");
		iconX = 90;
		iconZ = 0;
		
		description.add(nameFormat + "\u00A7l" + skillName);
		description.add("\u00A7oExample skill, has no function currently.");
		description.add("\u00A7oWill not be included in release.");
		description.add("One-handed sword skill. Levelled by using");
		description.add("one-handed swords.");
		description.add("Later unlocks other skills.");
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat(skillName, xp);
		compound.setTag(skillId, nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = (NBTTagCompound) compound.getTag(skillId);
		xp = nbt.getInteger(skillName);		
	}
	
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(SkillLevelSwords.staticSkillId, new SkillLevelSwords(player, staticSkillId));
	}

	@Override public void init(Entity entity, World world) {}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties(skillId, new SkillLevelSwords((EntityPlayer)event.entity, skillId));
		}
	}
}
