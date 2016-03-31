package co.uk.silvania.rpgcore.skills;

import java.util.ArrayList;

import co.uk.silvania.rpgcore.RPGCore;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

public class SkillLevelAgility extends SkillLevelBase implements IExtendedEntityProperties {
	
	public static String staticSkillId;
	
	public SkillLevelAgility(EntityPlayer player, String skillID) {
		skillName = "Agility";
		skillId = skillID;
		staticSkillId = skillID;
		this.xp = 0;
		skillIcon = new ResourceLocation(RPGCore.MODID, "textures/gui/skills.png");
		iconX = 30;
		iconZ = 0;
		
		levelMultiplier = 2.0; //Higher value = slower levelling.
		
		incompatableSkills.add("skillStrength");
		
		hasGui = true;
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger(skillName + "XP", xp);
		compound.setTag(skillId, nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = (NBTTagCompound) compound.getTag(skillId);
		xp = nbt.getInteger(skillName + "XP");
	}

	@Override public void init(Entity entity, World world) {}
	
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(SkillLevelAgility.staticSkillId, new SkillLevelAgility(player, staticSkillId));
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties(skillId, new SkillLevelAgility((EntityPlayer)event.entity, skillId));
		}
	}
}
