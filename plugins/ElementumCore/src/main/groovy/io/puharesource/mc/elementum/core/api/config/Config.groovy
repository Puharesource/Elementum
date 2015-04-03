package io.puharesource.mc.elementum.core.api.config
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.transform.TypeChecked

import java.nio.file.Files
/**
 * Created by Tarkan on 01-04-2015.
 * This class is under the GPLv3 license.
 */
class Config {
    private transient File path
    private transient File file
    private transient String fileName
    private transient Gson gson

    Config(File path, String fileName) {
        this.path = path
        this.fileName = fileName.toLowerCase() + ".json"
        file = new File(path, this.fileName)
        gson = new GsonBuilder().setPrettyPrinting().create()
    }

    @TypeChecked
    public <T>T load(Class<T> clazz, Object defaults) {
        if (!path.exists()) path.mkdirs()

        try {
            if (!file.exists()) {
                file.createNewFile()
                file.setText(gson.toJson(defaults))
            }
        } catch (IOException e) {
            e.printStackTrace()
        }

        return gson.fromJson(file.getText("utf-8"), T.class)
    }

    void save() {
        try {
            file.setText(gson.toJson(this), "utf-8")
        } catch (IOException e) {
            e.printStackTrace()
        }
    }

    void backupToFile(File folder, String fileName) {
        try {
            Files.copy(file.toPath(), new File(folder, fileName).toPath())
        } catch (IOException e) {
            e.printStackTrace()
        }
    }

    Config getConfig() { this }
}
