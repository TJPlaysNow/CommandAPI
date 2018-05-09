package com.tjplaysnow.commandapi.main;

import com.tjplaysnow.commandapi.commandsbase.Command;
import com.tjplaysnow.commandapi.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuperCommand extends Command {
	
	public SuperCommand() {
		super("super", "The most super command ever.", "Super.Command", "...");
		setCommand((sender, args) -> {
			if (args.length == 0) {
				sendHelp(sender);
				return true;
			} else if (args[0].equalsIgnoreCase("help")){
				sendHelp(sender);
			} else {
				if (!(sender instanceof Player)) {
					sender.sendMessage("§cUh oh, this is a player only command.");
					return true;
				}
			}
			return false;
		});
		
		addSubCommand(new SubCommand("test", (sender, args) -> {
			Player player = (Player) sender;
			player.sendMessage("This was a test command.");
			return true;
		}));
		
		SubCommand subCommand = new SubCommand("sendnudes", (sender, args) -> {
			Player player = (Player) sender;
			player.sendMessage("§7Ha. ha. ha.. You wish.");
			return true;
		});
		
		subCommand.addSubCommand(new SubCommand("plz", (sender, args) -> {
			Player player = (Player) sender;
			player.sendMessage("Hmm, maybe.");
			return true;
		}));
	}
	
	private void sendHelp(CommandSender sender) {
		sender.sendMessage("§eIf it is help you seek, from me you will not get it.");
	}
}