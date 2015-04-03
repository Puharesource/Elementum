package io.puharesource.mc.elementum.core.listeners

import io.puharesource.mc.elementum.core.ElementumCorePlugin
import io.puharesource.mc.elementum.core.api.user.OnlineUser
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

/**
 * Created by Tarkan on 02-04-2015.
 * This class is under the GPLv3 license.
 */
final class ListenerDamage implements Listener {
    @EventHandler(ignoreCancelled = true)
    void onDamage(EntityDamageEvent event) {
        def entity = event.getEntity()

        if (entity instanceof Player) {
            OnlineUser user = ElementumCorePlugin.instance.storageManager.getOnlineUser(entity.getUniqueId())
            if (user.isGod()) event.setCancelled(true)
        }
    }
}
