package co.uk.silvania.rpgcore.skills;

import co.uk.silvania.rpgcore.RPGCore;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class SkillLevelAgility extends SkillLevelBase implements IExtendedEntityProperties {
	
	public static String staticSkillId;
	
	public SkillLevelAgility(EntityPlayer player, String skillID) {
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
		player.registerExtendedProperties(SkillLevelAgility.staticSkillId, new SkillLevelAgility(player, staticSkillId));
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties(skillId, new SkillLevelAgility((EntityPlayer)event.entity, skillId));
		}
	}
	
	@Override
	public double levelMultiplier() {
		return 2.0;
	}

	@Override
	public boolean hasGui() {
		return true;
	}

	@Override
	public String skillName() {
		return "Agility";
	}

	@Override
	public void openGui() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addIncompatibilities() {
		incompatibleSkills.add("skillStrength");
	}

	@Override
	public void addDescription() {
		description.add(nameFormat() + "\u00A7l" + skillName());
		description.add("Base Skill");
		description.add("Required for most speed skills");
		description.add("Levelled slowly by sprinting and jumping.");
		description.add("Slowly increases sprint speed.");
	}

	@Override
	public ResourceLocation skillIcon() {
		return new ResourceLocation(RPGCore.MODID, "textures/gui/skills.png");
	}

	@Override
	public void activateSkill(EntityPlayer player, World world) {}

	@Override
	public int iconX() {
		return 30;
	}

	@Override
	public int iconZ() {
		return 0;
	}

	int tick = 0;
	float prevTickFall = 0;
	float playerFallHealth = -1;
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.START && event.side == Side.SERVER) {
			if (event.player.isSprinting()) {
				if (tick < 20) {
					tick += 1;
				} else {
					System.out.println("Player sprinted for a full second. XP!");
					System.out.println("Time: " + System.currentTimeMillis());
					//addXPWithUpdate(1F, event.player);
					tick = 0;
				}
			} else {
				tick = 0;
			}
			if (event.player.fallDistance > prevTickFall) {
				prevTickFall = event.player.fallDistance;
				playerFallHealth = event.player.getHealth();
			} else {
				if (playerFallHealth <= event.player.getHealth() && prevTickFall > 3) {
					System.out.println("Health! playerFallHealth: " + playerFallHealth + ", getHealth(): " + event.player.getHealth());
					System.out.println("Player didn't take damage on falling, get some XP!");
				}
				playerFallHealth = -1;
				prevTickFall = 0;
			}
		}
	}
	
	@Override
	public void levelUp() {
		System.out.println("Level up! " + skillName() + " is now level " + getLevel());
	}
	
	@Override
	public boolean canGainXP() {
		return false;
	}
	
	@Override
	public int xpBarColour() {
		return 252;
	}
}
