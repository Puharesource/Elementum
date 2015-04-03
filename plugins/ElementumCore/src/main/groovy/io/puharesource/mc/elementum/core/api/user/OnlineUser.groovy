package io.puharesource.mc.elementum.core.api.user

import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * Created by Tarkan on 01-04-2015.
 * This class is under the GPLv3 license.
 */
final class OnlineUser extends User {
    private transient Player player

    Player getPlayer() { player ?: Bukkit.getPlayer(uuid)}

    void update() {
        Player player = getPlayer()
        username = player.name
        if (!nameHistory.contains(player.name))
            nameHistory.add(player.name)
    }
}
