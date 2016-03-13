package co.uk.silvania.rpgcore;

import java.util.ArrayList;

import co.uk.silvania.rpgcore.skills.SkillLevelBase;

public class RegisterSkill {
	
	public static ArrayList<SkillLevelBase> skillList = new ArrayList<SkillLevelBase>();
	
	public RegisterSkill() {}
	
	public static void register(SkillLevelBase skill) {
		skillList.add(skill);
		System.out.println("[RPGCore] Skill " + skill.skillName + " loaded successfully!");
	}

}
