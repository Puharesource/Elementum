package io.puharesource.mc.elementum.core.commands

import io.puharesource.mc.elementum.core.ElementumCorePlugin as Core
import io.puharesource.mc.elementum.core.api.command.RootCommand
import io.puharesource.mc.elementum.core.api.command.SenderType
import io.puharesource.mc.elementum.core.api.storage.StorageManager
import io.puharesource.mc.elementum.core.utils.TextUtils
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * Created by Tarkan on 02-04-2015.
 * This class is under the GPLv3 license.
 */
final class CommandNickname extends RootCommand {
    CommandNickname() {
        super(Core.getInstance(), "nick", "elementum.command.nickname", SenderType.BOTH, "<nick>|<player> <nick>", "Sets the displayname of a given player.", Type.NORMAL, false, ['nickname', 'displayname'] as String[])
    }


    @Override
    void execute(String label, String[] args, CommandSender sender) {
        if (args.length <= 0) {
            syntaxError(sender)
            return
        }

        if (args.length > 1) {
            StorageManager manager = Core.getInstance().getStorageManager()

            if (!manager.hasUsernamePlayed(args[0])) {
                sender.sendMessage(ChatColor.RED.toString() + args[0] + " has never played on the server before!")
                return
            }

            Player player = Bukkit.getPlayer(args[0])
            String nick = TextUtils.format(args[1])

            if (player != null) {
                manager.getOnlineUser(player.getUniqueId()).setNickname(nick)
                player.setDisplayName(nick)
            } else {
                manager.getOfflineUser(manager.getUuidFromUsername(args[0])).setNickname(nick)
            }

            sender.sendMessage(ChatColor.GREEN.toString() + "You set " + args[0] + "'s nickname to \"" + ChatColor.RESET + nick + ChatColor.GREEN + "\"")
        } else {
            if (sender instanceof Player) {
                String nick = ChatColor.translateAlternateColorCodes('&'.toCharacter(), args[0])

                Core.getInstance().getStorageManager().getOnlineUser(sender.getUniqueId()).setNickname(nick)
                sender.setDisplayName(nick)
                sender.sendMessage(ChatColor.GREEN.toString() + "Your nickname has been set to " + nick)
            } else sender.sendMessage(ChatColor.RED.toString() + "You need to be a player to set your own nickname!")
        }
    }
}
