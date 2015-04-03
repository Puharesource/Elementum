package io.puharesource.mc.elementum.core.api.command

import org.bukkit.command.CommandSender

/**
 * Created by Tarkan on 01-04-2015.
 * This class is under the GPLv3 license.
 */
abstract class SubCommand {
    private String name
    private String permission
    private SenderType allowedCommandSender
    private String usage
    private String description
    private RootCommand.Type type = RootCommand.Type.NORMAL
    private String[] aliases

    public SubCommand(String name, String permission, SenderType allowedCommandSender, String usage, String description, RootCommand.Type type, String... aliases) {
        this.name = name.toUpperCase().trim()
        this.permission = permission.toLowerCase().trim()
        this.allowedCommandSender = allowedCommandSender
        this.usage = usage
        this.description = description
        this.type = type
        this.aliases = aliases
    }

    String getName() { name }
    String getPermission() { permission }
    SenderType getAllowedCommandSender() { allowedCommandSender }
    String getUsage() { usage }
    String getDescription() { description }
    RootCommand.Type getType() { type }
    String[] getAliases() { aliases }

    abstract void execute(String cmdName, String[] args, CommandSender sender)
}
