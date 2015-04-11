package io.puharesource.mc.elementum.core.api.config

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

/**
 * Created by Tarkan on 05-04-2015.
 * This class is under the GPLv3 license.
 */
@SerializableAs("config")
class YamlConfigTest implements ConfigurationSerializable {

    private final Map<String, Object> map

    String someString = "This is the default text of someString."
    int someInt = 1
    List<String> someList = ["Line #1", "Line #2", "Line #3", "Line#4"]
    float someFloat = 1.1f

    YamlConfigTest(Map<String, Object> map) {
        this.map = map
    }

    @Override
    Map<String, Object> serialize() { map }

    static YamlConfigTest deserialize(Map<String, Object> map) { new YamlConfigTest(map) }

    static YamlConfigTest valueOf(Map<String, Object> map) { new YamlConfigTest(map) }
}
