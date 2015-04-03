package io.puharesource.mc.elementum.core.listeners

import io.puharesource.mc.elementum.core.ElementumCorePlugin
import io.puharesource.mc.elementum.core.api.user.OnlineUser
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent

/**
 * Created by Tarkan on 02-04-2015.
 * This class is under the GPLv3 license.
 */
final class ListenerMisc implements Listener {
    @EventHandler
    void onFoodChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            OnlineUser user = ElementumCorePlugin.instance.storageManager.getOnlineUser(event.getEntity().getUniqueId())
            if (user.isGod()) event.setCancelled(true)
        }
    }
}
