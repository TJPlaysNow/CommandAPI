package com.tjplaysnow.commandapi.commands;

import com.tjplaysnow.commandapi.commandsbase.Command;
import org.bukkit.command.CommandSender;

import java.util.function.BiPredicate;

public class SubCommand extends Command {
	
	public SubCommand(String label, BiPredicate<CommandSender, String[]> command) {
		super(label);
		setCommand(command);
	}
}