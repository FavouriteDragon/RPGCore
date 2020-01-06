package co.uk.silvania.rpgcore.network;

import co.uk.silvania.rpgcore.RPGUtils;
import co.uk.silvania.rpgcore.skills.EquippedSkills;
import co.uk.silvania.rpgcore.skills.GlobalLevel;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class EquipNewSkillPacket implements IMessage {

	int slotId;
	String skillId;

	public EquipNewSkillPacket() {
	}

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
			EntityPlayerMP player = ctx.getServerHandler().player;

			EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get(player);
			SkillLevelBase newSkill = SkillLevelBase.getSkillByID(message.skillId, player);
			GlobalLevel glevel = (GlobalLevel) GlobalLevel.get(player);

			for (int i = 0; i < equippedSkills.skillSlots; i++) {
				SkillLevelBase skill = SkillLevelBase.getSkillByID(equippedSkills.getSkillInSlot(i), player);

				if (skill != null) {
					RPGUtils.prtln(player.getDisplayName() + " is attempting to equip " + skill.skillName());

					if (skill.skillId.equals(message.skillId)) {
						RPGUtils.prtln("Duplicate skill detected. Removing...");
						equippedSkills.setSkill(i, "");
					}

					if (newSkill != null) {
						for (int j = 0; j < newSkill.incompatibleSkills.size(); j++) {
							RPGUtils.prtln("Iterating. " + j + ": " + newSkill.incompatibleSkills.get(j));
							if (newSkill.incompatibleSkills.get(j).equals(skill.skillId)) {
								RPGUtils.prtln("Incompatable skill " + newSkill.incompatibleSkills.get(j) + " detected. Incompatible with " + skill.skillId + ". Removing...?");
								RPGUtils.prtln("Slot: " + equippedSkills.findSkillSlot(newSkill.incompatibleSkills.get(j)));
								if (newSkill.canSkillBeEquipped(player)) {
									if (glevel.slotUnlockedLevel(message.slotId) <= glevel.getLevel()) {
										RPGUtils.verbose(player + " has equipped skill " + skill.skillName() + " to slot " + message.slotId);
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
