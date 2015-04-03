package io.puharesource.mc.elementum.core.commands

import io.puharesource.mc.elementum.core.ElementumCorePlugin
import io.puharesource.mc.elementum.core.api.command.RootCommand
import io.puharesource.mc.elementum.core.api.command.SenderType
import io.puharesource.mc.elementum.core.utils.PlayerUtils
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * Created by Tarkan on 02-04-2015.
 * This class is under the GPLv3 license.
 */
final class CommandFeed extends RootCommand {
    CommandFeed() {
        super(ElementumCorePlugin.getInstance(), "feed", "elementum.command.feed", SenderType.BOTH, "[player]", "Feeds the given player", Type.NORMAL, false)
    }

    @Override
    void execute(String label, String[] args, CommandSender sender) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                PlayerUtils.feedPlayer(sender)
                sender.sendMessage(ChatColor.GREEN.toString() + "You have been fed!")
            } else sender.sendMessage(ChatColor.RED.toString() + "You need to be a player to feed yourself!")
        } else {
            Player player = Bukkit.getPlayer(args[0])

            if (player != null) {
                PlayerUtils.feedPlayer(player)
                sender.sendMessage(ChatColor.GREEN.toString() + "You fed " + player.getName())
                player.sendMessage(ChatColor.GREEN.toString() + "You have been fed!")
            } else sender.sendMessage(ChatColor.RED.toString() + args[0] + " is not currently online!")
        }
    }
}
