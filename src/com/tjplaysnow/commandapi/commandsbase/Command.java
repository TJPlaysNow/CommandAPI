package com.tjplaysnow.commandapi.commandsbase;

import com.tjplaysnow.commandapi.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;

public abstract class Command {
	
	private String label;
	private String description;
	private String permission;
	private String usage;
	private String permissionError;
	
	private BiPredicate<CommandSender, String[]> command;
	private List<SubCommand> subCommands;
	
	private String castPlayerError = "§cUh oh, you have to be a player for this command.";
	private boolean castPlayer = false;
	
	public Command(String label, String description, String permission, String usage) {
		this.label = label;
		this.description = description;
		this.permission = permission;
		this.usage = usage;
		permissionError = "§cUh oh, you don't have permission for this command.";
		subCommands = new ArrayList<>();
		command = (sender, args) -> false;
	}
	
	public Command(String label, String description, String permission) {
		this.label = label;
		this.description = description;
		this.permission = permission;
		usage = "";
		permissionError = "§cUh oh, you don't have permission for this command.";
		subCommands = new ArrayList<>();
		command = (sender, args) -> false;
	}
	
	public Command(String label, String description) {
		this.label = label;
		this.description = description;
		permission = "";
		usage = "";
		permissionError = "§cUh oh, you don't have permission for this command.";
		subCommands = new ArrayList<>();
		command = (sender, args) -> false;
	}
	
	public Command(String label) {
		this.label = label;
		description = "";
		permission = "";
		usage = "";
		permissionError = "§cUh oh, you don't have permission for this command.";
		subCommands = new ArrayList<>();
		command = (sender, args) -> false;
	}
	
	public void addSubCommand(SubCommand subCommand) {
		subCommands.add(subCommand);
	}
	
	protected void setCommand(BiPredicate<CommandSender, String[]> command) {
		this.command = command;
	}
	
	public boolean onCommand(CommandSender sender, String[] args) {
		if (castPlayer) {
			if (!(sender instanceof Player)) {
				if (!castPlayerError.equalsIgnoreCase("")) {
					sender.sendMessage(castPlayerError);
				}
				return false;
			}
		}
		if (args.length > 1) {
			if (subCommands.size() > 1) {
				for (Command subCommand : subCommands) {
					if (args[0].equalsIgnoreCase(subCommand.label)) {
						if (subCommand.getPermission().equals("") || sender.hasPermission(subCommand.getPermission())) {
							List<String> a = new ArrayList<>(Arrays.asList(args));
							a.remove(0);
							boolean ret = subCommand.onCommand(sender, (String[]) a.toArray());
							if (ret) {
								return true;
							}
						} else {
							sender.sendMessage(subCommand.getPermissionError());
						}
					}
				}
			}
		}
		return command.test(sender, args);
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public String getUsage() {
		return usage;
	}
	
	public String getPermissionError() {
		return permissionError;
	}
}