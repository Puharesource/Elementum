package io.puharesource.mc.elementum.core
import groovy.transform.CompileStatic

import java.util.logging.Logger
/**
 * Created by Tarkan on 01-04-2015.
 * This class is under the GPLv3 license.
 */
@CompileStatic
final class LogManager {
    private static final Logger LOGGER = Logger.getLogger("Elementum")

    static Logger getLogger() { LOGGER }
}
