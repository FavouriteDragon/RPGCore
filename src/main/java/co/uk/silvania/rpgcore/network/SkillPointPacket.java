package co.uk.silvania.rpgcore.network;

import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.skills.GlobalLevel;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class SkillPointPacket implements IMessage {
	
	int strAdd;
	int agiAdd;
	
	public SkillPointPacket() {}
	
	public SkillPointPacket(int strAdd, int agiAdd) {
		this.strAdd = strAdd;
		this.agiAdd = agiAdd;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		strAdd = buf.readInt();
		agiAdd = buf.readInt();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(strAdd);
		buf.writeInt(agiAdd);
	}

	public static class Handler implements IMessageHandler<SkillPointPacket, IMessage> {

		@Override
		public IMessage onMessage(SkillPointPacket message, MessageContext ctx) {
			EntityPlayer player = ctx.getServerHandler().playerEntity;
			World world = player.worldObj;
			System.out.println("Skill Point Packet get! STR add: " + message.strAdd + ", AGI add: " + message.agiAdd);
			
			GlobalLevel glevel = (GlobalLevel) GlobalLevel.get(player);
			
			if ((message.strAdd + message.agiAdd) <= glevel.skillPoints) {
				SkillLevelBase skillStr = SkillLevelBase.getSkillByID("skillStrength", player);
				SkillLevelBase skillAgi = SkillLevelBase.getSkillByID("skillAgility", player);
				for (int i = 0; i < message.strAdd; i++) { skillStr.forceLevelUp(); }
				for (int i = 0; i < message.agiAdd; i++) { skillAgi.forceLevelUp(); }
				glevel.setSkillPoints(glevel.skillPoints - (message.strAdd+message.agiAdd));
				
				RPGCore.network.sendTo(new LevelPacket(skillStr.getXP(), -1, skillStr.skillId), (EntityPlayerMP) player);
				RPGCore.network.sendTo(new LevelPacket(skillAgi.getXP(), -1, skillAgi.skillId), (EntityPlayerMP) player);
				
			} else {
				//If they're above glevel.skillPoints, the player 99.9% is using a hacked client to try and cheat it.
				//We won't accuse them, but we won't let them either.
				player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "Skill point server/client mismatch! Points not added."));
			}
			return null;
		}
	}
}
