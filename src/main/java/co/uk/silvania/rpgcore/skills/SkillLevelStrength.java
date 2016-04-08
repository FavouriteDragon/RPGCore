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

public class SkillLevelStrength extends SkillLevelBase implements IExtendedEntityProperties {
	
	public static String staticSkillId;
	
	public SkillLevelStrength(EntityPlayer player, String skillID) {
		skillName = "Strength";
		skillId = skillID;
		staticSkillId = skillID;
		this.xp = 0;
		skillIcon = new ResourceLocation(RPGCore.MODID, "textures/gui/skills.png");
		iconX = 0;
		iconZ = 0;
		
		incompatibleSkills.add("skillAgility");
		
		description.add(nameFormat + "\u00A7l" + skillName);
		description.add("Base Skill.");
		description.add("Required for most damage skills");
		description.add("Levelled slowly with any act of strength,");
		description.add("such as mining, fighting and so on.");
		description.add("Slowly increases punch damage.");
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

	@Override public void init(Entity entity, World world) {}
	
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(SkillLevelStrength.staticSkillId, new SkillLevelStrength(player, staticSkillId));
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties(skillId, new SkillLevelStrength((EntityPlayer)event.entity, skillId));
		}
	}
}
