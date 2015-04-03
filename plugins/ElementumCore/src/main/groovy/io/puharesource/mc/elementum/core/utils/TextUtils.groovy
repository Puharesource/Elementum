package io.puharesource.mc.elementum.core.utils

import groovy.transform.CompileStatic
import org.bukkit.ChatColor

/**
 * Created by Tarkan on 02-04-2015.
 * This class is under the GPLv3 license.
 */
@CompileStatic
final class TextUtils {
    static String stripColors(String text) { ChatColor.stripColor(format(text)) }
    static String format(String text) { ChatColor.translateAlternateColorCodes('&'.toCharacter(), text) }
}
