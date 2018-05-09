package com.tjplaysnow.commandapi.commands;

import com.tjplaysnow.commandapi.commandsbase.Command;
import org.bukkit.command.CommandSender;

import java.util.function.BiPredicate;

public class BasicCommand extends Command {
	
	public BasicCommand(String label, String description, String permission, String usage, BiPredicate<CommandSender, String[]> command) {
		super(label, description, permission, usage);
		setCommand(command);
	}
	
	public BasicCommand(String label, String description, String permission, BiPredicate<CommandSender, String[]> command) {
		super(label, description, permission);
		setCommand(command);
	}
	
	public BasicCommand(String label, String description, BiPredicate<CommandSender, String[]> command) {
		super(label, description);
		setCommand(command);
	}
	
	public BasicCommand(String label, BiPredicate<CommandSender, String[]> command) {
		super(label);
		setCommand(command);
	}
}