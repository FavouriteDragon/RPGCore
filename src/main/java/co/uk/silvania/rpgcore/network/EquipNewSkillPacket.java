package co.uk.silvania.rpgcore.network;

import co.uk.silvania.rpgcore.skills.EquippedSkills;
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
			EquippedSkills skills = (EquippedSkills) EquippedSkills.get(ctx.getServerHandler().playerEntity);
			skills.setSkill(message.slotId, message.skillId);
			
			return new EquippedSkillsPacket(
				skills.getSkillInSlot(0), 
				skills.getSkillInSlot(1), 
				skills.getSkillInSlot(2), 
				skills.getSkillInSlot(3), 
				skills.getSkillInSlot(4), 
				skills.getSkillInSlot(5), 
				skills.getSkillInSlot(6)
			);
		}
	}
}
