package co.uk.silvania.rpgcore;

import co.uk.silvania.rpgcore.skills.SkillLevelJump;
import co.uk.silvania.rpgcore.skills.SkillLevelPunch;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = RPGCore.MODID, version = RPGCore.VERSION)
public class RPGCore {
	
    public static final String MODID = "rpgcore";
    public static final String VERSION = "0.0.1";
    
    public static SimpleNetworkWrapper network;
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	network = NetworkRegistry.INSTANCE.newSimpleChannel("RPGCore");
    	
    	network.registerMessage(LevelPacket.Handler.class, LevelPacket.class, 0, Side.CLIENT);
    	
    	SkillLevelJump skillJump = new SkillLevelJump(null, "skillJump");
    	SkillLevelPunch skillPunch = new SkillLevelPunch(null, "skillPunch");

    	System.out.println("########## INIT ##########");
    	System.out.println("########## INIT ##########");
    	System.out.println("########## INIT ##########");
    	System.out.println("########## INIT ##########");
    	System.out.println("########## INIT ##########");
    	System.out.println(" ");
    	RegisterSkill.register(skillJump);
    	RegisterSkill.register(skillPunch);
    	System.out.println(" ");
    	System.out.println("########## INIT ##########");
    	System.out.println("########## INIT ##########");
    	System.out.println("########## INIT ##########");
    	System.out.println("########## INIT ##########");
    	System.out.println("########## INIT ##########");
    	
    	MinecraftForge.EVENT_BUS.register(new HandlerOfEvents());
    	MinecraftForge.EVENT_BUS.register(new SkillLevelJump(null, "skillJump"));
    	MinecraftForge.EVENT_BUS.register(new SkillLevelPunch(null, "skillPunch"));
    }
}
