package co.uk.silvania.rpgcore.network;

import co.uk.silvania.rpgcore.skills.EquippedSkills;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class EquippedSkillsPacket implements IMessage {
	
	String slot0;
	String slot1;
	String slot2;
	String slot3;
	String slot4;
	String slot5;
	String slot6;
	
	public EquippedSkillsPacket() {}
	
	public EquippedSkillsPacket(String slot0, String slot1, String slot2, String slot3, String slot4, String slot5, String slot6) {
		this.slot0 = slot0;
		this.slot1 = slot1;
		this.slot2 = slot2;
		this.slot3 = slot3;
		this.slot4 = slot4;
		this.slot5 = slot5;
		this.slot6 = slot6;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		slot0 = ByteBufUtils.readUTF8String(buf);
		slot1 = ByteBufUtils.readUTF8String(buf);
		slot2 = ByteBufUtils.readUTF8String(buf);
		slot3 = ByteBufUtils.readUTF8String(buf);
		slot4 = ByteBufUtils.readUTF8String(buf);
		slot5 = ByteBufUtils.readUTF8String(buf);
		slot6 = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, slot0);
		ByteBufUtils.writeUTF8String(buf, slot1);
		ByteBufUtils.writeUTF8String(buf, slot2);
		ByteBufUtils.writeUTF8String(buf, slot3);
		ByteBufUtils.writeUTF8String(buf, slot4);
		ByteBufUtils.writeUTF8String(buf, slot5);
		ByteBufUtils.writeUTF8String(buf, slot6);
	}
	
	public static class Handler implements IMessageHandler<EquippedSkillsPacket, IMessage> {
		
		@Override
		public IMessage onMessage(EquippedSkillsPacket message, MessageContext ctx) {
			EquippedSkills skills = (EquippedSkills) EquippedSkills.get((EntityPlayer) Minecraft.getMinecraft().thePlayer);
			System.out.println("Equipped Skills packet received.");
			
			skills.setSkill(0, message.slot0);
			skills.setSkill(1, message.slot1);
			skills.setSkill(2, message.slot2);
			skills.setSkill(3, message.slot3);
			skills.setSkill(4, message.slot4);
			skills.setSkill(5, message.slot5);
			skills.setSkill(6, message.slot6);
			return null;
		}
	}

}
