package io.puharesource.mc.elementum.core.api.config.serialization
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked
/**
 * Created by Tarkan on 05-04-2015.
 * This class is under the GPLv3 license.
 */
@CompileStatic
class YamlSerializer {
    static Map<String, Object> getSerializedMap(Object o) {
        for (Map.Entry<String, Object> entry : o.getProperties().entrySet()) {

        }
    }

    @TypeChecked
    static <T>T loadConfig(T confingClass, boolean saveDefaults) {
        
    }
}
