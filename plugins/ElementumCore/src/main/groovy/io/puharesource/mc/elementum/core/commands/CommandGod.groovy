package io.puharesource.mc.elementum.core.commands
import io.puharesource.mc.elementum.core.ElementumCorePlugin
import io.puharesource.mc.elementum.core.api.command.RootCommand
import io.puharesource.mc.elementum.core.api.command.SenderType
import io.puharesource.mc.elementum.core.api.storage.StorageManager
import io.puharesource.mc.elementum.core.api.user.OnlineUser
import io.puharesource.mc.elementum.core.api.user.User
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
/**
 * Created by Tarkan on 02-04-2015.
 * This class is under the GPLv3 license.
 */
final class CommandGod extends RootCommand {
    CommandGod() {
        super(ElementumCorePlugin.getInstance(), "god", "elementum.command.god", SenderType.BOTH, "[player]", "Makes a player invulnerable", Type.NORMAL, false)
    }

    @Override
    void execute(String label, String[] args, CommandSender sender) {
        StorageManager manager = ElementumCorePlugin.getInstance().storageManager

        if (args.length == 0) {
            if (sender instanceof Player) {
                OnlineUser user = manager.getOnlineUser(sender.getUniqueId())
                user.setGod(!user.isGod())
                if (user.isGod())
                    sender.sendMessage(ChatColor.GREEN.toString() + "You have turned god on.")
                else sender.sendMessage(ChatColor.GREEN.toString() + "You have turned god off.")
            } else sender.sendMessage(ChatColor.RED.toString() + "You need to be a player to feed yourself!")
        } else {
            User user = manager.getUserFromUsername(args[0])
            if (user != null) {
                user.setGod(!user.isGod())
                if (user instanceof OnlineUser) {
                    if (user.isGod()) {
                        user.getPlayer().sendMessage(ChatColor.GREEN.toString() + "You are now invulnerable.")
                        sender.sendMessage(ChatColor.GREEN.toString() + user.username + " is now invulnerable.")
                    } else {
                        user.getPlayer().sendMessage(ChatColor.GREEN.toString() + "You are no longer invulnerable.")
                        sender.sendMessage(ChatColor.GREEN.toString() + user.username + " is no longer invulnerable.")
                    }
                }
            } else sender.sendMessage(ChatColor.RED.toString() + args[0] + " has never played on the server before!")
        }
    }
}
