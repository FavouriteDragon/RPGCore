package co.uk.silvania.rpgcore;

import co.uk.silvania.rpgcore.network.EquippedSkillsPacket;
import co.uk.silvania.rpgcore.network.LevelPacket;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {
	
	public void init() {
		registerKeyBinds();
	}
	
	public void registerKeyBinds() {}
	public void syncLevelsWithClient(LevelPacket message, MessageContext ctx) {}
	public void syncEquippedSkillsWithClient(EquippedSkillsPacket message, MessageContext ctx) {}

}
