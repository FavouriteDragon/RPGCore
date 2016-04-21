package co.uk.silvania.rpgcore.client;

import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.network.OpenGuiPacket;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;

public class KeyInputHandler {
	
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (KeyBindings.openSkills.isPressed()) {
			RPGCore.network.sendToServer(new OpenGuiPacket(0));
		}
	}
}
