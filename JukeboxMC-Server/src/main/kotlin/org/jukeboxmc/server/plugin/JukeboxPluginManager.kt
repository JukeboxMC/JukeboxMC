package org.jukeboxmc.server.plugin

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import it.unimi.dsi.fastutil.objects.Object2ObjectMap
import org.jukeboxmc.api.Server
import org.jukeboxmc.api.event.*
import org.jukeboxmc.api.extensions.asType
import org.jukeboxmc.api.extensions.isType
import org.jukeboxmc.api.plugin.Plugin
import org.jukeboxmc.api.plugin.PluginLoadOrder
import org.jukeboxmc.api.plugin.PluginManager
import org.jukeboxmc.api.plugin.PluginYAML
import org.jukeboxmc.server.JukeboxServer
import org.yaml.snakeyaml.LoaderOptions
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor
import java.io.IOException
import java.lang.reflect.InvocationTargetException
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

class JukeboxPluginManager(
    private val server: JukeboxServer
) : PluginManager {

    private val pluginLoader: PluginLoader = PluginLoader(server.getLogger(), this)

    private val yamlLoader = Yaml(CustomClassLoaderConstructor(this.javaClass.classLoader, LoaderOptions()))
    private val pluginMap: Object2ObjectMap<String, Plugin> = Object2ObjectArrayMap()
    private val cachedClasses: Object2ObjectMap<String, Class<*>> = Object2ObjectArrayMap()
    val pluginClassLoaders: Object2ObjectMap<String, PluginClassLoader> = Object2ObjectArrayMap()
    private val listeners: MutableMap<Class<out Event?>, MutableMap<EventPriority, MutableList<RegisteredListener>>> =
        HashMap()

    init {
        this.loadPluginsIn(this.server.getPluginFolder().toPath())
    }

    fun getClassFromCache(className: String?): Class<*>? {
        var clazz = cachedClasses[className]
        if (clazz != null) {
            return clazz
        }
        for (loader in pluginClassLoaders.values) {
            try {
                if (loader.findClass(className!!, false).also { clazz = it } != null) {
                    return clazz
                }
            } catch (e: ClassNotFoundException) {
                //ignore
            }
        }
        return null
    }

    fun cacheClass(className: String?, clazz: Class<*>?) {
        cachedClasses.putIfAbsent(className, clazz)
    }

    fun enableAllPlugins(pluginLoadOrder: PluginLoadOrder?) {
        val failed = LinkedList<Plugin?>()
        for (plugin in pluginMap.values) {
            if (plugin.getLoadOrder() == pluginLoadOrder) {
                if (!enablePlugin(plugin, null)) {
                    failed.add(plugin)
                }
            }
        }
        if (!failed.isEmpty()) {
            val builder = StringBuilder("§cFailed to load plugins: §e")
            while (failed.peek() != null) {
                val plugin = failed.poll()
                builder.append(plugin!!.getName())
                if (failed.peek() != null) {
                    builder.append(", ")
                }
            }
            this.server.getLogger().info(builder.toString())
        }
    }

    private fun loadPluginsIn(folderPath: Path?, directStartup: Boolean = false) {
        try {
            val pluginPaths = Files.walk(folderPath)
            pluginPaths.filter { path: Path? ->
                path.let {
                    Files.isRegularFile(it!!)
                }
            }.filter(PluginLoader::isJarFile)
                .forEach { jarPath: Path? ->
                    this.loadPlugin(
                        jarPath!!,
                        directStartup
                    )
                }
        } catch (e: IOException) {
            this.server.getLogger().error("Error while filtering plugin files $e")
        }
    }

    private fun enablePlugin(plugin: Plugin, parent: String?): Boolean {
        if (plugin.isEnabled()) return true
        val pluginName = plugin.getName()
        if (plugin.getDescription()?.depends != null) {
            for (depend in plugin.getDescription()?.depends!!) {
                if (depend == parent) {
                    this.server.getLogger().warn("§cCan not enable plugin $pluginName circular dependency $parent!")
                    return false
                }
                val dependPlugin: Plugin? = this.getPlugin(depend)
                if (dependPlugin == null) {
                    this.server.getLogger().warn("§cCan not enable plugin $pluginName missing dependency $depend!")
                    return false
                }
                if (!dependPlugin.isEnabled() && !enablePlugin(dependPlugin, pluginName)) {
                    return false
                }
            }
        }
        try {
            plugin.setEnabled(true)
            this.server.getLogger()
                .info("Loaded plugin " + plugin.getName() + " Version: " + plugin.getVersion() + " successfully!")
        } catch (e: java.lang.RuntimeException) {
            e.printStackTrace()
            return false
        }
        return true
    }

    override fun getPlugin(name: String): Plugin? {
        return this.pluginMap[name]
    }

    override fun getPlugins(): Collection<Plugin> {
        return this.pluginMap.values
    }

    override fun loadPlugin(path: Path, directStartup: Boolean): Plugin? {
        if (!Files.isRegularFile(path) || !PluginLoader.isJarFile(path)) {
            this.server.getLogger().warn("Cannot load plugin: Provided file is no jar file: " + path.fileName)
            return null
        }

        val pluginFile = path.toFile()
        if (!pluginFile.exists()) {
            return null
        }

        val config: PluginYAML = this.pluginLoader.loadPluginData(pluginFile, yamlLoader) ?: return null

        if (this.getPlugin(config.name!!) != null) {
            this.server.getLogger().warn("Plugin is already loaded: " + config.name)
            return null
        }

        val plugin: Plugin = this.pluginLoader.loadPluginJAR(config, pluginFile) ?: return null

        pluginMap[config.name] = plugin

        plugin.onStartup()
        if (directStartup) {
            try {
                plugin.setEnabled(true)
            } catch (e: Exception) {
                this.server.getLogger().error("Direct startup failed!" + e.message)
            }
        }
        return plugin
    }

    override fun loadPlugin(path: Path): Plugin? {
        return this.loadPlugin(path, false)
    }

    override fun disablePlugins() {
        for (plugin in this.pluginMap.values) {
            this.server.getLogger().info("Disabling plugin " + plugin.getName())
            try {
                plugin.setEnabled(false)
            } catch (e: RuntimeException) {
                e.printStackTrace()
            }
        }
    }

    override fun registerListener(listener: Listener) {
        val listenerClass: Class<out Listener?> = listener::class.java
        for (method in listenerClass.declaredMethods) {
            val eventHandler: EventHandler = method.getAnnotation(EventHandler::class.java) ?: continue
            val eventPriority: EventPriority = eventHandler.priority
            if (method.parameterTypes.size != 1 || !Event::class.java.isAssignableFrom(method.parameterTypes[0])) {
                continue
            }
            val eventClass = method.parameterTypes[0]
            if (eventClass.isType<Class<out Event?>>()) {
                listeners.putIfAbsent(eventClass.asType(), LinkedHashMap())
            }
            listeners[eventClass]!!.putIfAbsent(eventPriority, ArrayList())
            listeners[eventClass]!![eventPriority]?.add(RegisteredListener(method, listener))
        }
    }

    override fun callEvent(event: Event) {
        val eventPriorityListMap = listeners[event::class.java]
        if (eventPriorityListMap != null) {
            for (eventPriority in EventPriority.entries) {
                val methods = eventPriorityListMap[eventPriority]
                if (methods != null) {
                    for (registeredListener in methods) {
                        try {
                            registeredListener.methode.invoke(registeredListener.listener, event)
                        } catch (e: IllegalAccessException) {
                            e.printStackTrace()
                        } catch (e: InvocationTargetException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }

    override fun getServer(): Server {
        return this.server
    }
}