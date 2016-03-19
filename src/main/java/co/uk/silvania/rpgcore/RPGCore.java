package co.uk.silvania.rpgcore;

import co.uk.silvania.rpgcore.network.LevelPacket;
import co.uk.silvania.rpgcore.network.OpenGuiPacket;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = RPGCore.MODID, version = RPGCore.VERSION)
public class RPGCore {
	
    public static final String MODID = "rpgcore";
    public static final String VERSION = "0.2.0";
    
    @Instance(RPGCore.MODID)
    public static RPGCore instance;
    public static GuiHandler guiHandler = new GuiHandler();
    
    @SidedProxy(clientSide="co.uk.silvania.rpgcore.client.ClientProxy", serverSide="co.uk.silvania.rpgcore.CommonProxy")
    public static CommonProxy proxy;
    
    public static SimpleNetworkWrapper network;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	network = NetworkRegistry.INSTANCE.newSimpleChannel("RPGCore");
    	
    	proxy.init();
    	
    	network.registerMessage(LevelPacket.Handler.class, LevelPacket.class, 0, Side.CLIENT);
    	network.registerMessage(OpenGuiPacket.Handler.class, OpenGuiPacket.class, 1, Side.SERVER);
    	
    	MinecraftForge.EVENT_BUS.register(new HandlerOfEvents());
    	MinecraftForge.EVENT_BUS.register(new SkillLevelBase());
    }
}
