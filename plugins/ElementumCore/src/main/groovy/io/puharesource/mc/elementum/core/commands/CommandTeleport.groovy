package io.puharesource.mc.elementum.core.commands
import io.puharesource.mc.elementum.core.ElementumCorePlugin
import io.puharesource.mc.elementum.core.api.command.RootCommand
import io.puharesource.mc.elementum.core.api.command.SenderType
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * Created by Tarkan on 03-04-2015.
 * This class is under the GPLv3 license.
 */
final class CommandTeleport extends RootCommand {
    CommandTeleport() {
        super(ElementumCorePlugin.getInstance(), "tp", "elementum.command.teleport", SenderType.BOTH, "<player>|<player-from> <player-to>", "Teleports you or another player to the specified player.", type, errorOnWrongSub, "teleport", "tele")
    }

    @Override
    void execute(String label, String[] args, CommandSender sender) {
        if (args.length == 1) {

        } else if (args.length == 2) {
            if (!sender.hasPermission("elementum.command.teleport.other")) {
                sender.sendMessage("${ChatColor.RED} You do not have permission to teleport other players!")
                return
            }

            Player playerFrom = Bukkit.getPlayer args[0]
            Player playerTo = Bukkit.getPlayer args[1]

            if (playerFrom == null) {
                sender.sendMessage("${ChatColor.RED} Couldn't find player ${args[0]}")
                return
            } else if (playerTo == null) {
                sender.sendMessage("${ChatColor.RED} Couldn't find player ${args[1]}")
                return
            }
        } else {
            syntaxError(sender)
        }
    }
}
