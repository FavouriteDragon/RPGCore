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
	
	public int skillSlots = 7;
	
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
		compound.setTag("equippedSkills", nbt);		
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = (NBTTagCompound) compound.getTag("equippedSkills");
		skillId0 = nbt.getString("slot0");
		skillId1 = nbt.getString("slot1");
		skillId2 = nbt.getString("slot2");
		skillId3 = nbt.getString("slot3");
		skillId4 = nbt.getString("slot4");
		skillId5 = nbt.getString("slot5");
		skillId6 = nbt.getString("slot6");
	}

	@Override public void init(Entity entity, World world) {}
	
	public void setSkill(int slot, String skillId) {
		System.out.println("Setting skill! Slot: " + slot + ", skill ID: " + skillId);
		if (slot == 0) { skillId0 = skillId; }
		if (slot == 1) { skillId1 = skillId; }
		if (slot == 2) { skillId2 = skillId; }
		if (slot == 3) { skillId3 = skillId; }
		if (slot == 4) { skillId4 = skillId; }
		if (slot == 5) { skillId5 = skillId; }
		if (slot == 6) { skillId6 = skillId; }
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
	}
	
	public String getSkillInSlot(int slot) {
		if (slot == 0 && skillId0 != null) { return skillId0; }
		if (slot == 1 && skillId1 != null) { return skillId1; }
		if (slot == 2 && skillId2 != null) { return skillId2; }
		if (slot == 3 && skillId3 != null) { return skillId3; }
		if (slot == 4 && skillId4 != null) { return skillId4; }
		if (slot == 5 && skillId5 != null) { return skillId5; }
		if (slot == 6 && skillId6 != null) { return skillId6; }
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
}
