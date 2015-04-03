package io.puharesource.mc.elementum.core.api.command
import io.puharesource.mc.elementum.core.ElementumCorePlugin
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.*
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

import java.util.stream.Collectors
/**
 * Created by Tarkan on 01-04-2015.
 * This class is under the GPLv3 license.
 */
abstract class RootCommand implements CommandExecutor, TabCompleter {

    private JavaPlugin plugin
    private String name
    private String permission
    private SenderType allowedCommandSender
    private String usage
    private String description
    private Type type = Type.NORMAL
    private boolean errorOnWrongSub
    private List<String> aliases

    private Map<String, SubCommand> subCommands = new HashMap<>()

    enum Type {NORMAL, ASYNC, ACCEPT}

    RootCommand(final JavaPlugin plugin, final String name, final String permission, final SenderType allowedCommandSender, final String usage, final String description, final Type type, final boolean errorOnWrongSub) {
        this(plugin, name, permission, allowedCommandSender, usage, description, type, errorOnWrongSub, [] as String[])
    }

    RootCommand(final JavaPlugin plugin, final String name, final String permission, final SenderType allowedCommandSender, final String usage, final String description, final Type type, final boolean errorOnWrongSub, final String... aliases) {
        this.plugin = plugin
        this.name = name.toUpperCase().trim()
        this.permission = permission.toLowerCase().trim()
        this.allowedCommandSender = allowedCommandSender
        this.usage = usage
        this.description = description
        this.type = type
        this.errorOnWrongSub = errorOnWrongSub
        this.aliases = aliases
    }

    abstract void execute(final String label, final String[] args, final CommandSender sender)

    void addSubCommand(SubCommand subCommand) {
        subCommands.put(subCommand.getName(), subCommand)
        subCommand.aliases.each {subCommands.put(it.toUpperCase().trim(), subCommand)}
    }

    private void commandList(CommandSender sender, ChatColor primaryColor, ChatColor secondaryColor) {
        if (!(usage.isEmpty() && description.isEmpty()))
            sender.sendMessage("${primaryColor}    /${name.toLowerCase()} ${usage}${secondaryColor} - ${description}")
        List<String> alreadyShown = new ArrayList<>()

        for (SubCommand cmd : subCommands.values()) {
            if (alreadyShown.contains(cmd.getName())) continue
            alreadyShown.add(cmd.getName())
            sender.sendMessage("${primaryColor}    /${name.toLowerCase()} ${cmd.name.toLowerCase()} ${usage}${secondaryColor} - ${description}")
        }
    }

    void syntaxError(CommandSender sender) {
        sender.sendMessage("${ChatColor.RED}Wrong usage! Correct usage" + ((subCommands.size() > 1 || (subCommands.size() == 1 && !usage.isEmpty() && !description.isEmpty())) ? "s:" : ":"))
        commandList(sender, ChatColor.RED, ChatColor.GRAY)
    }

    void helpMessage(CommandSender sender) {
        sender.sendMessage("${ChatColor.GRAY}\"{$ChatColor.GREEN}${name}${ChatColor.GRAY}${ChatColor.GREEN}\" Help")
        commandList(sender, ChatColor.GREEN, ChatColor.GRAY)
    }

    JavaPlugin getPlugin() { plugin }
    String getName() { name }
    String getPermission() { permission }
    SenderType getAllowedCommandSender() { allowedCommandSender }
    String getUsage() { usage }
    String getDescription() { description }
    Type getType() { type }
    List<String> getAliases() { aliases }

    @Override
    boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase(name)) return false
        if (!permission.isEmpty() && !sender.hasPermission(permission)) {
            sender.sendMessage("${ChatColor.RED}You do not have permission to use that command!")
            return true
        }
        if (args.length > 0 && errorOnWrongSub) {
            final SubCommand subCommand = subCommands.get(args[0].toUpperCase().trim())
            if (subCommand != null) {
                if (subCommand.getAllowedCommandSender() == SenderType.CONSOLE && !(sender instanceof ConsoleCommandSender)) {
                    sender.sendMessage("${ChatColor.RED}This command can only be run from the console!")
                    return true
                } else if (subCommand.getAllowedCommandSender() == SenderType.PLAYER && !(sender instanceof Player)) {
                    sender.sendMessage("${ChatColor.RED}This command can only be run as a player!")
                    return true
                }

                if (!subCommand.getPermission().isEmpty() && !sender.hasPermission(subCommand.getPermission())) {
                    sender.sendMessage("${ChatColor.RED}You do not have permission to use that command!")
                    return true
                }

                if (subCommand.getType() == Type.ASYNC) {
                    Bukkit.getScheduler().runTaskAsynchronously(ElementumCorePlugin.getInstance(), {subCommand.execute(args[0].toLowerCase(), Arrays.copyOfRange(args, 1, args.length), sender)})
                } else subCommand.execute(args[0].toLowerCase(), Arrays.copyOfRange(args, 1, args.length), sender)
            } else syntaxError(sender)
        } else if (args.length > 0) {
            final SubCommand subCommand = subCommands.get(args[0].toUpperCase().trim())
            if (subCommand != null) {

                if (!subCommand.getPermission().isEmpty() && !sender.hasPermission(subCommand.getPermission())) {
                    sender.sendMessage("${ChatColor.RED}You do not have permission to use that command!")
                    return true
                }

                if (subCommand.getType() == Type.ASYNC) {
                    Bukkit.getScheduler().runTaskAsynchronously(ElementumCorePlugin.getInstance(), {subCommand.execute(args[0].toLowerCase(), Arrays.copyOfRange(args, 1, args.length), sender)})
                } else subCommand.execute(args[0].toLowerCase(), Arrays.copyOfRange(args, 1, args.length), sender)
                return true
            } else {
                if (type == Type.ASYNC) {
                    Bukkit.getScheduler().runTaskAsynchronously(ElementumCorePlugin.getInstance(), {execute(cmd.label.toLowerCase(), args, sender)})
                } else execute(cmd.label.toLowerCase(), args, sender)
            }
        } else {
            if (sender instanceof Player && allowedCommandSender == SenderType.CONSOLE) {
                sender.sendMessage("${ChatColor.RED}This command can only be run from the console!")
                return true
            }
            if (sender instanceof ConsoleCommandSender && allowedCommandSender == SenderType.PLAYER) {
                sender.sendMessage("${ChatColor.RED}This command can only be run as a player!")
                return true
            }
            if (type == Type.ASYNC) {
                Bukkit.getScheduler().runTaskAsynchronously(ElementumCorePlugin.getInstance(), {execute(cmd.label.toLowerCase(), args, sender)})
            } else execute(cmd.label.toLowerCase(), args, sender)
        }

        return true
    }

    @Override
    List<String> onTabComplete(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        List<String> possibilities = new ArrayList<>()

        if (args.length == 1)
            possibilities.addAll(subCommands.keySet().stream().filter({sub -> sub.toLowerCase().startsWith(args[0].toLowerCase())}).map({str -> str.toLowerCase()}).collect(Collectors.toList()))

        return possibilities.size() <= 0 ? null : possibilities
    }
}
