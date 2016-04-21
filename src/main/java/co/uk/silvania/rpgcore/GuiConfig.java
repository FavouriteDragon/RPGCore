package co.uk.silvania.rpgcore;

import java.io.File;

import co.uk.silvania.rpgcore.client.skillgui.MultiLineButton;
import cpw.mods.fml.client.config.GuiSlider;
import net.minecraftforge.common.config.Configuration;

public class GuiConfig {
	
	public static File rpgCoreConfigFile;
	
	public static boolean globalXpTop;
	public static boolean showGlobalXp;
	public static boolean showGlobalLevel;
	public static boolean globalLevelLeft;
	
	public static boolean showXP1;
	public static boolean showIcon1;
	public static int xpTextType1;
	public static int xpBarPos1;
	public static int xOffset1;
	public static int yOffset1;
	public static int xpBarStyle1;
	public static int xpBarWidth1;

	public static boolean showXP2;
	public static boolean showIcon2;
	public static int xpTextType2;
	public static int xpBarPos2;
	public static int xOffset2;
	public static int yOffset2;
	public static int xpBarStyle2;
	public static int xpBarWidth2;
	
	public static boolean showXP3;
	public static boolean showIcon3;
	public static int xpTextType3;
	public static int xpBarPos3;
	public static int xOffset3;
	public static int yOffset3;
	public static int xpBarStyle3;
	public static int xpBarWidth3;
	
	public static boolean showXP4;
	public static boolean showIcon4;
	public static int xpTextType4;
	public static int xpBarPos4;
	public static int xOffset4;
	public static int yOffset4;
	public static int xpBarStyle4;
	public static int xpBarWidth4;
	
	public static boolean showXP5;
	public static boolean showIcon5;
	public static int xpTextType5;
	public static int xpBarPos5;
	public static int xOffset5;
	public static int yOffset5;
	public static int xpBarStyle5;
	public static int xpBarWidth5;

	public static boolean showXP6;
	public static boolean showIcon6;
	public static int xpTextType6;
	public static int xpBarPos6;
	public static int xOffset6;
	public static int yOffset6;
	public static int xpBarStyle6;
	public static int xpBarWidth6;

	public static boolean showXP7;
	public static boolean showIcon7;
	public static int xpTextType7;
	public static int xpBarPos7;
	public static int xOffset7;
	public static int yOffset7;
	public static int xpBarStyle7;
	public static int xpBarWidth7;

	public static boolean showXP8;
	public static boolean showIcon8;
	public static int xpTextType8;
	public static int xpBarPos8;
	public static int xOffset8;
	public static int yOffset8;
	public static int xpBarStyle8;
	public static int xpBarWidth8;

	public static boolean showXP9;
	public static boolean showIcon9;
	public static int xpTextType9;
	public static int xpBarPos9;
	public static int xOffset9;
	public static int yOffset9;
	public static int xpBarStyle9;
	public static int xpBarWidth9;

	public static boolean showXP10;
	public static boolean showIcon10;
	public static int xpTextType10;
	public static int xpBarPos10;
	public static int xOffset10;
	public static int yOffset10;
	public static int xpBarStyle10;
	public static int xpBarWidth10;

	public static boolean showXP11;
	public static boolean showIcon11;
	public static int xpTextType11;
	public static int xpBarPos11;
	public static int xOffset11;
	public static int yOffset11;
	public static int xpBarStyle11;
	public static int xpBarWidth11;

	public static boolean showXP12;
	public static boolean showIcon12;
	public static int xpTextType12;
	public static int xpBarPos12;
	public static int xOffset12;
	public static int yOffset12;
	public static int xpBarStyle12;
	public static int xpBarWidth12;
	
	public static Configuration config;
	
	public static void init(String configPath) {
		rpgCoreConfigFile = new File(configPath + "GuiConfig.cfg");
		config = new Configuration(rpgCoreConfigFile);
		initConfig();
	}
	
