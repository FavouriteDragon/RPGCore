package co.uk.silvania.rpgcore.skills;

import java.util.ArrayList;

import co.uk.silvania.rpgcore.RegisterSkill;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class SkillLevelBase {
	
	public int xp;
	public int level;
	public ResourceLocation skillIcon;
	public int iconX;
	public int iconZ;
	public String skillName;
	public String skillId;
	public double levelMultiplier = 1.0; //Higher value = slower levelling.
	
	public ArrayList<String> incompatableSkills = new ArrayList<String>();
	public ArrayList<String> requiredSkills = new ArrayList<String>();
	
	public SkillLevelBase() {
		this.xp = 0;
	}
	
	public void addXP(int xpAdd, EntityPlayer player) {
		EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get(player);
		//We check on your behalf to make sure the skill is equipped before allowing the XP to be added.
		if (equippedSkills.isSkillEquipped(skillId)) {
			xp += xpAdd;
		}
	}
	
	public int getXP() {
		return xp;
	}
	
	public String getXPForPrint() {
		return getXP() + " / " + getXpForLevel(getLevel()+1);
	}
	
	public int getLevel() {
		int base = 83;
		int previousXp = 83;
		int level = 1;
		
		while (xp >= previousXp) {
			//Level curve algorithm
			//Takes the base value (83; XP required to reach level 2)
			//Then adds a percentage based on the skill multiplier and their current level.
			//The last part (level/10*10) plays well with the fact it's an integer.
			//Dividing int by int will only give a whole number; so 1-10 / 10 will always give 0,
			// 11-20 / 10 = 1, 21-30/10 = 2 and so on.
			//So, for every level you go up, it gets a little harder - but every 10 levels it jumps an extra bit.
			//levelMultiplier can be set by each skill to adjust the rate it increases. Higher numbers are slower.
			//I personally recommend 2.0, it works well - but it's up to the developer.
			//For comparison, 50,000 XP at 1.0 gives level 43 (almost 44), and at 2.0 gives mid-39.
			//Printout of levels up to above 50k XP at 1.0 and 2.0: http://pastebin.com/LxrtsLAf
			//
			//Of course you can use any number from 0.0001 to Float.MAX_VALUE - but the latter will be near impossible to level.
			//Finally, I recommend developers make the value configurable. Servers have varying difficulty, and admins may want to tweak it to match.
			previousXp += base + ((base / 100.0) * ((level*levelMultiplier) * (35 + ((level/10)*10))));
			level++;
		}
		
		return level;
		
	}
	
	public int getXpForLevel(int level) {
		int base = 83;
		int xpForLevel = 83;
		
		for (int i = 1; i < level; i++) {
			System.out.println("[GetLevel] XP: " + xp + ", Previous XP: " + xpForLevel + ", Level: " + i + ", level/10: " + (i/10));
			xpForLevel += base + ((base / 100.0) * ((i*levelMultiplier) * (35 + ((i/10)*10))));
		}
		return xpForLevel;
	}
	
	public int xpToNextLevel() {		
		return (getXpForLevel(getLevel())) - getXP();
	}
	
	public void setLevel() {
		level = (int) Math.round(levelMultiplier * Math.sqrt(xp));
		System.out.println("Level: " + level);
	}
	
	public void setXP(int xpSet) {
		xp = xpSet;
	}
	
	public static IExtendedEntityProperties get(EntityPlayer player, String skillId) {
		return player.getExtendedProperties(skillId);
	}
	
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		((SkillLevelBase) SkillLevelBase.get(event.entityPlayer, skillId)).copy((SkillLevelBase) SkillLevelBase.get(event.original, skillId));
	}
	
	public void copy(SkillLevelBase properties) {
		xp = properties.xp;
	}
	
	public static SkillLevelBase getSkillByID(String skillId, EntityPlayer player) {
		for (int i = 0; i < RegisterSkill.skillList.size(); i++) {
			SkillLevelBase skillBase = RegisterSkill.skillList.get(i);
			SkillLevelBase skill = (SkillLevelBase) skillBase.get(player, skillBase.skillId);
			
			if (skill.skillId.equals(skillId)) {
				return skill;
			}
		}
		return null;
	}
}
