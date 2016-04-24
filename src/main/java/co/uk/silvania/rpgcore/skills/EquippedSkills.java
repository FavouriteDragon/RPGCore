package co.uk.silvania.rpgcore.skills;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class EquippedSkills implements IExtendedEntityProperties {
	
	public String skillId0;
	public String skillId1;
	public String skillId2;
	public String skillId3;
	public String skillId4;
	public String skillId5;
	public String skillId6;
	public String skillId7;
	public String skillId8;
	public String skillId9;
	public String skillId10;
	public String skillId11;
	
	public int skillSlots = 12;
	
	public EquippedSkills() {}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = new NBTTagCompound();
		//Empty skills prevent accidentally setting them to null; which wipes the data entirely. 
		nbt.setString("slot0", skillId0 + "");
		nbt.setString("slot1", skillId1 + "");
		nbt.setString("slot2", skillId2 + "");
		nbt.setString("slot3", skillId3 + "");
		nbt.setString("slot4", skillId4 + "");
		nbt.setString("slot5", skillId5 + "");
		nbt.setString("slot6", skillId6 + "");
		nbt.setString("slot7", skillId7 + "");
		nbt.setString("slot8", skillId8 + "");
		nbt.setString("slot9", skillId9 + "");
		nbt.setString("slot10", skillId10 + "");
		nbt.setString("slot11", skillId11 + "");
		
		compound.setTag("equippedSkills", nbt);		
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = (NBTTagCompound) compound.getTag("equippedSkills");
		skillId0 = nbt.getString("slot0") + "";
		skillId1 = nbt.getString("slot1") + "";
		skillId2 = nbt.getString("slot2") + "";
		skillId3 = nbt.getString("slot3") + "";
		skillId4 = nbt.getString("slot4") + "";
		skillId5 = nbt.getString("slot5") + "";
		skillId6 = nbt.getString("slot6") + "";
		skillId7 = nbt.getString("slot7") + "";
		skillId8 = nbt.getString("slot8") + "";
		skillId9 = nbt.getString("slot9") + "";
		skillId10 = nbt.getString("slot10") + "";
		skillId11 = nbt.getString("slot11") + "";
	}

	@Override public void init(Entity entity, World world) {}
	
	public void setSkill(int slot, String skillId) {
		if (slot == 0) { skillId0 = skillId; }
		if (slot == 1) { skillId1 = skillId; }
		if (slot == 2) { skillId2 = skillId; }
		if (slot == 3) { skillId3 = skillId; }
		if (slot == 4) { skillId4 = skillId; }
		if (slot == 5) { skillId5 = skillId; }
		if (slot == 6) { skillId6 = skillId; }
		if (slot == 7) { skillId7 = skillId; }
		if (slot == 8) { skillId8 = skillId; }
		if (slot == 9) { skillId9 = skillId; }
		if (slot == 10) { skillId10 = skillId; }
		if (slot == 11) { skillId11 = skillId; }
	}
	
	public static IExtendedEntityProperties get(EntityPlayer player) {
		return player.getExtendedProperties("equippedSkills");
	}
	
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		((EquippedSkills) EquippedSkills.get(event.entityPlayer)).copy((EquippedSkills) EquippedSkills.get(event.original));
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties("equippedSkills", new EquippedSkills());
		}
	}
	
	
	public void copy(EquippedSkills properties) {
		skillId0 = properties.skillId0;
		skillId1 = properties.skillId1;
		skillId2 = properties.skillId2;
		skillId3 = properties.skillId3;
		skillId4 = properties.skillId4;
		skillId5 = properties.skillId5;
		skillId6 = properties.skillId6;
		skillId7 = properties.skillId7;
		skillId8 = properties.skillId8;
		skillId9 = properties.skillId9;
		skillId10 = properties.skillId10;
		skillId11 = properties.skillId11;
	}
	
	public String getSkillInSlot(int slot) {
		if (slot == 0 && skillId0 != null) { return skillId0; }
		if (slot == 1 && skillId1 != null) { return skillId1; }
		if (slot == 2 && skillId2 != null) { return skillId2; }
		if (slot == 3 && skillId3 != null) { return skillId3; }
		if (slot == 4 && skillId4 != null) { return skillId4; }
		if (slot == 5 && skillId5 != null) { return skillId5; }
		if (slot == 6 && skillId6 != null) { return skillId6; }
		if (slot == 7 && skillId7 != null) { return skillId7; }
		if (slot == 8 && skillId8 != null) { return skillId8; }
		if (slot == 9 && skillId9 != null) { return skillId9; }
		if (slot == 10 && skillId10 != null) { return skillId10; }
		if (slot == 11 && skillId11 != null) { return skillId11; }
		return " ";
	}
	
	public int findSkillSlot(String skillId) {
		for (int i = 0; i < skillSlots; i++) {
			if (getSkillInSlot(i).equals(skillId)) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean isSkillEquipped(String skillId) {
		return findSkillSlot(skillId) != -1;
	}
	
	public void clearSkills() {
		for (int i = 0; i < skillSlots; i++) {
			setSkill(i, "");
		}
	}
}
