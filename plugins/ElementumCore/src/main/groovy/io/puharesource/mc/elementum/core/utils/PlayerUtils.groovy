package io.puharesource.mc.elementum.core.utils

import groovy.transform.CompileStatic
import org.bukkit.entity.Player

/**
 * Created by Tarkan on 02-04-2015.
 * This class is under the GPLv3 license.
 */
@CompileStatic
final class PlayerUtils {
    static void healPlayer(Player player) {
        player.setHealth(player.getMaxHealth())
    }

    static void feedPlayer(Player player) {
        player.setFoodLevel(20)
    }
}
