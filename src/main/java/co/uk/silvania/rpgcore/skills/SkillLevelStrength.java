package co.uk.silvania.rpgcore.skills;

import co.uk.silvania.rpgcore.RPGCore;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class SkillLevelStrength extends SkillLevelBase implements IExtendedEntityProperties {
	
	public static String staticSkillId;
	
	public SkillLevelStrength(EntityPlayer player, String skillID) {
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
		player.registerExtendedProperties(SkillLevelStrength.staticSkillId, new SkillLevelStrength(player, staticSkillId));
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties(skillId, new SkillLevelStrength((EntityPlayer)event.entity, skillId));
		}
	}
	
	@Override
	public boolean canGainXP() {
		return false;
	}

	@Override
	public boolean hasGui() {
		return false;
	}

	@Override
	public String skillName() {
		return "Strength";
	}
	
	@Override
	public double levelMultiplier() {
		return 3.5;
	}

	@Override
	public void openGui() {}
	
	@Override
	public void addIncompatibilities() {
		incompatibleSkills.add("skillAgility");
	}

	@Override
	public void addDescription() {
		description.add(nameFormat() + "\u00A7l" + skillName());
		description.add("Base Skill.");
		description.add("Required for most damage skills");
		description.add("Levelled slowly with any act of strength,");
		description.add("such as mining, fighting and so on.");
		description.add("Slowly increases punch damage.");
	}

	@Override
	public ResourceLocation skillIcon() {
		return new ResourceLocation(RPGCore.MODID, "textures/gui/skills.png");
	}

	@Override public void activateSkill(EntityPlayer player, World world) {}

	@Override
	public int iconX() {
		return 0;
	}

	@Override
	public int iconZ() {
		return 0;
	}
	
	@SubscribeEvent
	public void onPunch(LivingAttackEvent event) {
		if (event.source.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			SkillLevelStrength skill = (SkillLevelStrength) SkillLevelStrength.get(player, skillId);
			if (player.getHeldItem() != null && player.getHeldItem().getItem().isItemTool(player.getHeldItem())) {
				return;
			}
			skill.addXP(event.ammount / 5, player);
			System.out.println("Punch! isRemote: " + event.source.getEntity().worldObj.isRemote);
		}
	}
	
	
	@SubscribeEvent
	public void onBlockBreak(BreakEvent event) {
		Block block = event.block;		
		EntityPlayer player = event.getPlayer();
		
		float hardness = block.getBlockHardness(event.world, event.x, event.y, event.z);
		
		if (player.getHeldItem() != null && player.getHeldItem().getItem().isItemTool(player.getHeldItem())) {
			return;
		}
		if (hardness >= 0.5F && !player.capabilities.isCreativeMode) {
			System.out.println("Break block. hardness: " + hardness);
			SkillLevelStrength skill = (SkillLevelStrength) SkillLevelStrength.get(player, skillId);
			skill.addXPWithUpdate(hardness / 2F, player);
		}
	}
	
	@Override
	public void levelUp() {
		System.out.println("Level up! " + skillName() + " is now level " + getLevel());
	}
}
