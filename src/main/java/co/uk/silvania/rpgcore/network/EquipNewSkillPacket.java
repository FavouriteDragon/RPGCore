package co.uk.silvania.rpgcore.network;

import co.uk.silvania.rpgcore.skills.EquippedSkills;
import co.uk.silvania.rpgcore.skills.GlobalLevel;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class EquipNewSkillPacket implements IMessage {
	
	int slotId;
	String skillId;
	
	public EquipNewSkillPacket() {}
	
	public EquipNewSkillPacket(int slot, String skill) {
		slotId = slot;
		skillId = skill;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		slotId = ByteBufUtils.readVarShort(buf);
		skillId = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarShort(buf, slotId);
		ByteBufUtils.writeUTF8String(buf, skillId);
	}

	public static class Handler implements IMessageHandler<EquipNewSkillPacket, IMessage> {

		@Override
		public IMessage onMessage(EquipNewSkillPacket message, MessageContext ctx) {
			EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get(ctx.getServerHandler().playerEntity);
			SkillLevelBase newSkill = SkillLevelBase.getSkillByID(message.skillId, ctx.getServerHandler().playerEntity);
			GlobalLevel glevel = (GlobalLevel) GlobalLevel.get((EntityPlayer) ctx.getServerHandler().playerEntity);
			
			for (int i = 0; i < equippedSkills.skillSlots; i++) {
				SkillLevelBase skill = SkillLevelBase.getSkillByID(equippedSkills.getSkillInSlot(i), ctx.getServerHandler().playerEntity);	
				
				if (skill != null) {
					if (skill.skillId.equals(message.skillId)) {
						System.out.println("Duplicate skill detected. Removing...");
						equippedSkills.setSkill(i, "");
					}
					
					if (newSkill != null) {
						for (int j = 0; j < newSkill.incompatibleSkills.size(); j++) {
							System.out.println("Iterating. " + j + ": " + newSkill.incompatibleSkills.get(j));
							if (newSkill.incompatibleSkills.get(j).equals(skill.skillId)) {
								System.out.println("Incompatable skill " + newSkill.incompatibleSkills.get(j) + " detected. Incompatible with " + skill.skillId + ". Removing...?");
								System.out.println("Slot: " + equippedSkills.findSkillSlot(newSkill.incompatibleSkills.get(j)));
								if (newSkill.canSkillBeEquipped(ctx.getServerHandler().playerEntity)) {
									if (glevel.slotUnlockedLevel(message.slotId) <= glevel.getLevel()) {
										equippedSkills.setSkill(equippedSkills.findSkillSlot(newSkill.incompatibleSkills.get(j)), "");
									}
								}
							}
						}
					}
				}
			}
			
			equippedSkills.setSkill(message.slotId, message.skillId);
			
			
			
			return new EquippedSkillsPacket(
				equippedSkills.getSkillInSlot(0), 
				equippedSkills.getSkillInSlot(1), 
				equippedSkills.getSkillInSlot(2), 
				equippedSkills.getSkillInSlot(3), 
				equippedSkills.getSkillInSlot(4), 
				equippedSkills.getSkillInSlot(5), 
				equippedSkills.getSkillInSlot(6),
				equippedSkills.getSkillInSlot(7), 
				equippedSkills.getSkillInSlot(8), 
				equippedSkills.getSkillInSlot(9), 
				equippedSkills.getSkillInSlot(10), 
				equippedSkills.getSkillInSlot(11)
			);
		}
	}
}
