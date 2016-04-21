package co.uk.silvania.rpgcore.network;

import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.skills.EquippedSkills;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class EquippedSkillsPacket implements IMessage {
	
	public String slot0;
	public String slot1;
	public String slot2;
	public String slot3;
	public String slot4;
	public String slot5;
	public String slot6;
	public String slot7;
	public String slot8;
	public String slot9;
	public String slot10;
	public String slot11;
	
	public EquippedSkillsPacket() {}
	
	public EquippedSkillsPacket(String slot0, String slot1, String slot2, String slot3, String slot4, String slot5, 
			String slot6, String slot7, String slot8, String slot9, String slot10, String slot11) {
		this.slot0 = slot0;
		this.slot1 = slot1;
		this.slot2 = slot2;
		this.slot3 = slot3;
		this.slot4 = slot4;
		this.slot5 = slot5;
		this.slot6 = slot6;
		this.slot7 = slot7;
		this.slot8 = slot8;
		this.slot9 = slot9;
		this.slot10 = slot10;
		this.slot11 = slot11;
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
		slot7 = ByteBufUtils.readUTF8String(buf);
		slot8 = ByteBufUtils.readUTF8String(buf);
		slot9 = ByteBufUtils.readUTF8String(buf);
		slot10 = ByteBufUtils.readUTF8String(buf);
		slot11 = ByteBufUtils.readUTF8String(buf);
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
		ByteBufUtils.writeUTF8String(buf, slot7);
		ByteBufUtils.writeUTF8String(buf, slot8);
		ByteBufUtils.writeUTF8String(buf, slot9);
		ByteBufUtils.writeUTF8String(buf, slot10);
		ByteBufUtils.writeUTF8String(buf, slot11);
	}
	
	public static class Handler implements IMessageHandler<EquippedSkillsPacket, IMessage> {
		
		@Override
		public IMessage onMessage(EquippedSkillsPacket message, MessageContext ctx) {
			RPGCore.proxy.syncEquippedSkillsWithClient(message, ctx);
			return null;
		}
	}

}
