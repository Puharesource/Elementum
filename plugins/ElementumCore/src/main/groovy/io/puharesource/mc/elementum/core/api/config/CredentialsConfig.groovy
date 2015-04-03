package io.puharesource.mc.elementum.core.api.config

import groovy.transform.CompileStatic
import io.puharesource.mc.elementum.core.ElementumCorePlugin as Core
import io.puharesource.mc.elementum.core.api.credentials.MySQLCredentials
import io.puharesource.mc.elementum.core.api.credentials.RedisCredentials

/**
 * Created by Tarkan on 01-04-2015.
 * This class is under the GPLv3 license.
 */
@CompileStatic
final class CredentialsConfig {

    private transient static CredentialsConfig instance
    private transient static Config config = new Config(Core.getInstance().getDataFolder(), "credentials")

    String databaseType = "MYSQLI"

    MySQLCredentials mysql = new MySQLCredentials()
    RedisCredentials redis = new RedisCredentials()

    protected CredentialsConfig() {  }

    static CredentialsConfig getConfig() { (instance ?: config.load(this.class, new CredentialsConfig())) as CredentialsConfig }
}
