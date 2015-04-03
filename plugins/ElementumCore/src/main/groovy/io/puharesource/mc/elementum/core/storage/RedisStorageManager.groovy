package io.puharesource.mc.elementum.core.storage

import com.google.gson.Gson
import io.puharesource.mc.elementum.core.ElementumCorePlugin
import io.puharesource.mc.elementum.core.api.config.CredentialsConfig
import io.puharesource.mc.elementum.core.api.storage.StorageManager
import io.puharesource.mc.elementum.core.api.user.OfflineUser
import io.puharesource.mc.elementum.core.api.user.OnlineUser
import io.puharesource.mc.elementum.core.api.user.User
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import redis.clients.jedis.Jedis
/**
 * Created by Tarkan on 30-03-2015.
 * This class is under the GPLv3 license.
 */
final class RedisStorageManager extends StorageManager {

    private final String PREFIX = "ELEMENTUM:"
    private final String USER_PREFIX = PREFIX + "USER:"

    private Jedis jedis

    RedisStorageManager(ElementumCorePlugin plugin) {
        super(plugin)

        jedis = new Jedis(CredentialsConfig.getConfig().redis.host)

        Gson gson = new Gson()

        for (String userKey : jedis.keys(USER_PREFIX + "*")) {
            try {
                OfflineUser user = gson.fromJson(jedis.get(userKey), OfflineUser.class)
                names.put(user.username.toLowerCase(), user.uuid)
            } catch (Exception e) {
                e.printStackTrace()
                plugin.getLogger().warning("Failed to load user " + userKey + " from the redis database!")
            }
        }
    }

    @Override
    void saveUser(UUID uuid) {
        if (!containsUser(uuid)) throw new IllegalArgumentException(uuid.toString() + " doesn't exist!")
        User user = getOnlineUser(uuid)
        jedis.set(USER_PREFIX + uuid.toString(), user.getJson())
    }

    @Override
    User createUser(UUID uuid) {
        if (containsUser(uuid)) throw new IllegalArgumentException(uuid.toString() + " is already a registered user!")
        User user = new OfflineUser(uuid)
        jedis.set(USER_PREFIX + uuid.toString(), user.getJson())
        return user
    }

    @Override
    boolean containsUser(UUID uuid) {
        return jedis.get(USER_PREFIX + uuid.toString()) != null
    }

    @Override
    void makeUserOnline(UUID uuid) {
        if (!containsUser(uuid)) throw new IllegalArgumentException(uuid.toString() + " doesn't exist!")
        OnlineUser user = new Gson().fromJson(getOfflineUser(uuid).getJson(), OnlineUser.class)
        user.update()
        onlineUsers.put(uuid, user)
    }

    @Override
    void makeUserOffline(UUID uuid) {
        if (!containsUser(uuid)) throw new IllegalArgumentException(uuid.toString() + " isn't online!")
        onlineUsers.remove(uuid)
    }

    @Override
    OnlineUser getOnlineUser(UUID uuid) {
        if (!containsUser(uuid)) throw new IllegalArgumentException(uuid.toString() + " doesn't exist!")
        if (!onlineUsers.containsKey(uuid)) throw new IllegalArgumentException(uuid.toString() + " isn't online!")

        return onlineUsers.get(uuid)
    }

    @Override
    OfflineUser getOfflineUser(UUID uuid) {
        if (!containsUser(uuid)) throw new IllegalArgumentException(uuid.toString() + " doesn't exist!")

        return new Gson().fromJson(jedis.get(USER_PREFIX + uuid.toString()), OfflineUser.class)
    }

    @Override
    User getUserFromUsername(String name) {
        Player player = Bukkit.getPlayer(name)

        if (player != null)
            return onlineUsers.get(player.getUniqueId())
        else hasUsernamePlayed(name) ? getOfflineUser(getUuidFromUsername(name)) : null
    }
}
