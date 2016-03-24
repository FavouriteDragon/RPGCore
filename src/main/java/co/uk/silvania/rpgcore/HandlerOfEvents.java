package co.uk.silvania.rpgcore;

import co.uk.silvania.rpgcore.network.EquippedSkillsPacket;
import co.uk.silvania.rpgcore.network.LevelPacket;
import co.uk.silvania.rpgcore.skills.EquippedSkills;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class HandlerOfEvents {
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Pre event) {
		Minecraft mc = Minecraft.getMinecraft();
		
		int totalEquipped = 0;
		
		for (int i = 0; i < RegisterSkill.skillList.size(); i++) {
			SkillLevelBase skillBase = RegisterSkill.skillList.get(i);
			SkillLevelBase skill = (SkillLevelBase) skillBase.get((EntityPlayer) mc.thePlayer, skillBase.skillId);
			EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get((EntityPlayer) Minecraft.getMinecraft().thePlayer);
			
			if (equippedSkills.isSkillEquipped(skill.skillId)) {
				totalEquipped++;
				mc.fontRenderer.drawString("Name: " + skill.skillName + ", XP: " + skill.getXP(), 2, ((totalEquipped-1)*10)+2, 16777215);
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerJoinWorld(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayer && !event.entity.worldObj.isRemote) {
			for (int i = 0; i < RegisterSkill.skillList.size(); i++) {
				SkillLevelBase skillBase = RegisterSkill.skillList.get(i);
				System.out.println("skillID being sent to client: " + skillBase.skillId);
				SkillLevelBase skill = (SkillLevelBase) skillBase.get((EntityPlayer) event.entity, skillBase.skillId);
				System.out.println("Sending data to client!");
				RPGCore.network.sendTo(new LevelPacket(skill.getXP(), skill.skillId), (EntityPlayerMP) event.entity);
			}
			EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get((EntityPlayer) event.entity);
			
			System.out.println("Sending equipped skills to client!");
			System.out.println("Slot 0: " + equippedSkills.getSkillInSlot(0));
			RPGCore.network.sendTo(new EquippedSkillsPacket(
					equippedSkills.getSkillInSlot(0), 
					equippedSkills.getSkillInSlot(1), 
					equippedSkills.getSkillInSlot(2), 
					equippedSkills.getSkillInSlot(3), 
					equippedSkills.getSkillInSlot(4), 
					equippedSkills.getSkillInSlot(5), 
					equippedSkills.getSkillInSlot(6)), (EntityPlayerMP) event.entity);
		}
	}

}
