package org.jukeboxmc.api.config

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import org.jukeboxmc.api.extensions.fromJson
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.io.*
import java.util.*

class Config(private val file: File?, private val configType: ConfigType) {
    private var inputStream: InputStream? = null
    private var configSection: ConfigSection = ConfigSection()
    private var gson: Gson? = null
    private var yaml: Yaml? = null
    private var properties: Properties? = null

    init {
        if (file != null && !file.exists()) {
            file.createNewFile()
        }
        load()
    }

    @Throws(IOException::class)
    private fun load() {
        (inputStream ?: file?.let { FileInputStream(it) })?.let { inputStream ->
            InputStreamReader(inputStream).use { reader ->
                when (configType) {
                    ConfigType.JSON -> {
                        val gsonBuilder = GsonBuilder()
                        gsonBuilder.setPrettyPrinting()
                        gsonBuilder.registerTypeAdapter(Double::class.java, JsonSerializer<Double> { src, _, _ ->
                            when (src) {
                                src.toInt().toDouble() -> {
                                    JsonPrimitive(src.toInt())
                                }
                                src.toLong().toDouble() -> {
                                    JsonPrimitive(src.toLong())
                                }
                                else -> {
                                    JsonPrimitive(src)
                                }
                            }
                        })
                        gson = gsonBuilder.create()
                        configSection = gson?.fromJson<LinkedHashMap<String, Any>>(reader)?.let { ConfigSection(it) } ?: return
                    }

                    ConfigType.YAML -> {
                        val dumperOptions = DumperOptions()
                        dumperOptions.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
                        yaml = Yaml(dumperOptions)
                        configSection = yaml?.load<LinkedHashMap<String, Any>>(reader)?.let { ConfigSection(it) } ?: return
                    }

                    ConfigType.PROPERTIES -> {
                        properties = Properties()
                        properties?.load(reader)
                    }
                }
            }
        }
    }


    fun setInputStream(inputStream: InputStream) {
        this.inputStream = inputStream
    }

    fun exists(key: String): Boolean {
        return when (configType) {
            ConfigType.JSON, ConfigType.YAML -> configSection.exists(key)
            ConfigType.PROPERTIES -> properties?.getProperty(key) != null
        }
    }

    fun set(key: String, value: Any) {
        when (configType) {
            ConfigType.JSON, ConfigType.YAML -> configSection[key] = value
            ConfigType.PROPERTIES -> properties?.setProperty(key, value.toString())
        }
    }

    fun remove(key: String) {
        when (configType) {
            ConfigType.JSON, ConfigType.YAML -> {
                configSection.remove(key)
                save()
            }
            ConfigType.PROPERTIES -> {
                properties?.remove(key)
                save()
            }
        }
    }

    fun addDefault(key: String, value: Any) {
        if (!exists(key)) {
            set(key, value)
            save()
        }
    }

    fun get(key: String): Any? {
        return when (configType) {
            ConfigType.JSON, ConfigType.YAML -> configSection.get(key)
            ConfigType.PROPERTIES -> properties?.getProperty(key)
        }
    }

    fun getStringList(key: String): List<String?> {
        return configSection.getStringList(key)
    }

    fun getString(key: String): String {
        return configSection.getString(key)
    }

    fun getIntegerList(key: String): List<Int?> {
        return configSection.getIntegerList(key)
    }

    fun getInt(key: String): Int {
        return configSection.getInt(key)
    }

    fun getLongList(key: String): List<Long?> {
        return configSection.getLongList(key)
    }

    fun getLong(key: String): Long {
        return configSection.getLong(key)
    }

    fun getDoubleList(key: String): List<Double?> {
        return configSection.getDoubleList(key)
    }

    fun getDouble(key: String): Double {
        return configSection.getDouble(key)
    }

    fun getFloatList(key: String): List<Float?> {
        return configSection.getFloatList(key)
    }

    fun getFloat(key: String): Float {
        return configSection.getFloat(key)
    }

    fun getByteList(key: String): List<Byte?> {
        return configSection.getByteList(key)
    }

    fun getByte(key: String): Byte {
        return configSection.getByte(key)
    }

    fun getShortList(key: String): List<Short?> {
        return configSection.getShortList(key)
    }

    fun getShort(key: String): Short {
        return configSection.getShort(key)
    }

    fun getBooleanList(key: String): List<Boolean?> {
        return configSection.getBooleanList(key)
    }

    fun getBoolean(key: String): Boolean {
        return configSection.getBoolean(key)
    }

    fun getMap(): Map<String, Any> {
        return configSection
    }

    fun getKeys(): Set<String> {
        return configSection.keys
    }

    fun getValues(): Collection<Any> {
        return configSection.values
    }

    fun getConfigSection(): ConfigSection {
        return configSection
    }

    fun toJSON(): String {
        val objectMapper = ObjectMapper()
        try {
            return objectMapper.activateDefaultTyping(objectMapper.polymorphicTypeValidator).writeValueAsString(this)
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class)
    fun save() {
        if (file == null) {
            throw IOException("This config cannot be saved!")
        }
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        OutputStreamWriter(FileOutputStream(file)).use { writer ->
            when (configType) {
                ConfigType.JSON -> gson?.toJson(configSection, writer)
                ConfigType.YAML -> yaml?.dump(configSection, writer)
                ConfigType.PROPERTIES -> properties?.store(writer, "")
            }
        }
    }
}
