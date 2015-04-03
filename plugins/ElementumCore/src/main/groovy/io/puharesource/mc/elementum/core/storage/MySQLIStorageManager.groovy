package io.puharesource.mc.elementum.core.storage

import io.puharesource.mc.elementum.core.ElementumCorePlugin
import io.puharesource.mc.elementum.core.api.storage.StorageManager
import io.puharesource.mc.elementum.core.api.user.OfflineUser
import io.puharesource.mc.elementum.core.api.user.OnlineUser
import io.puharesource.mc.elementum.core.api.user.User

/**
 * Created by Tarkan on 30-03-2015.
 * This class is under the GPLv3 license.
 */
final class MySQLIStorageManager extends StorageManager {
    MySQLIStorageManager(ElementumCorePlugin plugin) {
        super(plugin)
    }

    @Override
    void saveUser(UUID uuid) {

    }

    @Override
    User createUser(UUID uuid) {
        return null
    }

    @Override
    boolean containsUser(UUID uuid) {
        return false
    }

    @Override
    void makeUserOnline(UUID uuid) {

    }

    @Override
    void makeUserOffline(UUID uuid) {

    }

    @Override
    OnlineUser getOnlineUser(UUID uuid) {
        return null
    }

    @Override
    OfflineUser getOfflineUser(UUID uuid) {
        return null
    }

    @Override
    User getUserFromUsername(String name) {
        return null
    }
}
