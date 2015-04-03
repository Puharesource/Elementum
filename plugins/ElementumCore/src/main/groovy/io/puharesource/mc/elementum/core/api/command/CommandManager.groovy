package io.puharesource.mc.elementum.core.api.command

import groovy.transform.CompileStatic
import io.puharesource.mc.elementum.core.LogManager
import io.puharesource.mc.elementum.core.utils.NMSUtils
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.command.PluginCommand
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.SimplePluginManager

import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException

/**
 * Created by Tarkan on 01-04-2015.
 * This class is under the GPLv3 license.
 */
@CompileStatic
final class CommandManager {
    static void register(final RootCommand command) {
        if (command == null) throw new IllegalArgumentException("Command cannot be null!")

        unregisterSilently(command.name.toLowerCase())
        command.aliases.each {unregisterSilently it.toLowerCase().trim()}
        if (registerAttempt(command))
            command.plugin.getLogger().info("Registered command \"" + command.name.toLowerCase() + "\".")
        else command.plugin.getLogger().warning("Failed to register command \"" + command.name.toLowerCase() + "\".")
    }

    static void unregister(final String name) {
        if (name == null) throw new IllegalArgumentException("Command name cannot be null!")

        unregister(getCommand(name, null))
    }

    static void unregisterSilently(final String name) {
        if (name == null) throw new IllegalArgumentException("Command name cannot be null!")

        unregisterSilently(getCommand(name, null))
    }

    static void unregister(final Command command) {
        if (command == null) throw new IllegalArgumentException("Command cannot be null!")

        try {
            Map<String, Command> knownCommands = getKnownCommandMap()
            knownCommands.remove(command.getName())
            command.aliases.each {knownCommands.remove it}
            LogManager.getLogger().info("Unregistered command \"" + command.getName() + "\".")
        } catch (SecurityException | IllegalArgumentException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace()
            LogManager.getLogger().warning("Failed to unregister command!")
        }
    }

    static void unregisterSilently(final Command command) {
        if (command == null) throw new IllegalArgumentException("Command cannot be null!")

        try {
            def knownCommands = getKnownCommandMap()
            knownCommands.remove(command.name)
            command.aliases.each {knownCommands.remove it}
        } catch (SecurityException | IllegalArgumentException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace()
        }
    }

    private static boolean registerAttempt(final RootCommand command) {
        if (command == null) throw new IllegalArgumentException("Command cannot be null!")

        PluginCommand pluginCmd = command.plugin.getCommand(command.name.toLowerCase())
        if (pluginCmd != null) return false

        pluginCmd = getCommand(command.name, command.plugin)
        pluginCmd.setPermission(command.permission)
        pluginCmd.setAliases(command.aliases)
        pluginCmd.setDescription(command.description)
        pluginCmd.setPermissionMessage(ChatColor.RED.toString() + "You do not have permission to use that command!")
        try {
            getCommandMap().register(command.plugin.description.name, pluginCmd)
            pluginCmd.setExecutor(command)
            pluginCmd.setTabCompleter(command)
        } catch (Exception ignored) { return false }
        return true
    }

    private static PluginCommand getCommand(final String name, final Plugin plugin) {
        if (name == null) throw new IllegalArgumentException("Command name cannot be null!")

        PluginCommand command = null
        try {
            Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class)
            constructor.setAccessible(true)

            command = constructor.newInstance(name, plugin)
        } catch (SecurityException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace()
        }

        return command
    }

    private static CommandMap getCommandMap() {
        CommandMap commandMap = null
        try {
            if ((Bukkit.getPluginManager() instanceof SimplePluginManager)) {
                Field field = SimplePluginManager.class.getDeclaredField("commandMap")
                field.setAccessible(true)

                commandMap = field.get(Bukkit.getPluginManager()) as CommandMap
            }
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace()
        }

        return commandMap
    }

    private static Map<String, Command> getKnownCommandMap() throws NoSuchFieldException, IllegalAccessException {
        NMSUtils.getPrivateField(NMSUtils.getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap"), "knownCommands") as Map<String, Command>
    }
}
