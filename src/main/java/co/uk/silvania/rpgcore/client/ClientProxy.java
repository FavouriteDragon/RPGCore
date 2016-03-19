package co.uk.silvania.rpgcore.client;

import co.uk.silvania.rpgcore.CommonProxy;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerKeyBinds() {
		FMLCommonHandler.instance().bus().register(new KeyInputHandler());
		KeyBindings.init();
	}
}
