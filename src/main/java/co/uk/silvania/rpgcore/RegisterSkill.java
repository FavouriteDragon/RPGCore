package co.uk.silvania.rpgcore;

import java.util.ArrayList;

import co.uk.silvania.rpgcore.skills.SkillLevelBase;

public class RegisterSkill {
	
	public static ArrayList<SkillLevelBase> skillList = new ArrayList<SkillLevelBase>();
	
	public RegisterSkill() {}
	
	public static void register(SkillLevelBase skill) {
		skillList.add(skill);
		System.out.println("[RPGCore] Skill " + skill.skillName() + " loaded successfully!");
	}
	
	public static String getDisplayNameForSkill(String skillId) {
		for (int i = 0; i < skillList.size(); i++) {
			if (skillList.get(i).skillId.equals(skillId)) {
				return skillList.get(i).skillName();
			}
		}
		return "";
	}

}
