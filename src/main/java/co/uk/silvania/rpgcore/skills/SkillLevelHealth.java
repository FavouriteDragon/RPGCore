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

public class SkillLevelHealth extends SkillLevelBase implements IExtendedEntityProperties {
	
	public static String staticSkillId;
	
	public SkillLevelHealth(EntityPlayer player, String skillID) {
		skillName = "Health";
		skillId = skillID;
		staticSkillId = skillID;
		this.xp = 0;
		skillIcon = new ResourceLocation(RPGCore.MODID, "textures/gui/skills.png");
		iconX = 60;
		iconZ = 0;
		
		levelMultiplier = 1.2; //Higher value = slower levelling.
		
		unlockedLevel = 2;
		requiredSkills.add("skillAgility"); 
		requiredSkills.add("skillStrength");
		requiredSkills.add("skillSwords");
		requiredSkills.add("skillJump"); 
		
		description.add(nameFormat + "\u00A7l" + skillName);
		description.add("\u00A7oRequirements are just to show how requirements work.");
		description.add("\u00A7oRequirements can never be met.");
		description.add("Each level gives +1 HP");
		description.add("Experience earned by taking damage.");
		description.add("Different damage sources give different XP.");
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat(skillName + "XP", xp);
		compound.setTag(skillId, nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = (NBTTagCompound) compound.getTag(skillId);
		xp = nbt.getFloat(skillName + "XP");
	}

	@Override public void init(Entity entity, World world) {}
	
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(SkillLevelHealth.staticSkillId, new SkillLevelHealth(player, staticSkillId));
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties(skillId, new SkillLevelHealth((EntityPlayer)event.entity, skillId));
		}
	}

}
