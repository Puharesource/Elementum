package io.puharesource.mc.elementum.core.api.config

import groovy.transform.CompileStatic
import io.puharesource.mc.elementum.core.ElementumCorePlugin as Core

/**
 * Created by Tarkan on 01-04-2015.
 * This class is under the GPLv3 license.
 */
@CompileStatic
final class MainConfig {
    private transient static MainConfig instance
    private transient static Config config = new Config(Core.getInstance().getDataFolder(), "config")

    protected MainConfig() {  }

    static MainConfig getConfig() { (instance ?: config.load(this.class, new MainConfig())) as MainConfig }
}
