package io.puharesource.mc.elementum.core.api.credentials

/**
 * Created by Tarkan on 01-04-2015.
 * This class is under the GPLv3 license.
 */
class MySQLCredentials implements Credentials {
    String username = "minecraft"
    String password = "pass123"
    String host = "localhost"
    int port = 3306
    String database = "minecraft"
}
