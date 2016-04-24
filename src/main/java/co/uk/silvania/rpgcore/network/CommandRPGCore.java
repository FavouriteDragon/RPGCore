package co.uk.silvania.rpgcore.network;

import java.util.ArrayList;

import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.RPGUtils;
import co.uk.silvania.rpgcore.RegisterSkill;
import co.uk.silvania.rpgcore.skills.EquippedSkills;
import co.uk.silvania.rpgcore.skills.GlobalLevel;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

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
			sender.addChatMessage(new ChatComponentText(red + "/rpgcore list" + green + " - List all currently loaded skills"));
			sender.addChatMessage(new ChatComponentText(red + "/rpgcore reset <player>" + green + " - Reset ALL of the specified players skills and XP."));
		}
		if (args[0].equalsIgnoreCase("addxp") || args[0].equalsIgnoreCase("xpadd")) {
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
		if (args[0].equalsIgnoreCase("list")) {
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < RegisterSkill.skillList.size(); i++) {
				SkillLevelBase skill = RegisterSkill.skillList.get(i);
				list.add(skill.nameFormat() + "[" + skill.shortName() + "] " + skill.skillName() + " (" + skill.skillId + ")\n");
			}

			IChatComponent chat = new ChatComponentText(green + "Hover here to view skill list.");
			chat.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(list.toString())));
			
			sender.addChatMessage(new ChatComponentText(green + "").appendSibling(chat));
		}
		if (args[0].equalsIgnoreCase("reset")) {
			if (args[1].isEmpty()) {
				sender.addChatMessage(new ChatComponentText(red + "Please specify a player!"));
			}
			ArrayList<EntityPlayer> players = (ArrayList<EntityPlayer>) sender.getEntityWorld().playerEntities;
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).getCommandSenderName().equalsIgnoreCase(args[1])) {
					EntityPlayer player = players.get(i);
					for (int j = 0; j < RegisterSkill.skillList.size(); j++) {
						SkillLevelBase skillBase = RegisterSkill.skillList.get(j);
						SkillLevelBase skill = (SkillLevelBase) skillBase.get(player, skillBase.skillId);
						
						skill.setXP(0);
						RPGCore.network.sendTo(new LevelPacket(skill.getXP(), -1, skill.skillId), (EntityPlayerMP) player);
					}
					GlobalLevel glevel = (GlobalLevel) GlobalLevel.get(player);
					EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get(player);
					
					glevel.setXP(0);
					glevel.setSkillPoints(0);
					
					equippedSkills.clearSkills();
					RPGCore.network.sendTo(new EquippedSkillsPacket("", "", "", "", "", "", "", "", "", "", "", ""), (EntityPlayerMP) player);
					RPGCore.network.sendTo(new LevelPacket((int)(glevel.getXPGlobal()*10), glevel.getSkillPoints(), glevel.skillId), (EntityPlayerMP) player);
					player.addChatMessage(new ChatComponentText(red + "[RPGCore] Your skills have been reset by an admin."));
					
					sender.addChatMessage(new ChatComponentText(green + player.getDisplayName() + "'s skills have been reset."));
					return;
				}
			}
			sender.addChatMessage(new ChatComponentText(red + "Player not found."));
		}
	}
}
