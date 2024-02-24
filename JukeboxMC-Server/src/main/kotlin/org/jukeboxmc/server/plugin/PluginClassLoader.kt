package org.jukeboxmc.server.plugin

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import java.io.File
import java.net.URL
import java.net.URLClassLoader


class PluginClassLoader(private val pluginManager: JukeboxPluginManager, parent: ClassLoader?, file: File) :
    URLClassLoader(arrayOf<URL>(file.toURI().toURL()), parent) {

    private val classes: Object2ObjectOpenHashMap<String, Class<*>> = Object2ObjectOpenHashMap<String, Class<*>>()

    override fun findClass(name: String): Class<*>? {
        return this.findClass(name, true)
    }

    fun findClass(name: String, checkGlobal: Boolean): Class<*>? {
        var result: Class<*>? = classes[name]
        if (result != null) {
            return result
        }
        if (checkGlobal) {
            result = pluginManager.getClassFromCache(name)
        }
        if (result == null) {
            if (super.findClass(name).also { result = it } != null) {
                pluginManager.cacheClass(name, result)
            }
        }
        this.classes[name] = result
        return result
    }
}

