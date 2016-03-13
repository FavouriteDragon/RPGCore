package co.uk.silvania.rpgcore;

import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class LevelPacket implements IMessage {
	
	public int xp;
	public String skillId;
	
	public LevelPacket() {}
	
	public LevelPacket(int xp, String skillId) { 
		this.xp = xp;
		this.skillId = skillId;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		xp = ByteBufUtils.readVarShort(buf);
		skillId = ByteBufUtils.readUTF8String(buf);
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarShort(buf, xp);
		ByteBufUtils.writeUTF8String(buf, skillId);
	}
	
	public static class Handler implements IMessageHandler<LevelPacket, IMessage> {

		@Override
		public IMessage onMessage(LevelPacket message, MessageContext ctx) {
			SkillLevelBase level = (SkillLevelBase) SkillLevelBase.get((EntityPlayer) Minecraft.getMinecraft().thePlayer, message.skillId);
			System.out.println("PACKET RECEIVED! SKILLID: " + message.skillId + ", XP: " + message.xp);
			level.setXP(message.xp);
			return null;
		}
	}

}
