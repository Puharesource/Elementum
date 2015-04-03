package io.puharesource.mc.elementum.core.listeners

import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

/**
 * Created by Tarkan on 02-04-2015.
 * This class is under the GPLv3 license.
 */
final class ListenerChat implements Listener {
    @EventHandler
    void onChat(AsyncPlayerChatEvent event) {
        event.setFormat(event.getPlayer().getDisplayName() + ChatColor.WHITE +  ": " + event.getMessage())
    }
}
