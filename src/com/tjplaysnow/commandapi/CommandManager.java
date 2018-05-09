package com.tjplaysnow.commandapi;

import com.tjplaysnow.commandapi.commandsbase.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor {
	
	private final Plugin plugin;
	private final List<Command> commands;
	
	public CommandManager(Plugin plugin) {
		this.plugin = plugin;
		commands = new ArrayList<>();
	}
	
	public void addCommand(Command command) {
		commands.add(command);
		try {
			registerCommand(command.getLabel(), command.getDescription(), command.getPermission(), command.getUsage(), plugin, this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	public void addCommands(List<Command> commands) {
		for (Command command : commands) {
			addCommand(command);
		}
	}
	
	private void registerCommand(String name, String description, String permission, String usage, Plugin plugin, CommandExecutor executor) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
		Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
		constructor.setAccessible(true);
		PluginCommand command = constructor.newInstance(name, plugin);
		command.setExecutor(executor);
		command.setDescription(description);
		command.setUsage(usage);
		command.setLabel(name);
		command.setPermission(permission);
		Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
		bukkitCommandMap.setAccessible(true);
		CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
		commandMap.register(plugin.getDescription().getName().toLowerCase(), command);
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command paramCommand, String label, String[] args) {
		for (Command command : commands) {
			if (command.getLabel().equals(label)) {
				if (command.getPermission().equals("") || sender.hasPermission(command.getPermission())) {
					command.onCommand(sender, args);
					return true;
				} else {
					sender.sendMessage(command.getPermissionError());
				}
			}
		}
		return false;
	}
}