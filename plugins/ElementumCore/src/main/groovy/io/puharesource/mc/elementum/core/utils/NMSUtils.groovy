package io.puharesource.mc.elementum.core.utils

import groovy.transform.CompileStatic
import org.bukkit.Bukkit

import java.lang.reflect.Field
import java.util.regex.Pattern

/**
 * Created by Tarkan on 01-04-2015.
 * This class is under the GPLv3 license.
 */
@CompileStatic
final class NMSUtils {
    private final static Pattern BRAND = Pattern.compile("(v|)[0-9][_.][0-9][_.][R0-9]*");
    final static String CRAFTBUKKIT_PATH = "org.bukkit.craftbukkit.";
    final static String NMS_PATH = "net.minecraft.server.";

    static Class<?> getCraftbukkitClass(String path) throws ClassNotFoundException { Class.forName(CRAFTBUKKIT_PATH + getServerVersion() + path) }

    static Class<?> getCraftbukkitClassArray(String path) throws ClassNotFoundException { Class.forName("[L" + CRAFTBUKKIT_PATH + getServerVersion() + path + ";") }

    static Class<?> getNMSClass(String path) throws ClassNotFoundException { Class.forName(NMS_PATH + getServerVersion() + path) }

    static Class<?> getNMSClassArray(String path) throws ClassNotFoundException { Class.forName("[L" + NMS_PATH + getServerVersion() + path + ";") }

    static Object getPrivateField(Object object, String field) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = object.getClass()
        Field objectField = clazz.getDeclaredField(field)
        objectField.setAccessible(true)
        Object result = objectField.get(object)
        objectField.setAccessible(false)
        return result
    }

    static String getServerVersion() {
        String version;
        String pkg = Bukkit.getServer().getClass().getPackage().getName()
        String version0 = pkg.substring(pkg.lastIndexOf('.') + 1)
        if (!BRAND.matcher(version0).matches()) {
            version0 = ""
        }
        version = version0
        return !"".equals(version) ? version + "." : ""
    }
}
