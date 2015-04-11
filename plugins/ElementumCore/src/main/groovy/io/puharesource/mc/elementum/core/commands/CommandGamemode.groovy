package io.puharesource.mc.elementum.core.commands
import io.puharesource.mc.elementum.core.ElementumCorePlugin
import io.puharesource.mc.elementum.core.api.command.RootCommand
import io.puharesource.mc.elementum.core.api.command.SenderType
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode as GM
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
/**
 * Created by Tarkan on 02-04-2015.
 * This class is under the GPLv3 license.
 */
final class CommandGamemode extends RootCommand {
    CommandGamemode() {
        super(ElementumCorePlugin.getInstance(), "gamemode", "elementum.command.gamemode", SenderType.BOTH, "<gamemode> [player]|[player]", "Sets the gamemode of the given player", Type.NORMAL, false, ['gm', 'gms', 'gm0', 'gmc', 'gm1', 'gma', 'gm2', 'gmsp', 'gm3'] as String[])
    }

    @Override
    void execute(String label, String[] args, CommandSender sender) {
        if (args.length <= 0 && sender instanceof ConsoleCommandSender) {
            sender.sendMessage("${ChatColor.RED} You need to specify a player!")
            return
        }

        if (label.length() > 2 && label.toLowerCase().startsWith("gm")) {
            GM gameMode = getGamemode(label.substring(2))
            if (args.length <= 0) {
                (sender as Player).setGameMode(gameMode)
                sender.sendMessage(ChatColor.GREEN.toString() + "You have set your gamemode to ${gameMode.toString().toLowerCase()}")
            } else {
                Player player = Bukkit.getPlayer(args[0])

                if (player != null) {
                    sender.sendMessage("${ChatColor.GREEN} You have set ${player.name}'s gamemode to ${gameMode.toString().toLowerCase()}")
                    player.sendMessage("${ChatColor.GREEN} Your gamemode has been set to ${gameMode.toString().toLowerCase()}")
                } else sender.sendMessage("${ChatColor.RED} ${args[0]} isn't currently online!")
            }
        } else {
            if (sender instanceof ConsoleCommandSender) {
                if (args.length >= 2) {
                    GM gameMode = getGamemode(args[0])
                    if (gameMode != null) {
                        Player player = Bukkit.getPlayer(args[1])

                        if (player != null) {
                            player.setGameMode(gameMode)
                            sender.sendMessage(ChatColor.GREEN.toString() + "You set " + player.getName() + "'s gamemode to " + gameMode.toString().toLowerCase())
                        } else sender.sendMessage(ChatColor.RED.toString() + args[1] + " is not currently online!")
                    } else sender.sendMessage(ChatColor.RED.toString() + args[0] + " is not a correct gamemode!")
                } else {
                    sender.sendMessage(ChatColor.RED.toString() + "Wrong usage! Correct usage:")
                    sender.sendMessage(ChatColor.RED.toString() + "    /" + label + " <gamemode> <player>")
                }
            } else if (sender instanceof Player) {
                if (args.length >= 2) {
                    GM gameMode = getGamemode(args[0])
                    if (gameMode != null) {
                        Player player = Bukkit.getPlayer(args[1])

                        if (player != null) {
                            player.setGameMode(gameMode)
                            sender.sendMessage(ChatColor.GREEN.toString() + "You set " + player.getName() + "'s gamemode to " + gameMode.toString().toLowerCase())
                        } else sender.sendMessage(ChatColor.RED.toString() + args[1] + " is not currently online!")
                    } else sender.sendMessage(ChatColor.RED.toString() + args[0] + " is not a correct gamemode!")
                } else if (args.length == 1) {
                    GM gameMode = getGamemode(args[0])
                    if (gameMode != null) {
                        sender.setGameMode(gameMode)
                        sender.sendMessage(ChatColor.GREEN.toString() + "You have set your gamemode to " + gameMode.toString().toLowerCase())
                    } else sender.sendMessage(ChatColor.RED.toString() + args[0] + " is not a correct gamemode!")
                } else {
                    sender.sendMessage(ChatColor.RED.toString() + "Wrong usage! Correct usage:")
                    sender.sendMessage(ChatColor.RED.toString() + "    /" + label + " <gamemode> [player]")
                }
            }
        }
    }

    private static GM getGamemode(String str) {
        switch (str.toLowerCase().trim()) {
            case "0": return GM.SURVIVAL
            case "s": return GM.SURVIVAL
            case "survival": return GM.SURVIVAL

            case "1": return GM.CREATIVE
            case "c": return GM.CREATIVE
            case "creative": return GM.CREATIVE

            case "2": return GM.ADVENTURE
            case "a": return GM.ADVENTURE
            case "adventure": return GM.ADVENTURE

            case "3": return GM.SPECTATOR
            case "sp": return GM.SPECTATOR
            case "spectator": return GM.SPECTATOR

            default: return null
        }
    }
}
