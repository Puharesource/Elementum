package io.puharesource.mc.elementum.core.api.user

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer

/**
 * Created by Tarkan on 30-03-2015.
 * This class is under the GPLv3 license.
 */
final class OfflineUser extends User {
    private transient OfflinePlayer offlinePlayer

    OfflineUser(UUID uuid) {
        this.uuid = uuid
    }

    OfflinePlayer getOfflinePlayer() { offlinePlayer ?: Bukkit.getOfflinePlayer(uuid) }
}
