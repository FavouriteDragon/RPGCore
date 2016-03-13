package co.uk.silvania.rpgcore;

import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import co.uk.silvania.rpgcore.skills.SkillLevelJump;
import co.uk.silvania.rpgcore.skills.SkillLevelPunch;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class HandlerOfEvents {
	
	@SubscribeEvent
	public void onFall(LivingFallEvent event) {
		if (event.entity instanceof EntityPlayer) {
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Pre event) {
		Minecraft mc = Minecraft.getMinecraft();
		
		for (int i = 0; i < RegisterSkill.skillList.size(); i++) {
			SkillLevelBase skillBase = RegisterSkill.skillList.get(i);
			if (skillBase instanceof SkillLevelJump) {
				SkillLevelJump jump = ((SkillLevelJump) skillBase).get((EntityPlayer) mc.thePlayer);
				mc.fontRenderer.drawString("Name: " + jump.skillName + ", XP: " + jump.getXP(), 2, (i*10)+2, 16777215);
			} else if (skillBase instanceof SkillLevelPunch) {
				SkillLevelPunch punch = ((SkillLevelPunch) skillBase).get((EntityPlayer) mc.thePlayer);
				mc.fontRenderer.drawString("Name: " + punch.skillName + ", XP: " + punch.getXP(), 2, (i*10)+2, 16777215);
			}
		}
	}
	
	/*@SubscribeEvent
	public void onPlayerJoinWorld(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayer && !event.entity.worldObj.isRemote) {
			SkillLevelBase level = SkillLevelBase.get((EntityPlayer) event.entity);
			System.out.println("Sending data to client!");
			//RPGCore.network.sendTo(new LevelPacket(level.getXP()), (EntityPlayerMP) event.entity);
		}
	}*/

}
