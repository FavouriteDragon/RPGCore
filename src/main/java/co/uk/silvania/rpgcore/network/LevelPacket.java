package co.uk.silvania.rpgcore.network;

import co.uk.silvania.rpgcore.RPGCore;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class LevelPacket implements IMessage {
	
	public float xp;
	public int val;
	public String skillId;
	
	public LevelPacket() {}
	
	public LevelPacket(float xp, int val, String skillId) { 
		this.xp = xp;
		this.val = val;
		this.skillId = skillId;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		xp = buf.readFloat();
		val = buf.readInt();
		skillId = ByteBufUtils.readUTF8String(buf);
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(xp);
		buf.writeInt(val);
		ByteBufUtils.writeUTF8String(buf, skillId);
	}
	
	public static class Handler implements IMessageHandler<LevelPacket, IMessage> {

		@Override
		public IMessage onMessage(LevelPacket message, MessageContext ctx) {
			RPGCore.proxy.syncLevelsWithClient(message, ctx);
			return null;
		}
	}

}
