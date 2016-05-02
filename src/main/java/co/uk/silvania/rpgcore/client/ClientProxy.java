package co.uk.silvania.rpgcore.client;

import co.uk.silvania.rpgcore.CommonProxy;
import co.uk.silvania.rpgcore.network.EquippedSkillsPacket;
import co.uk.silvania.rpgcore.network.LevelPacket;
import co.uk.silvania.rpgcore.skills.EquippedSkills;
import co.uk.silvania.rpgcore.skills.GlobalLevel;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerKeyBinds() {
		FMLCommonHandler.instance().bus().register(new KeyInputHandler());
		KeyBindings.init();
	}
	
	@Override
	public void syncLevelsWithClient(LevelPacket message, MessageContext ctx) {
		if (message.skillId.equalsIgnoreCase(GlobalLevel.staticSkillId)) {
			GlobalLevel glevel = (GlobalLevel) GlobalLevel.get((EntityPlayer) Minecraft.getMinecraft().thePlayer, message.skillId);
			glevel.setXP(message.xp);
			glevel.setSkillPoints(message.val);
		} else {
			SkillLevelBase level = (SkillLevelBase) SkillLevelBase.get((EntityPlayer) Minecraft.getMinecraft().thePlayer, message.skillId);
			level.setXP(message.xp);
		}
	}
	
	@Override
	public void syncEquippedSkillsWithClient(EquippedSkillsPacket message, MessageContext ctx) {
		EquippedSkills skills = (EquippedSkills) EquippedSkills.get((EntityPlayer) Minecraft.getMinecraft().thePlayer);
		
		skills.setSkill(0, message.slot0);
		skills.setSkill(1, message.slot1);
		skills.setSkill(2, message.slot2);
		skills.setSkill(3, message.slot3);
		skills.setSkill(4, message.slot4);
		skills.setSkill(5, message.slot5);
		skills.setSkill(6, message.slot6);
		skills.setSkill(7, message.slot7);
		skills.setSkill(8, message.slot8);
		skills.setSkill(9, message.slot9);
		skills.setSkill(10, message.slot10);
		skills.setSkill(11, message.slot11);
	}
}
