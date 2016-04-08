package co.uk.silvania.rpgcore.network;

import java.util.ArrayList;

import co.uk.silvania.rpgcore.RPGUtils;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandRPGCore extends CommandBase {

	@Override
	public String getCommandName() {
		return "rpgcore";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return EnumChatFormatting.GREEN + "Type " + EnumChatFormatting.RED + "/rpgcore help" + EnumChatFormatting.GREEN + " for more information.";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		String red = "\u00A7c";
		String green = "\u00A7a";
		
		if (args.length == 0) {
			sender.addChatMessage(new ChatComponentText(green + "Use " + red + "/rpgcore help" + green + " for help."));
			return;
		}
		if (args[0].equalsIgnoreCase("help")) {
			sender.addChatMessage(new ChatComponentText(red + "/rpgcore help" + green + " - Displays this help message"));
			sender.addChatMessage(new ChatComponentText(red + "/rpgcore addxp <skillid> <amount> [player]" + green + " - Add amount XP to skill ID, to yourself or player."));
		}
		if (args[0].equalsIgnoreCase("addxp")) {
			if (args.length == 4) {
				ArrayList<EntityPlayer> players = (ArrayList<EntityPlayer>) sender.getEntityWorld().playerEntities;
				for (int i = 0; i < players.size(); i++) {
					if (players.get(i).getCommandSenderName().equalsIgnoreCase(args[3])) {
						sender.addChatMessage(new ChatComponentText(green + SkillLevelBase.addXPToSkill(RPGUtils.parseInt(args[2]), players.get(i), args[1])));
						return;
					} else {
						sender.addChatMessage(new ChatComponentText(red + "Player not found."));
					}
				}
			} else if (args.length == 3) {
				if (sender instanceof EntityPlayer) {
					sender.addChatMessage(new ChatComponentText(green + SkillLevelBase.addXPToSkill(RPGUtils.parseInt(args[2]), (EntityPlayer) sender, args[1])));
				} else {
					sender.addChatMessage(new ChatComponentText(red + "Player name is required when on server console!"));
				}
			} else {
				sender.addChatMessage(new ChatComponentText(red + "Invalid arguments! /rpgcore addxp <skillid> <amount> [player]"));
			}
		}
	}
}
