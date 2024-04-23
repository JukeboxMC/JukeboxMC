package org.jukeboxmc.server.plugin

import org.jukeboxmc.api.logger.Logger
import org.jukeboxmc.api.plugin.Plugin
import org.jukeboxmc.api.plugin.PluginLoadOrder
import org.jukeboxmc.api.plugin.PluginYAML
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes
import org.yaml.snakeyaml.Yaml
import java.io.DataInputStream
import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Path
import java.util.concurrent.atomic.AtomicReference
import java.util.jar.JarFile

open class PluginLoader(
    private val logger: Logger,
    private val pluginManager: JukeboxPluginManager
) {

    companion object {
        fun isJarFile(file: Path): Boolean {
            return file.fileName.toString().endsWith(".jar")
        }
    }

    fun loadPluginJAR(pluginConfig: PluginYAML, pluginJar: File?): Plugin? {
        val loader: PluginClassLoader
        try {
            loader = PluginClassLoader(pluginManager, this.javaClass.classLoader, pluginJar!!)
            pluginManager.pluginClassLoaders[pluginConfig.name] = loader
        } catch (e: MalformedURLException) {
            logger.error("Error while creating class loader(plugin=" + pluginConfig.name + ")" + e.message)
            return null
        }
        try {
            val mainClass = loader.loadClass(pluginConfig.main)
            if (!Plugin::class.java.isAssignableFrom(mainClass)) {
                return null
            }
            val castedMain: Class<out Plugin?> = mainClass.asSubclass(Plugin::class.java)
            val plugin: Plugin? = castedMain.getDeclaredConstructor().newInstance()
            plugin?.init(pluginConfig, pluginManager.getServer(), pluginJar)
            return plugin
        } catch (e: Exception) {
            logger.error("Error while loading plugin main class(main=" + pluginConfig.main + ",plugin=" + pluginConfig.name + ")" + e.message)
        }
        return null
    }

    fun loadPluginData(file: File, yaml: Yaml): PluginYAML? {
        try {
            JarFile(file).use { pluginJar ->
                val configEntry = pluginJar.getJarEntry("plugin.yml")
                if (configEntry == null) {
                    val pluginYAML = PluginYAML()
                    if (pluginYAML.name != null && pluginYAML.version != null && pluginYAML.author != null && pluginYAML.main != null) {
                        return pluginYAML
                    }
                    this.logger.warn("Jar file " + file.name + " doesnt contain a plugin.yml!")
                    return null
                }
                pluginJar.getInputStream(configEntry).use { fileStream ->
                    val pluginConfig: PluginYAML = yaml.loadAs(fileStream, PluginYAML::class.java)
                    if (pluginConfig.main != null && pluginConfig.name != null) {
                        return pluginConfig
                    }
                }
                this.logger.warn("Invalid plugin.yml for " + file.name + ": main and/or name property missing")
            }
        } catch (e: IOException) {
            this. logger.error("Error while reading plugin directory$e")
        }
        return null
    }
}