package io.puharesource.mc.elementum.core.listeners

import io.puharesource.mc.elementum.core.ElementumCorePlugin
import io.puharesource.mc.elementum.core.api.storage.StorageManager
import io.puharesource.mc.elementum.core.api.user.OnlineUser
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
/**
 * Created by Tarkan on 30-03-2015.
 * This class is under the GPLv3 license.
 */
final class ListenerConnection implements Listener {

    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer()

        StorageManager manager = ElementumCorePlugin.getInstance().getStorageManager()

        if (!manager.containsUser(player.getUniqueId())) {
            manager.createUser(player.getUniqueId())
        } else {
            manager.makeUserOnline(player.getUniqueId())
            OnlineUser user = manager.getOnlineUser(player.getUniqueId())
            user
            user.getPlayer().setDisplayName(user.getNickname())

            if (!user.getNameHistory().contains(player.getName()))
                user.getNameHistory().add(player.getName())
        }
    }

    @EventHandler
    void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer()
        StorageManager manager = ElementumCorePlugin.getInstance().getStorageManager()

        manager.saveUser(player.getUniqueId())
        manager.makeUserOffline(player.getUniqueId())
    }
}
