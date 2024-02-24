package org.jukeboxmc.api.plugin

import org.jukeboxmc.api.Server
import org.jukeboxmc.api.logger.Logger
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.jar.JarFile


abstract class Plugin {

    private var enabled = false
    private var description: PluginYAML? = null
    private var server: Server? = null
    private var pluginFile: File? = null
    var dataFolder: File? = null
        private set
    private var logger: Logger? = null
    private var initialized = false

    fun init(description: PluginYAML, server: Server, pluginFile: File?) {
        if (initialized) server.getLogger().info("Plugin has been already initialized!")
        initialized = true
        this.description = description
        this.server = server
        this.logger = server.getLogger()
        this.pluginFile = pluginFile
        this.dataFolder = File("${server.getPluginFolder()}/${description.name}/")
        if (!this.dataFolder!!.exists()) {
            this.dataFolder!!.mkdirs()
        }
    }

    /**
     * Called when the plugin is loaded into the server, but before it was enabled.
     * Can be used to load important information or to establish connections
     */
    open fun onStartup() {}

    /**
     * Called when the base server startup is done and the plugins are getting enabled.
     * Also called whenever the plugin state changes to enabled
     */
    abstract fun onEnable()

    /**
     * Called on server shutdown, or when the plugin gets disabled, for example by another plugin or when an error occurred.
     * Also gets called when the plugin state changes to disabled
     */
    open fun onDisable() {}

    /**
     * @param filename the file name to read
     * @return Returns a file from inside the plugin jar as an InputStream
     */
    fun getResourceFile(filename: String?): InputStream? {
        try {
            val pluginJar = JarFile(pluginFile)
            val entry = pluginJar.getJarEntry(filename)
            return pluginJar.getInputStream(entry)
        } catch (e: IOException) {
            logger?.error("Can not get plugin resource!")
        }
        return null
    }

    @JvmOverloads
    fun saveResource(filename: String?, replace: Boolean = false): Boolean {
        return this.saveResource(filename, filename, replace)
    }

    /**
     * Saves a resource from the plugin jar's resources to the plugin folder
     *
     * @param filename   the name of the file in the jar's resources
     * @param outputName the name the file should be saved as in the plugin folder
     * @param replace    whether the file should be replaced even if present already
     * @return returns false if an exception occurred, the file already exists and shouldn't be replaced, and when the file could
     * not be found in the jar
     * returns true if the file overwrite / copy was successful
     */
    fun saveResource(filename: String?, outputName: String?, replace: Boolean): Boolean {
        if (filename != null && filename.trim { it <= ' ' }.isNotEmpty()) {
            logger?.error("Filename can not be null!")
        }
        val file = File(dataFolder, outputName)
        if (file.exists() && !replace) {
            return false
        }
        try {
            getResourceFile(filename).use { resource ->
                if (resource == null) {
                    return false
                }
                val outFolder = file.parentFile
                if (!outFolder.exists()) {
                    outFolder.mkdirs()
                }
                this.writeFile(file, resource)
            }
        } catch (e: IOException) {
            logger?.error("Can not save plugin file!")
            return false
        }
        return true
    }

    fun isEnabled(): Boolean {
        return enabled
    }

    /**
     * Changes the plugin's state
     *
     * @param enabled whether the plugin should be enabled or disabled
     * @throws RuntimeException Thrown whenever an uncaught error occurred in onEnable() or onDisable() of a plugin
     */
    @Throws(RuntimeException::class)
    fun setEnabled(enabled: Boolean) {
        if (this.enabled == enabled) {
            return
        }
        this.enabled = enabled
        try {
            if (enabled) {
                onEnable()
            } else {
                onDisable()
            }
        } catch (e: Exception) {
            throw RuntimeException(
                "Can not " + (if (enabled) "enable" else "disable") + " plugin " + description?.name + "!",
                e
            )
        }
    }

    fun getDescription(): PluginYAML? {
        return description
    }

    fun getName(): String {
        return description?.name!!
    }

    fun getVersion(): String {
        return description?.version!!
    }

    fun getAuthor(): String {
        return description?.author!!
    }

    fun getAuthors(): List<String> {
        return description?.authors!!
    }

    fun getLoadOrder(): PluginLoadOrder {
        return description?.loadOrder!!
    }

    fun getServer(): Server {
        return server!!
    }

    fun getLogger(): Logger {
        return logger!!
    }

    open fun writeFile(file: File, content: InputStream?) {
        requireNotNull(content) { "Content must not be null!" }
        if (!file.exists()) {
            file.createNewFile()
        }
        val stream = FileOutputStream(file)
        val buffer = ByteArray(1024)
        var length: Int
        while (content.read(buffer).also { length = it } != -1) {
            stream.write(buffer, 0, length)
        }
        content.close()
        stream.close()
    }
}

