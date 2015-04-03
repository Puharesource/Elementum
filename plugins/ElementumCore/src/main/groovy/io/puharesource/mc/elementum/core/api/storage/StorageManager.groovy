package io.puharesource.mc.elementum.core.api.storage
import io.puharesource.mc.elementum.core.ElementumCorePlugin
import io.puharesource.mc.elementum.core.api.user.OnlineUser
import io.puharesource.mc.elementum.core.api.user.User
import io.puharesource.mc.elementum.core.api.user.OfflineUser

import java.util.concurrent.ConcurrentSkipListMap
/**
 * Created by Tarkan on 30-03-2015.
 * This class is under the GPLv3 license.
 */
abstract class StorageManager {

    protected final transient Map<UUID, OnlineUser> onlineUsers = new ConcurrentSkipListMap<>()
    protected final transient Map<String, UUID> names = new ConcurrentSkipListMap<>()

    public StorageManager(ElementumCorePlugin plugin) {

    }

    abstract void saveUser(UUID uuid)
    abstract User createUser(UUID uuid)
    abstract boolean containsUser(UUID uuid)
    abstract void makeUserOnline(UUID uuid)
    abstract void makeUserOffline(UUID uuid)
    abstract OnlineUser getOnlineUser(UUID uuid)
    abstract OfflineUser getOfflineUser(UUID uuid)
    boolean hasUsernamePlayed(String name) { names.containsKey(name.toLowerCase().trim()) }
    UUID getUuidFromUsername(String name) { names.get(name.toLowerCase().trim()) }
    abstract User getUserFromUsername(String name)
}