	public static void initConfig() {		
		try {
			config.load();
			config.addCustomCategoryComment(Configuration.CATEGORY_GENERAL, "These configs are best set from the in-game GUI." + "\n" 
			+ "I recommend not playing with them here!!!" + "\n" 
			+ "To edit, click an equipped skill, and press Configure.");
			
			globalXpTop = config.getBoolean("Global XP Top/Bottom", Configuration.CATEGORY_GENERAL, true, "True to show xp at top of screen, false for bottom.");
			showGlobalXp = config.getBoolean("Show Global XP", Configuration.CATEGORY_GENERAL, true, "Show the global XP bar");
			showGlobalLevel = config.getBoolean("Show Global Level", Configuration.CATEGORY_GENERAL, false, "Show the level as text on the global XP bar");
			globalLevelLeft = config.getBoolean("Global Text Left/Right", Configuration.CATEGORY_GENERAL, false, "True to show the text on the left, false for right.");
			
			showXP1 = config.getBoolean("Show XP (1)", Configuration.CATEGORY_GENERAL, true, "Show slot 1's XP bar");
			showIcon1 = config.getBoolean("Show Icon (1)", Configuration.CATEGORY_GENERAL, false, "Show Skill Icon on slot's XP bar");
			xpTextType1 = config.getInt("XP Text Type (1)", Configuration.CATEGORY_GENERAL, 3, 0, 6, "Type of text to show on XP bar");
			xpBarPos1 = config.getInt("XP Bar Position (1)", Configuration.CATEGORY_GENERAL, 3, 1, 6, "Position of XP bar on-screen");
			xOffset1 = config.getInt("XP Bar X-Offset (1)", Configuration.CATEGORY_GENERAL, -96, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar X-offset. Only change in-game!!");
			yOffset1 = config.getInt("XP Bar Y-Offset (1)", Configuration.CATEGORY_GENERAL, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar Y-offset. Only change in-game!!");
			xpBarStyle1 = config.getInt("XP Bar Style (1)", Configuration.CATEGORY_GENERAL, 1, 1, 13, "XP bar style");
			xpBarWidth1 = config.getInt("XP Bar Width (1)", Configuration.CATEGORY_GENERAL, 100, 21, 125, "XP bar width");
			
			showXP2 = config.getBoolean("Show XP (2)", Configuration.CATEGORY_GENERAL, true, "Show slot 2's XP bar");
			showIcon2 = config.getBoolean("Show Icon (2)", Configuration.CATEGORY_GENERAL, false, "Show Skill Icon on slot's XP bar");
			xpTextType2 = config.getInt("XP Text Type (2)", Configuration.CATEGORY_GENERAL, 3, 0, 6, "Type of text to show on XP bar");
			xpBarPos2 = config.getInt("XP Bar Position (2)", Configuration.CATEGORY_GENERAL, 3, 1, 6, "Position of XP bar on-screen");
			xOffset2 = config.getInt("XP Bar X-Offset (2)", Configuration.CATEGORY_GENERAL, -2, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar X-offset. Only change in-game!!");
			yOffset2 = config.getInt("XP Bar Y-Offset (2)", Configuration.CATEGORY_GENERAL, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar Y-offset. Only change in-game!!");
			xpBarStyle2 = config.getInt("XP Bar Style (2)", Configuration.CATEGORY_GENERAL, 4, 1, 13, "XP bar style");
			xpBarWidth2 = config.getInt("XP Bar Width (2)", Configuration.CATEGORY_GENERAL, 100, 21, 125, "XP bar width");
			
			showXP3 = config.getBoolean("Show XP (3)", Configuration.CATEGORY_GENERAL, true, "Show slot 3's XP bar");
			showIcon3 = config.getBoolean("Show Icon (3)", Configuration.CATEGORY_GENERAL, false, "Show Skill Icon on slot's XP bar");
			xpTextType3 = config.getInt("XP Text Type (3)", Configuration.CATEGORY_GENERAL, 3, 0, 6, "Type of text to show on XP bar");
			xpBarPos3 = config.getInt("XP Bar Position (3)", Configuration.CATEGORY_GENERAL, 3, 1, 6, "Position of XP bar on-screen");
			xOffset3 = config.getInt("XP Bar X-Offset (3)", Configuration.CATEGORY_GENERAL, -2, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar X-offset. Only change in-game!!");
			yOffset3 = config.getInt("XP Bar Y-Offset (3)", Configuration.CATEGORY_GENERAL, 19, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar Y-offset. Only change in-game!!");
			xpBarStyle3 = config.getInt("XP Bar Style (3)", Configuration.CATEGORY_GENERAL, 2, 1, 13, "XP bar style");
			xpBarWidth3 = config.getInt("XP Bar Width (3)", Configuration.CATEGORY_GENERAL, 100, 21, 125, "XP bar width");
			
			showXP4 = config.getBoolean("Show XP (4)", Configuration.CATEGORY_GENERAL, true, "Show slot 4's XP bar");
			showIcon4 = config.getBoolean("Show Icon (4)", Configuration.CATEGORY_GENERAL, false, "Show Skill Icon on slot's XP bar");
			xpTextType4 = config.getInt("XP Text Type (4)", Configuration.CATEGORY_GENERAL, 3, 0, 6, "Type of text to show on XP bar");
			xpBarPos4 = config.getInt("XP Bar Position (4)", Configuration.CATEGORY_GENERAL, 3, 1, 6, "Position of XP bar on-screen");
			xOffset4 = config.getInt("XP Bar X-Offset (4)", Configuration.CATEGORY_GENERAL, -2, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar X-offset. Only change in-game!!");
			yOffset4 = config.getInt("XP Bar Y-Offset (4)", Configuration.CATEGORY_GENERAL, 33, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar Y-offset. Only change in-game!!");
			xpBarStyle4 = config.getInt("XP Bar Style (4)", Configuration.CATEGORY_GENERAL, 4, 1, 13, "XP bar style");
			xpBarWidth4 = config.getInt("XP Bar Width (4)", Configuration.CATEGORY_GENERAL, 100, 21, 125, "XP bar width");
			
			showXP5 = config.getBoolean("Show XP (5)", Configuration.CATEGORY_GENERAL, true, "Show slot 5's XP bar");
			showIcon5 = config.getBoolean("Show Icon (5)", Configuration.CATEGORY_GENERAL, false, "Show Skill Icon on slot's XP bar");
			xpTextType5 = config.getInt("XP Text Type (5)", Configuration.CATEGORY_GENERAL, 3, 0, 6, "Type of text to show on XP bar");
			xpBarPos5 = config.getInt("XP Bar Position (5)", Configuration.CATEGORY_GENERAL, 3, 1, 6, "Position of XP bar on-screen");
			xOffset5 = config.getInt("XP Bar X-Offset (5)", Configuration.CATEGORY_GENERAL, -2, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar X-offset. Only change in-game!!");
			yOffset5 = config.getInt("XP Bar Y-Offset (5)", Configuration.CATEGORY_GENERAL, 47, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar Y-offset. Only change in-game!!");
			xpBarStyle5 = config.getInt("XP Bar Style (5)", Configuration.CATEGORY_GENERAL, 2, 1, 13, "XP bar style");
			xpBarWidth5 = config.getInt("XP Bar Width (5)", Configuration.CATEGORY_GENERAL, 100, 21, 125, "XP bar width");
			
			showXP6 = config.getBoolean("Show XP (6)", Configuration.CATEGORY_GENERAL, true, "Show slot 6's XP bar");
			showIcon6 = config.getBoolean("Show Icon (6)", Configuration.CATEGORY_GENERAL, false, "Show Skill Icon on slot's XP bar");
			xpTextType6 = config.getInt("XP Text Type (6)", Configuration.CATEGORY_GENERAL, 3, 0, 6, "Type of text to show on XP bar");
			xpBarPos6 = config.getInt("XP Bar Position (6)", Configuration.CATEGORY_GENERAL, 3, 1, 6, "Position of XP bar on-screen");
			xOffset6 = config.getInt("XP Bar X-Offset (6)", Configuration.CATEGORY_GENERAL, -2, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar X-offset. Only change in-game!!");
			yOffset6 = config.getInt("XP Bar Y-Offset (6)", Configuration.CATEGORY_GENERAL, 61, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar Y-offset. Only change in-game!!");
			xpBarStyle6 = config.getInt("XP Bar Style (6)", Configuration.CATEGORY_GENERAL, 4, 1, 13, "XP bar style");
			xpBarWidth6 = config.getInt("XP Bar Width (6)", Configuration.CATEGORY_GENERAL, 100, 21, 125, "XP bar width");
			
			showXP7 = config.getBoolean("Show XP (7)", Configuration.CATEGORY_GENERAL, true, "Show slot 7's XP bar");
			showIcon7 = config.getBoolean("Show Icon (7)", Configuration.CATEGORY_GENERAL, false, "Show Skill Icon on slot's XP bar");
			xpTextType7 = config.getInt("XP Text Type (7)", Configuration.CATEGORY_GENERAL, 3, 0, 6, "Type of text to show on XP bar");
			xpBarPos7 = config.getInt("XP Bar Position (7)", Configuration.CATEGORY_GENERAL, 3, 1, 6, "Position of XP bar on-screen");
			xOffset7 = config.getInt("XP Bar X-Offset (7)", Configuration.CATEGORY_GENERAL, -2, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar X-offset. Only change in-game!!");
			yOffset7 = config.getInt("XP Bar Y-Offset (7)", Configuration.CATEGORY_GENERAL, 75, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar Y-offset. Only change in-game!!");
			xpBarStyle7 = config.getInt("XP Bar Style (7)", Configuration.CATEGORY_GENERAL, 2, 1, 13, "XP bar style");
			xpBarWidth7 = config.getInt("XP Bar Width (7)", Configuration.CATEGORY_GENERAL, 100, 21, 125, "XP bar width");
			
			showXP8 = config.getBoolean("Show XP (8)", Configuration.CATEGORY_GENERAL, true, "Show slot 8's XP bar");
			showIcon8 = config.getBoolean("Show Icon (8)", Configuration.CATEGORY_GENERAL, false, "Show Skill Icon on slot's XP bar");
			xpTextType8 = config.getInt("XP Text Type (8)", Configuration.CATEGORY_GENERAL, 3, 0, 6, "Type of text to show on XP bar");
			xpBarPos8 = config.getInt("XP Bar Position (8)", Configuration.CATEGORY_GENERAL, 3, 1, 6, "Position of XP bar on-screen");
			xOffset8 = config.getInt("XP Bar X-Offset (8)", Configuration.CATEGORY_GENERAL, -96, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar X-offset. Only change in-game!!");
			yOffset8 = config.getInt("XP Bar Y-Offset (8)", Configuration.CATEGORY_GENERAL, 75, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar Y-offset. Only change in-game!!");
			xpBarStyle8 = config.getInt("XP Bar Style (8)", Configuration.CATEGORY_GENERAL, 3, 1, 13, "XP bar style");
			xpBarWidth8 = config.getInt("XP Bar Width (8)", Configuration.CATEGORY_GENERAL, 100, 21, 125, "XP bar width");
			
			showXP9 = config.getBoolean("Show XP (9)", Configuration.CATEGORY_GENERAL, true, "Show slot 9's XP bar");
			showIcon9 = config.getBoolean("Show Icon (9)", Configuration.CATEGORY_GENERAL, false, "Show Skill Icon on slot's XP bar");
			xpTextType9 = config.getInt("XP Text Type (9)", Configuration.CATEGORY_GENERAL, 3, 0, 6, "Type of text to show on XP bar");
			xpBarPos9 = config.getInt("XP Bar Position (9)", Configuration.CATEGORY_GENERAL, 3, 1, 6, "Position of XP bar on-screen");
			xOffset9 = config.getInt("XP Bar X-Offset (9)", Configuration.CATEGORY_GENERAL, -96, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP Bar X-offset. Only change in-game!!");
			yOffset9 = config.getInt("XP Bar Y-Offset (9)", Configuration.CATEGORY_GENERAL, 61, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP Bar Y-offset. Only change in-game!!");
			xpBarStyle9 = config.getInt("XP Bar Style (9)", Configuration.CATEGORY_GENERAL, 1, 1, 13, "XP bar style");
			xpBarWidth9 = config.getInt("XP Bar Width (9)", Configuration.CATEGORY_GENERAL, 100, 21, 125, "XP bar width");
			
			showXP10 = config.getBoolean("Show XP (10)", Configuration.CATEGORY_GENERAL, true, "Show slot 10's XP bar");
			showIcon10 = config.getBoolean("Show Icon (10)", Configuration.CATEGORY_GENERAL, false, "Show Skill Icon on slot's XP bar");
			xpTextType10 = config.getInt("XP Text Type (10)", Configuration.CATEGORY_GENERAL, 3, 0, 6, "Type of text to show on XP bar");
			xpBarPos10 = config.getInt("XP Bar Position (10)", Configuration.CATEGORY_GENERAL, 3, 1, 6, "Position of XP bar on-screen");
			xOffset10 = config.getInt("XP Bar X-Offset (10)", Configuration.CATEGORY_GENERAL, -96, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar X-offset. Only change in-game!!");
			yOffset10 = config.getInt("XP Bar Y-Offset (10)", Configuration.CATEGORY_GENERAL, 47, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar Y-offset. Only change in-game!!");
			xpBarStyle10 = config.getInt("XP Bar Style (10)", Configuration.CATEGORY_GENERAL, 3, 1, 13, "XP bar style");
			xpBarWidth10 = config.getInt("XP Bar Width (10)", Configuration.CATEGORY_GENERAL, 100, 21, 125, "XP bar width");
			
			showXP11 = config.getBoolean("Show XP (11)", Configuration.CATEGORY_GENERAL, true, "Show slot 11's XP bar");
			showIcon11 = config.getBoolean("Show Icon (11)", Configuration.CATEGORY_GENERAL, false, "Show Skill Icon on slot's XP bar");
			xpTextType11 = config.getInt("XP Text Type (11)", Configuration.CATEGORY_GENERAL, 3, 0, 6, "Type of text to show on XP bar");
			xpBarPos11 = config.getInt("XP Bar Position (11)", Configuration.CATEGORY_GENERAL, 3, 1, 6, "Position of XP bar on-screen");
			xOffset11 = config.getInt("XP Bar X-Offset (11)", Configuration.CATEGORY_GENERAL, -96, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar X-offset. Only change in-game!!");
			yOffset11 = config.getInt("XP Bar Y-Offset (11)", Configuration.CATEGORY_GENERAL, 33, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar Y-offset. Only change in-game!!");
			xpBarStyle11 = config.getInt("XP Bar Style (11)", Configuration.CATEGORY_GENERAL, 1, 1, 13, "XP bar style");
			xpBarWidth11 = config.getInt("XP Bar Width (11)", Configuration.CATEGORY_GENERAL, 100, 21, 125, "XP bar width");
			
			showXP12 = config.getBoolean("Show XP (12)", Configuration.CATEGORY_GENERAL, true, "Show slot 12's XP bar");
			showIcon12 = config.getBoolean("Show Icon (12)", Configuration.CATEGORY_GENERAL, false, "Show Skill Icon on slot's XP bar");
			xpTextType12 = config.getInt("XP Text Type (12)", Configuration.CATEGORY_GENERAL, 3, 0, 6, "Type of text to show on XP bar");
			xpBarPos12 = config.getInt("XP Bar Position (12)", Configuration.CATEGORY_GENERAL, 3, 1, 6, "Position of XP bar on-screen");
			xOffset12 = config.getInt("XP Bar X-Offset (12)", Configuration.CATEGORY_GENERAL, -96, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar X-offset. Only change in-game!!");
			yOffset12 = config.getInt("XP Bar Y-Offset (12)", Configuration.CATEGORY_GENERAL, 19, Integer.MIN_VALUE, Integer.MAX_VALUE, "XP bar Y-offset. Only change in-game!!");
			xpBarStyle12 = config.getInt("XP Bar Style (12)", Configuration.CATEGORY_GENERAL, 3, 1, 13, "XP bar style");
			xpBarWidth12 = config.getInt("XP Bar Width (12)", Configuration.CATEGORY_GENERAL, 100, 21, 125, "XP bar width");
			
		} catch (Exception e) {
			System.out.println("### WARNING! RPGCore could not load it's config files! ###");
		} finally {
			if (config.hasChanged()) {
				config.save();
			}
		}
	}
	
	public boolean getShowXp(int slot) {
		if (slot == 0) { return showXP1; }
		if (slot == 1) { return showXP2; }
		if (slot == 2) { return showXP3; }
		if (slot == 3) { return showXP4; }
		if (slot == 4) { return showXP5; }
		if (slot == 5) { return showXP6; }
		if (slot == 6) { return showXP7; }
		if (slot == 7) { return showXP8; }
		if (slot == 8) { return showXP9; }
		if (slot == 9) { return showXP10; }
		if (slot == 10){ return showXP11; }
		if (slot == 11){ return showXP12; }
		return false;
	}
	
	public boolean getShowIcon(int slot) {
		if (slot == 0) { return showIcon1; }
		if (slot == 1) { return showIcon2; }
		if (slot == 2) { return showIcon3; }
		if (slot == 3) { return showIcon4; }
		if (slot == 4) { return showIcon5; }
		if (slot == 5) { return showIcon6; }
		if (slot == 6) { return showIcon7; }
		if (slot == 7) { return showIcon8; }
		if (slot == 8) { return showIcon9; }
		if (slot == 9) { return showIcon10; }
		if (slot == 10){ return showIcon11; }
		if (slot == 11){ return showIcon12; }
		return false;
	}
	
	public int getXPTextType(int slot) {
		if (slot == 0) { return xpTextType1; }
		if (slot == 1) { return xpTextType2; }
		if (slot == 2) { return xpTextType3; }
		if (slot == 3) { return xpTextType4; }
		if (slot == 4) { return xpTextType5; }
		if (slot == 5) { return xpTextType6; }
		if (slot == 6) { return xpTextType7; }
		if (slot == 7) { return xpTextType8; }
		if (slot == 8) { return xpTextType9; }
		if (slot == 9) { return xpTextType10; }
		if (slot == 10){ return xpTextType11; }
		if (slot == 11){ return xpTextType12; }
		return 0;
	}
	
	public int getXPBarPos(int slot) {
		if (slot == 0) { return xpBarPos1; }
		if (slot == 1) { return xpBarPos2; }
		if (slot == 2) { return xpBarPos3; }
		if (slot == 3) { return xpBarPos4; }
		if (slot == 4) { return xpBarPos5; }
		if (slot == 5) { return xpBarPos6; }
		if (slot == 6) { return xpBarPos7; }
		if (slot == 7) { return xpBarPos8; }
		if (slot == 8) { return xpBarPos9; }
		if (slot == 9) { return xpBarPos10; }
		if (slot == 10){ return xpBarPos11; }
		if (slot == 11){ return xpBarPos12; }
		return 0;
	}
	
	public int getXPXOffset(int slot) {
		if (slot == 0) { return xOffset1; }
		if (slot == 1) { return xOffset2; }
		if (slot == 2) { return xOffset3; }
		if (slot == 3) { return xOffset4; }
		if (slot == 4) { return xOffset5; }
		if (slot == 5) { return xOffset6; }
		if (slot == 6) { return xOffset7; }
		if (slot == 7) { return xOffset8; }
		if (slot == 8) { return xOffset9; }
		if (slot == 9) { return xOffset10; }
		if (slot == 10){ return xOffset11; }
		if (slot == 11){ return xOffset12; }
		return 0;
	}
	
	public int getXPYOffset(int slot) {
		if (slot == 0) { return yOffset1; }
		if (slot == 1) { return yOffset2; }
		if (slot == 2) { return yOffset3; }
		if (slot == 3) { return yOffset4; }
		if (slot == 4) { return yOffset5; }
		if (slot == 5) { return yOffset6; }
		if (slot == 6) { return yOffset7; }
		if (slot == 7) { return yOffset8; }
		if (slot == 8) { return yOffset9; }
		if (slot == 9) { return yOffset10; }
		if (slot == 10){ return yOffset11; }
		if (slot == 11){ return yOffset12; }
		return 0;
	}
	
	public int getXPBarStyle(int slot) {
		if (slot == 0) { return xpBarStyle1; }
		if (slot == 1) { return xpBarStyle2; }
		if (slot == 2) { return xpBarStyle3; }
		if (slot == 3) { return xpBarStyle4; }
		if (slot == 4) { return xpBarStyle5; }
		if (slot == 5) { return xpBarStyle6; }
		if (slot == 6) { return xpBarStyle7; }
		if (slot == 7) { return xpBarStyle8; }
		if (slot == 8) { return xpBarStyle9; }
		if (slot == 9) { return xpBarStyle10; }
		if (slot == 10){ return xpBarStyle11; }
		if (slot == 11){ return xpBarStyle12; }
		return 0;
	}
	
	public int getXPBarWidth(int slot) {
		if (slot == 0) { return xpBarWidth1; }
		if (slot == 1) { return xpBarWidth2; }
		if (slot == 2) { return xpBarWidth3; }
		if (slot == 3) { return xpBarWidth4; }
		if (slot == 4) { return xpBarWidth5; }
		if (slot == 5) { return xpBarWidth6; }
		if (slot == 6) { return xpBarWidth7; }
		if (slot == 7) { return xpBarWidth8; }
		if (slot == 8) { return xpBarWidth9; }
		if (slot == 9) { return xpBarWidth10; }
		if (slot == 10){ return xpBarWidth11; }
		if (slot == 11){ return xpBarWidth12; }
		return 0;
	}
	
	public void setShowIcon(int slot, boolean par) {
		if (slot >= 0 && slot <= 11) {
			System.out.println("Setting showXP. Slot: " + slot + ", True/false: " + par);
			try {
				config.load();
				if (slot == 0) { config.get(Configuration.CATEGORY_GENERAL, "Show Icon (1)", par, "Show Skill Icon on slot's XP bar").set(par); }
				if (slot == 1) { config.get(Configuration.CATEGORY_GENERAL, "Show Icon (2)", par, "Show Skill Icon on slot's XP bar").set(par); }
				if (slot == 2) { config.get(Configuration.CATEGORY_GENERAL, "Show Icon (3)", par, "Show Skill Icon on slot's XP bar").set(par); }
				if (slot == 3) { config.get(Configuration.CATEGORY_GENERAL, "Show Icon (4)", par, "Show Skill Icon on slot's XP bar").set(par); }
				if (slot == 4) { config.get(Configuration.CATEGORY_GENERAL, "Show Icon (5)", par, "Show Skill Icon on slot's XP bar").set(par); }
				if (slot == 5) { config.get(Configuration.CATEGORY_GENERAL, "Show Icon (6)", par, "Show Skill Icon on slot's XP bar").set(par); }
				if (slot == 6) { config.get(Configuration.CATEGORY_GENERAL, "Show Icon (7)", par, "Show Skill Icon on slot's XP bar").set(par); }
				if (slot == 7) { config.get(Configuration.CATEGORY_GENERAL, "Show Icon (8)", par, "Show Skill Icon on slot's XP bar").set(par); }
				if (slot == 8) { config.get(Configuration.CATEGORY_GENERAL, "Show Icon (9)", par, "Show Skill Icon on slot's XP bar").set(par); }
				if (slot == 9) { config.get(Configuration.CATEGORY_GENERAL, "Show Icon (10)", par, "Show Skill Icon on slot's XP bar").set(par); }
				if (slot == 10) { config.get(Configuration.CATEGORY_GENERAL, "Show Icon (11)", par, "Show Skill Icon on slot's XP bar").set(par); }
				if (slot == 11) { config.get(Configuration.CATEGORY_GENERAL, "Show Icon (12)", par, "Show Skill Icon on slot's XP bar").set(par); }
			} catch (Exception e) {
				System.out.println("### WARNING! RPGCore could not load it's config files! ###");
			} finally {
				if (config.hasChanged()) {
					System.out.println("Saving config!");
					config.save();
					initConfig();
				} else {
					System.out.println("Config not changed.");
				}
			}
		}
	}
	
	public void setShowXp(int slot, boolean par) {
		if (slot >= 0 && slot <= 11) {
			System.out.println("Setting showXP. Slot: " + slot + ", True/false: " + par);
			try {
				config.load();
				if (slot == 0) { config.get(Configuration.CATEGORY_GENERAL, "Show XP (1)", par, "Show slot 1's XP bar").set(par); }
				if (slot == 1) { config.get(Configuration.CATEGORY_GENERAL, "Show XP (2)", par, "Show slot 2's XP bar").set(par); }
				if (slot == 2) { config.get(Configuration.CATEGORY_GENERAL, "Show XP (3)", par, "Show slot 3's XP bar").set(par); }
				if (slot == 3) { config.get(Configuration.CATEGORY_GENERAL, "Show XP (4)", par, "Show slot 4's XP bar").set(par); }
				if (slot == 4) { config.get(Configuration.CATEGORY_GENERAL, "Show XP (5)", par, "Show slot 5's XP bar").set(par); }
				if (slot == 5) { config.get(Configuration.CATEGORY_GENERAL, "Show XP (6)", par, "Show slot 6's XP bar").set(par); }
				if (slot == 6) { config.get(Configuration.CATEGORY_GENERAL, "Show XP (7)", par, "Show slot 7's XP bar").set(par); }
				if (slot == 7) { config.get(Configuration.CATEGORY_GENERAL, "Show XP (8)", par, "Show slot 8's XP bar").set(par); }
				if (slot == 8) { config.get(Configuration.CATEGORY_GENERAL, "Show XP (9)", par, "Show slot 9's XP bar").set(par); }
				if (slot == 9) { config.get(Configuration.CATEGORY_GENERAL, "Show XP (10)", par, "Show slot 10's XP bar").set(par); }
				if (slot == 10) { config.get(Configuration.CATEGORY_GENERAL, "Show XP (11)", par, "Show slot 11's XP bar").set(par); }
				if (slot == 11) { config.get(Configuration.CATEGORY_GENERAL, "Show XP (12)", par, "Show slot 12's XP bar").set(par); }
			} catch (Exception e) {
				System.out.println("### WARNING! RPGCore could not load it's config files! ###");
			} finally {
				if (config.hasChanged()) {
					System.out.println("Saving config!");
					config.save();
					initConfig();
				} else {
					System.out.println("Config not changed.");
				}
			}
		}
	}
	
	public void setXPTextType(int slot, int type) {
		if (slot >= 0 && slot <= 11) {
			System.out.println("Setting xpTextType. Slot: " + slot + ", Type: " + type);
			try {
				config.load();
				if (slot == 0) { config.get(Configuration.CATEGORY_GENERAL, "XP Text Type (1)", 0).set(type); }
				if (slot == 1) { config.get(Configuration.CATEGORY_GENERAL, "XP Text Type (2)", 0).set(type); }
				if (slot == 2) { config.get(Configuration.CATEGORY_GENERAL, "XP Text Type (3)", 0).set(type); }
				if (slot == 3) { config.get(Configuration.CATEGORY_GENERAL, "XP Text Type (4)", 0).set(type); }
				if (slot == 4) { config.get(Configuration.CATEGORY_GENERAL, "XP Text Type (5)", 0).set(type); }
				if (slot == 5) { config.get(Configuration.CATEGORY_GENERAL, "XP Text Type (6)", 0).set(type); }
				if (slot == 6) { config.get(Configuration.CATEGORY_GENERAL, "XP Text Type (7)", 0).set(type); }
				if (slot == 7) { config.get(Configuration.CATEGORY_GENERAL, "XP Text Type (8)", 0).set(type); }
				if (slot == 8) { config.get(Configuration.CATEGORY_GENERAL, "XP Text Type (9)", 0).set(type); }
				if (slot == 9) { config.get(Configuration.CATEGORY_GENERAL, "XP Text Type (10)", 0).set(type); }
				if (slot == 10) { config.get(Configuration.CATEGORY_GENERAL, "XP Text Type (11)", 0).set(type); }
				if (slot == 11) { config.get(Configuration.CATEGORY_GENERAL, "XP Text Type (12)", 0).set(type); }
			} catch (Exception e) {
				System.out.println("### WARNING! RPGCore could not load it's config files! ###");
			} finally {
				if (config.hasChanged()) {
					System.out.println("Saving config!");
					config.save();
					initConfig();
				} else {
					System.out.println("Config not changed.");
				}
			}
		}
	}
	
	public void setXPBarPos(int slot, int type) {
		if (slot >= 0 && slot <= 11) {
			System.out.println("Setting XP Bar Position. Slot: " + slot + ", Type: " + type);
			try {
				config.load();
				if (slot == 0) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Position (1)", 0).set(type); }
				if (slot == 1) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Position (2)", 0).set(type); }
				if (slot == 2) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Position (3)", 0).set(type); }
				if (slot == 3) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Position (4)", 0).set(type); }
				if (slot == 4) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Position (5)", 0).set(type); }
				if (slot == 5) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Position (6)", 0).set(type); }
				if (slot == 6) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Position (7)", 0).set(type); }
				if (slot == 7) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Position (8)", 0).set(type); }
				if (slot == 8) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Position (9)", 0).set(type); }
				if (slot == 9) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Position (10)", 0).set(type); }
				if (slot == 10) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Position (11)", 0).set(type); }
				if (slot == 11) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Position (12)", 0).set(type); }
			} catch (Exception e) {
				System.out.println("### WARNING! RPGCore could not load it's config files! ###");
			} finally {
				if (config.hasChanged()) {
					System.out.println("Saving config!");
					config.save();
					initConfig();
				} else {
					System.out.println("Config not changed.");
				}
			}
		}
	}
	
	public void setXPXOffset(int slot, int type) {
		if (slot >= 0 && slot <= 11) {
			System.out.println("Setting X Offset. Slot: " + slot + ", Type: " + type);
			try {
				config.load();
				if (slot == 0) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar X-Offset (1)", 0).set(type); }
				if (slot == 1) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar X-Offset (2)", 0).set(type); }
				if (slot == 2) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar X-Offset (3)", 0).set(type); }
				if (slot == 3) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar X-Offset (4)", 0).set(type); }
				if (slot == 4) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar X-Offset (5)", 0).set(type); }
				if (slot == 5) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar X-Offset (6)", 0).set(type); }
				if (slot == 6) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar X-Offset (7)", 0).set(type); }
				if (slot == 7) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar X-Offset (8)", 0).set(type); }
				if (slot == 8) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar X-Offset (9)", 0).set(type); }
				if (slot == 9) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar X-Offset (10)", 0).set(type); }
				if (slot == 10) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar X-Offset (11)", 0).set(type); }
				if (slot == 11) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar X-Offset (12)", 0).set(type); }
			} catch (Exception e) {
				System.out.println("### WARNING! RPGCore could not load it's config files! ###");
			} finally {
				if (config.hasChanged()) {
					System.out.println("Saving config!");
					config.save();
					initConfig();
				} else {
					System.out.println("Config not changed.");
				}
			}
		}
	}
	
	public void setXPYOffset(int slot, int type) {
		if (slot >= 0 && slot <= 11) {
			System.out.println("Setting Y Offset. Slot: " + slot + ", Type: " + type);
			try {
				config.load();
				if (slot == 0) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Y-Offset (1)", 0).set(type); }
				if (slot == 1) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Y-Offset (2)", 0).set(type); }
				if (slot == 2) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Y-Offset (3)", 0).set(type); }
				if (slot == 3) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Y-Offset (4)", 0).set(type); }
				if (slot == 4) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Y-Offset (5)", 0).set(type); }
				if (slot == 5) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Y-Offset (6)", 0).set(type); }
				if (slot == 6) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Y-Offset (7)", 0).set(type); }
				if (slot == 7) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Y-Offset (8)", 0).set(type); }
				if (slot == 8) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Y-Offset (9)", 0).set(type); }
				if (slot == 9) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Y-Offset (10)", 0).set(type); }
				if (slot == 10) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Y-Offset (11)", 0).set(type); }
				if (slot == 11) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Y-Offset (12)", 0).set(type); }
			} catch (Exception e) {
				System.out.println("### WARNING! RPGCore could not load it's config files! ###");
			} finally {
				if (config.hasChanged()) {
					System.out.println("Saving config!");
					config.save();
					initConfig();
				} else {
					System.out.println("Config not changed.");
				}
			}
		}
	}
	
	public void setXPBarStyle(int slot, int type) {
		if (slot >= 0 && slot <= 11) {
			System.out.println("Setting Y Offset. Slot: " + slot + ", Type: " + type);
			try {
				config.load();
				if (slot == 0) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Style (1)", 0).set(type); }
				if (slot == 1) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Style (2)", 0).set(type); }
				if (slot == 2) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Style (3)", 0).set(type); }
				if (slot == 3) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Style (4)", 0).set(type); }
				if (slot == 4) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Style (5)", 0).set(type); }
				if (slot == 5) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Style (6)", 0).set(type); }
				if (slot == 6) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Style (7)", 0).set(type); }
				if (slot == 7) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Style (8)", 0).set(type); }
				if (slot == 8) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Style (9)", 0).set(type); }
				if (slot == 9) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Style (10)", 0).set(type); }
				if (slot == 10) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Style (11)", 0).set(type); }
				if (slot == 11) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Style (12)", 0).set(type); }
			} catch (Exception e) {
				System.out.println("### WARNING! RPGCore could not load it's config files! ###");
			} finally {
				if (config.hasChanged()) {
					System.out.println("Saving config!");
					config.save();
					initConfig();
				} else {
					System.out.println("Config not changed.");
				}
			}
		}
	}
	
	public void setXPBarWidth(int slot, int type) {
		if (slot >= 0 && slot <= 11) {
			System.out.println("Setting Y Offset. Slot: " + slot + ", Type: " + type);
			try {
				config.load();
				if (slot == 0) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Width (1)", 0).set(type); }
				if (slot == 1) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Width (2)", 0).set(type); }
				if (slot == 2) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Width (3)", 0).set(type); }
				if (slot == 3) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Width (4)", 0).set(type); }
				if (slot == 4) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Width (5)", 0).set(type); }
				if (slot == 5) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Width (6)", 0).set(type); }
				if (slot == 6) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Width (7)", 0).set(type); }
				if (slot == 7) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Width (8)", 0).set(type); }
				if (slot == 8) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Width (9)", 0).set(type); }
				if (slot == 9) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Width (10)", 0).set(type); }
				if (slot == 10) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Width (11)", 0).set(type); }
				if (slot == 11) { config.get(Configuration.CATEGORY_GENERAL, "XP Bar Width (12)", 0).set(type); }
			} catch (Exception e) {
				System.out.println("### WARNING! RPGCore could not load it's config files! ###");
			} finally {
				if (config.hasChanged()) {
					System.out.println("Saving config!");
					config.save();
					initConfig();
				} else {
					System.out.println("Config not changed.");
				}
			}
		}
	}
	
	public void setGlobalSettings(boolean showGlobalXP, boolean globalLevelTop, boolean showGlobalLevel, boolean globalLevelLeft) {
		try {
			config.load();
			config.get(Configuration.CATEGORY_GENERAL, "Show Global XP", showGlobalXP, "Show the global XP bar").set(showGlobalXP);
			config.get(Configuration.CATEGORY_GENERAL, "Global XP Top/Bottom", globalLevelTop, "True to show xp at top of screen, false for bottom.").set(globalLevelTop);
			config.get(Configuration.CATEGORY_GENERAL, "Show Global Level", showGlobalLevel, "Show the level as text on the global XP bar").set(showGlobalLevel);
			config.get(Configuration.CATEGORY_GENERAL, "Global Text Left/Right", globalLevelLeft, "True to show the text on the left, false for right.").set(globalLevelLeft);
		} catch (Exception e) {
			System.out.println("### WARNING! RPGCore could not load it's config files! ###");
		} finally {
			if (config.hasChanged()) {
				System.out.println("Saving config!");
				config.save();
				initConfig();
			} else {
				System.out.println("Config not changed.");
			}
		}
	}
}
