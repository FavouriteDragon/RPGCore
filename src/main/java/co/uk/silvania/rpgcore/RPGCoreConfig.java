package co.uk.silvania.rpgcore;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class RPGCoreConfig {
	
	public static File rpgCoreConfigFile;
	
	public static String[] factions;
	public static int baseXp;
	public static int skillPointsPerLevel;
	public static boolean debugMode;
	public static boolean verbose;
	
	public static void init(String configPath) {
		rpgCoreConfigFile = new File(configPath + "RPGCore.cfg");
		config = new Configuration(rpgCoreConfigFile);
		
		initConfig();
	}
	
	public static Configuration config;
	
	public static void initConfig() {						
		try {
			config.load();
			factions = config.getStringList("Factions", Configuration.CATEGORY_GENERAL, new String[] {"Caelum", "Mortalitas"}, "Add new factions for players to select.");
			baseXp = config.getInt("baseXP", Configuration.CATEGORY_GENERAL, 83, 10, Integer.MAX_VALUE, "Base int used for level up curve multipliers. Higher numbers mean ALL skills take longer to level.");
			skillPointsPerLevel = config.getInt("skillPointsPerLevel", Configuration.CATEGORY_GENERAL, 3, 1, 999, "How many skill points are awarded for each Global Level increase.");			
			debugMode = config.getBoolean("Debug Mode", Configuration.CATEGORY_GENERAL, false, "Add a load of prints to console about basically everything.");
			verbose = config.getBoolean("Verbose", Configuration.CATEGORY_GENERAL, false, "Console output on standard things, such as a player levelling up or equipping skills.");
		} catch (Exception e) {
			System.out.println("### WARNING! RPGCore could not load it's config files! ###");
		} finally {
			if (config.hasChanged()) {
				config.save();
			}
		}
	}
	
	public void setVerbose(boolean par) {
		try {
			config.load();
			config.get(Configuration.CATEGORY_GENERAL, "Verbose", par, "Console output on standard things, such as a player levelling up or equipping skills.").set(par);
		} catch (Exception e) {
			System.out.println("### WARNING! RPGCore could not load it's config files! ###");
		} finally {
			if (config.hasChanged()) {
				config.save();
				initConfig();
			}
		}
	}
	
	public void setDebug(boolean par) {
		try {
			config.load();
			config.get(Configuration.CATEGORY_GENERAL, "Debug Mode", par, "Add a load of prints to console about basically everything.").set(par);
		} catch (Exception e) {
			System.out.println("### WARNING! RPGCore could not load it's config files! ###");
		} finally {
			if (config.hasChanged()) {
				config.save();
				initConfig();
			}
		}
	}
}
