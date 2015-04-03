package io.puharesource.mc.elementum.core.api.user
import com.google.gson.Gson
import org.bukkit.Location
/**
 * Created by Tarkan on 30-03-2015.
 * This class is under the GPLv3 license.
 */
abstract class User implements Comparable<User> {

    protected UUID uuid
    protected String username
    protected String nickname
    protected List<String> nameHistory = new LinkedList<>()

    protected BigDecimal money

    protected boolean god
    protected String jail
    protected List<Mail> mails = new LinkedList<>()
    protected Map<String, Location> homes = new LinkedHashMap<>()

    protected transient UUID reply
    protected transient Location lastLocation
    protected transient boolean vanished
    protected transient boolean afk

    UUID getUuid() { uuid }
    String getUsername() { username }

    String setNickname(String nickname) { this.nickname = nickname }
    String getNickname() { nickname }

    List<String> getNameHistory() { nameHistory }

    BigDecimal getMoney() { money }
    BigDecimal setMoney(BigDecimal money) { this.money = money }
    BigDecimal addMoney(BigDecimal money) { this.money = this.money.add(money) }
    BigDecimal takeMoney(BigDecimal money) { this.money = this.money.subtract(money) }

    boolean isGod() { god }
    void setGod(boolean god) { this.god = god }

    String getJail() { jail }
    void setJail() { this.jail = jail }

    Collection<UUID> getMails() { mails as Collection<UUID> }
    void addMail(String text, UUID sender) { mails.add(new Mail(text, sender)) }
    void clearMails() { mails.clear() }

    boolean hasHome() { !homes.isEmpty() }
    Location getHome(String home) { homes.get(home.toLowerCase().trim()) }
    void addHome(String home, Location location) { homes.put(home.toLowerCase().trim(), location) }
    void delHome(String home) { homes.remove(home) }

    UUID getReply() { reply }
    void setReply(UUID reply) { this.reply = reply }

    Location getLastLocation() { lastLocation }
    void setLastLocation(Location lastLocation) { this.lastLocation = lastLocation }

    boolean isVanished() { vanished }
    boolean setVanished(boolean vanished) { this.vanished = vanished }

    boolean isAfk() { afk }
    void setAfk(boolean afk) { this.afk = afk }

    String getJson() { new Gson().toJson(this) }

    @Override
    int compareTo(User o) { username.compareToIgnoreCase(o.getUsername()) }
}
