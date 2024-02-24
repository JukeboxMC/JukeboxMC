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
                    val entries = pluginJar.entries()
                    val pluginYAML = PluginYAML()
                    while (entries.hasMoreElements()) {
                        val entry = entries.nextElement()
                        if (entry.name.endsWith(".class")) {
                            val classReader =
                                ClassReader(DataInputStream(pluginJar.getInputStream(entry)))
                            if (classReader.superName == "org/jukeboxmc/api/plugin/Plugin") {
                                val pluginName = AtomicReference<String>()
                                val version = AtomicReference<String>()
                                val author = AtomicReference<String>()
                                val depends =
                                    AtomicReference<MutableList<String>>()
                                val pluginLoadOrder: AtomicReference<String> = AtomicReference(PluginLoadOrder.POSTWORLD.name)
                                classReader.accept(object : ClassVisitor(Opcodes.ASM7) {
                                    override fun visitAnnotation(
                                        descriptor: String,
                                        visible: Boolean
                                    ): AnnotationVisitor {
                                        when (descriptor) {
                                            "Lorg/jukeboxmc/api/plugin/annotation/PluginName;" -> {
                                                return object :
                                                    AnnotationVisitor(Opcodes.ASM7) {
                                                    override fun visit(key: String, value: Any) {
                                                        pluginName.set(value as String)
                                                    }
                                                }
                                            }
                                            "Lorg/jukeboxmc/api/plugin/annotation/Version;" -> {
                                                return object :
                                                    AnnotationVisitor(Opcodes.ASM7) {
                                                    override fun visit(key: String, value: Any) {
                                                        version.set(value as String)
                                                    }
                                                }
                                            }
                                            "Lorg/jukeboxmc/api/plugin/annotation/Author;" -> {
                                                return object :
                                                    AnnotationVisitor(Opcodes.ASM7) {
                                                    override fun visit(key: String, value: Any) {
                                                        author.set(value as String)
                                                    }
                                                }
                                            }
                                            "Lorg/jukeboxmc/api/plugin/annotation/Depends;" -> {
                                                return object :
                                                    AnnotationVisitor(Opcodes.ASM7) {
                                                    override fun visitArray(name: String): AnnotationVisitor {
                                                        depends.set(ArrayList())
                                                        return object :
                                                            AnnotationVisitor(Opcodes.ASM7) {
                                                            override fun visit(name: String, value: Any) {
                                                                depends.get().add(value as String)
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            "Lorg/jukeboxmc/api/plugin/annotation/Startup;" -> {
                                                return object :
                                                    AnnotationVisitor(Opcodes.ASM7) {
                                                    override fun visitEnum(
                                                        name: String,
                                                        descriptor: String,
                                                        value: String
                                                    ) {
                                                        pluginLoadOrder.set(value)
                                                    }
                                                }
                                            }
                                            else ->super.visitAnnotation(descriptor, visible)
                                        }
                                        return super.visitAnnotation(descriptor, visible)
                                    }
                                }, 0)
                                pluginYAML.main = classReader.className.replace("/", ".")
                                pluginYAML.name = pluginName.get()
                                pluginYAML.version = version.get()
                                pluginYAML.author = author.get()
                                pluginYAML.depends = depends.get()
                                pluginYAML.loadOrder = PluginLoadOrder.valueOf(pluginLoadOrder.get())
                            }
                        }
                    }
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