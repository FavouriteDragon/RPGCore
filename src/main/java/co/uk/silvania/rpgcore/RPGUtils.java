package co.uk.silvania.rpgcore;

public class RPGUtils {
	
	//I have written this class, but everything is considered public domain. Feel free to copy-paste anything from here.
	//as it might come in handy in other mods - but not worth making a lib for.
	//Just comment above the method saying "Nicked this from Flenix" or something ;)
	
	//Gets the PIXEL length of a string, for GUI right-align.
	public static int getStringLength(String str) {
		int i = 0;
		int l = 0;
		int t = 0;
		int brk = 0;
		int other = 0;
		
		for (int j = 0; j < str.length(); j++) {
			Character c = str.charAt(j);
			if (c.toString().equalsIgnoreCase("i") || c.toString().equalsIgnoreCase("!")
					|| c.toString().equalsIgnoreCase(".") || c.toString().equalsIgnoreCase(":")
					|| c.toString().equalsIgnoreCase(",") || c.toString().equalsIgnoreCase(";")) { i++; }
			else if (c.toString().equalsIgnoreCase("l")) { l++; }
			else if (c.toString().equalsIgnoreCase("t") || c.toString().equalsIgnoreCase("[") || c.toString().equalsIgnoreCase("]") || c.toString().equalsIgnoreCase(" ")) { t++; }
			else if (c.toString().equalsIgnoreCase("(") || c.toString().equalsIgnoreCase(")")) { brk++; }
			else { other++; }
		}
		
		return (i * 2) + (l * 3) + (t * 4) + (brk * 5) + (other * 6);
	}
	
	public static int parseInt(String s) {
		try {
			return Integer.parseInt("" + s);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

}
