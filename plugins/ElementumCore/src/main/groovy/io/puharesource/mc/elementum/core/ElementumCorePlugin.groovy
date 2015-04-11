package io.puharesource.mc.elementum.core
import groovy.transform.CompileStatic
import io.puharesource.mc.elementum.core.api.command.CommandManager
import io.puharesource.mc.elementum.core.api.config.CredentialsConfig
import io.puharesource.mc.elementum.core.api.config.MainConfig
import io.puharesource.mc.elementum.core.api.config.YamlConfigTest
import io.puharesource.mc.elementum.core.api.plugin.ElementumPlugin
import io.puharesource.mc.elementum.core.api.storage.StorageManager
import io.puharesource.mc.elementum.core.api.storage.StorageType
import io.puharesource.mc.elementum.core.commands.*
import io.puharesource.mc.elementum.core.listeners.ListenerChat
import io.puharesource.mc.elementum.core.listeners.ListenerConnection
import io.puharesource.mc.elementum.core.listeners.ListenerDamage
import io.puharesource.mc.elementum.core.listeners.ListenerMisc
import io.puharesource.mc.elementum.core.storage.MySQLIStorageManager
import io.puharesource.mc.elementum.core.storage.MySQLStorageManager
import io.puharesource.mc.elementum.core.storage.RedisStorageManager
import org.bukkit.configuration.serialization.ConfigurationSerialization
/**
*  Created by Tarkan on 30-03-2015.
*  This class is under the GPLv3 license.
*/
@CompileStatic
final class ElementumCorePlugin extends ElementumPlugin {

    private static ElementumCorePlugin instance
    private StorageManager storageManager

    @Override
    void onEnable() {
        instance = this

        MainConfig.getConfig()
        CredentialsConfig credentials = CredentialsConfig.getConfig()

        StorageType storageType = StorageType.valueOf(credentials.databaseType.toUpperCase().trim())

        if (storageType == StorageType.MYSQL) storageManager = new MySQLStorageManager(this);
        else if (storageType == StorageType.REDIS) storageManager = new RedisStorageManager(this)
        else if (storageType == StorageType.MYSQLI) storageManager = new MySQLIStorageManager(this);

        getServer().getPluginManager().registerEvents(new ListenerConnection(), this)
        getServer().getPluginManager().registerEvents(new ListenerChat(), this)
        getServer().getPluginManager().registerEvents(new ListenerDamage(), this)
        getServer().getPluginManager().registerEvents(new ListenerMisc(), this)

        CommandManager.register(new CommandNickname())
        CommandManager.register(new CommandHeal())
        CommandManager.register(new CommandFeed())
        CommandManager.register(new CommandGod())
        CommandManager.register(new CommandGamemode())

        ConfigurationSerialization.registerClass(YamlConfigTest.class)
    }

    static ElementumCorePlugin getInstance() { instance }
    StorageManager getStorageManager() { storageManager }

    StorageType getStorageType() {
        if (storageManager instanceof RedisStorageManager) return StorageType.REDIS
        if (storageManager instanceof MySQLStorageManager) return StorageType.MYSQL
        if (storageManager instanceof MySQLIStorageManager) return StorageType.MYSQLI
        return null
    }
}
