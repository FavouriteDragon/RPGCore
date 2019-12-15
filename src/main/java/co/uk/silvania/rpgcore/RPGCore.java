package co.uk.silvania.rpgcore;

import co.uk.silvania.rpgcore.network.*;
import co.uk.silvania.rpgcore.skills.*;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = RPGCore.MODID, version = RPGCore.VERSION)
public class RPGCore {

	public static final String MODID = "rpgcore";
	public static final String VERSION = "0.4.2";

	@Mod.Instance(RPGCore.MODID)
	public static RPGCore instance;
	public static GuiHandler guiHandler = new GuiHandler();

	@SidedProxy(clientSide = "co.uk.silvania.rpgcore.client.ClientProxy", serverSide = "co.uk.silvania.rpgcore.CommonProxy")
	public static CommonProxy proxy;
	public static SimpleNetworkWrapper network;
	public static String configPath;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, (IGuiHandler) guiHandler);

		configPath = event.getModConfigurationDirectory() + "/RPGCore/";

		RPGCoreConfig.init(configPath);
		GuiConfig.init(configPath);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		network = NetworkRegistry.INSTANCE.newSimpleChannel("RPGCore");
		proxy.init();

		SkillLevelAgility skillAgility = new SkillLevelAgility(null, "skillAgility");
		SkillLevelStrength skillStrength = new SkillLevelStrength(null, "skillStrength");
		SkillLevelHealth skillHealth = new SkillLevelHealth(null, "skillHealth");

		//Global level is NOT registered as it's not a normal skill, and shouldn't appear in lists etc.
		//This only works because it's within the mod. Registration is required for external skills.
		GlobalLevel gLevel = new GlobalLevel(null, "globalLevel");

		RegisterSkill.register(skillAgility);
		RegisterSkill.register(skillStrength);
		RegisterSkill.register(skillHealth);

		network.registerMessage(LevelPacket.Handler.class, LevelPacket.class, 0, Side.CLIENT);
		network.registerMessage(OpenGuiPacket.Handler.class, OpenGuiPacket.class, 1, Side.SERVER);
		network.registerMessage(EquippedSkillsPacket.Handler.class, EquippedSkillsPacket.class, 2, Side.CLIENT);
		network.registerMessage(EquipNewSkillPacket.Handler.class, EquipNewSkillPacket.class, 3, Side.SERVER);
		network.registerMessage(SkillPointPacket.Handler.class, SkillPointPacket.class, 4, Side.SERVER);

		FMLCommonHandler.instance().bus().register(new EventHandler());

		MinecraftForge.EVENT_BUS.register(new EventHandler());
		MinecraftForge.EVENT_BUS.register(new EquippedSkills());
		MinecraftForge.EVENT_BUS.register(new GlobalLevel(null, "globalLevel"));
		MinecraftForge.EVENT_BUS.register(new SkillLevelAgility(null, "skillAgility"));
		MinecraftForge.EVENT_BUS.register(new SkillLevelStrength(null, "skillStrength"));
		MinecraftForge.EVENT_BUS.register(new SkillLevelHealth(null, "skillHealth"));
	}

	@Mod.EventHandler
	public void serverStart(FMLServerStartingEvent event) {
		MinecraftServer server = MinecraftServer.getServer();
		ICommandManager command = server.getCommandManager();
		ServerCommandManager manager = (ServerCommandManager) command;

		manager.registerCommand(new CommandRPGCore());
	}
}
