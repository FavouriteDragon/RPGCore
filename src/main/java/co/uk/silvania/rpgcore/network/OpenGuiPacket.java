package co.uk.silvania.rpgcore.network;

import co.uk.silvania.rpgcore.RPGCore;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class OpenGuiPacket implements IMessage {
	
	int guiId;
	
	public OpenGuiPacket() {}
	
	public OpenGuiPacket(int guiID) {
		this.guiId = guiID;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		guiId = ByteBufUtils.readVarShort(buf);
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarShort(buf, guiId);
	}

	public static class Handler implements IMessageHandler<OpenGuiPacket, IMessage> {

		@Override
		public IMessage onMessage(OpenGuiPacket message, MessageContext ctx) {
			EntityPlayer player = ctx.getServerHandler().playerEntity;
			World world = player.worldObj;
			player.openGui(RPGCore.instance, message.guiId, world, (int) player.posX, (int) player.posY, (int) player.posZ);
			return null;
		}
	}
}
