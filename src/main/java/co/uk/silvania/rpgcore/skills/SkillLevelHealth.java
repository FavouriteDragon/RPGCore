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
		super(skillID);
		staticSkillId = skillID;
		this.xp = 0;
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat(skillId + "xp", xp);
		compound.setTag(skillId, nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = (NBTTagCompound) compound.getTag(skillId);
		xp = nbt.getFloat(skillId + "xp");
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
	
	@Override
	public boolean secretSkill() {
		return true;
	}
	
	@Override
	public double levelMultiplier() {
		return 3;
	}

	@Override
	public boolean hasGui() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String skillName() {
		return "Health";
	}

	@Override
	public void openGui() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addRequirements() {
		requiredSkills.add("skillAgility"); 
		requiredSkills.add("skillStrength");
		requiredSkills.add("skillSwords");
		requiredSkills.add("skillJump"); 
	}

	@Override
	public void addDescription() {
		description.add(nameFormat() + "\u00A7l" + skillName());
		description.add("\u00A7oRequirements are just to show how requirements work.");
		description.add("\u00A7oRequirements can never be met.");
		description.add("Each level gives +1 HP");
		description.add("Experience earned by taking damage.");
		description.add("Different damage sources give different XP.");
	}
	
	@Override
	public int unlockedLevel() {
		return 2;
	}

	@Override
	public ResourceLocation skillIcon() {
		return new ResourceLocation(RPGCore.MODID, "textures/gui/skills.png");
	}

	@Override
	public void activateSkill(EntityPlayer player, World world) {}

	@Override
	public int iconX() {
		return 60;
	}

	@Override
	public int iconZ() {
		return 0;
	}
	
	@Override
	public void xpGained(String skillId, float xpAdd, EntityPlayer player) {
		SkillLevelHealth skill = (SkillLevelHealth) SkillLevelHealth.get(player, skillId);
		skill.addXP(xpAdd/10, player);
	}

	@Override
	public void levelUp(EntityPlayer player) {
		super.levelUp(player);
		System.out.println("Level up! " + skillName() + " is now level " + getLevel());
	}
	
	@Override
	public boolean skillUnlocked() {
		return false;
	}
	
	@Override
	public void addEquipIssues() {
		equipIssues.add("Skill not yet implemented.");
	}
	
	@Override
	public String shortName() {
		return "HP";
	}
}
