package co.uk.silvania.rpgcore.client;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindings {
	
	public static KeyBinding openSkills;
	
	public static void init() {
		openSkills = new KeyBinding("key.openSkills", Keyboard.KEY_F4, "key.categories.rpgcore");
		
		ClientRegistry.registerKeyBinding(openSkills);
	}

}
