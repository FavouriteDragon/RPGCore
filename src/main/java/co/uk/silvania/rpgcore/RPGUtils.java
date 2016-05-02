package co.uk.silvania.rpgcore;

public class RPGUtils {
	
	//I have written this class, but everything is considered public domain. Feel free to copy-paste anything from here.
	//as it might come in handy in other mods - but not worth making a lib for.
	//Just comment above the method saying "Nicked this from Flenix" or something ;)
	
	public static int parseInt(String s) {
		try {
			return Integer.parseInt("" + s);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}
	
	public static void prtln(String str) {
		if (RPGCoreConfig.debugMode) {
			System.out.println("[RPGCore] " + str);
		}
	}
	
	public static void verbose(String str) {
		if (RPGCoreConfig.verbose) {
			System.out.println("[RPGCore] " + str);
		}
	}

}
