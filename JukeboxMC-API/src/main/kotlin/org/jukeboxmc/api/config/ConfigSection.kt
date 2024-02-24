package org.jukeboxmc.api.config

import org.jukeboxmc.api.extensions.asType
import org.jukeboxmc.api.extensions.isType
import java.util.function.Consumer

class ConfigSection() : LinkedHashMap<String, Any>() {

    constructor(map: LinkedHashMap<String, Any>) : this() {
        if (map.isEmpty()) return
        for ((key, value) in map) {
            if(value.isType<LinkedHashMap<String, Any>>()) {
                put(key, ConfigSection(value.asType()))
            }else{
                put(key, value)
            }
        }
    }

    private fun parseList(list: List<Any?>): List<Any> {
        val newList: MutableList<Any> = ArrayList()
        for (value in list.filterNotNull()) {
            if(value.isType<LinkedHashMap<String, Any>>()) {
                newList.add(ConfigSection(value.asType()))
            } else {
                newList.add(value)
            }
        }
        return newList
    }

    fun getAllMap(): Map<String?, Any?> {
        return java.util.LinkedHashMap(this)
    }

    fun getAll(): ConfigSection {
        return ConfigSection(this)
    }

    fun <T> getObject(key: String?, defaultValue: T): T {
        if (key.isNullOrEmpty()) {
            return defaultValue
        }
        if (super.containsKey(key)) {
            return super.get(key) as? T ?: defaultValue
        }
        val keys = key.split("\\.".toRegex(), limit = 2).toTypedArray()
        if (!super.containsKey(keys[0])) {
            return defaultValue
        }
        val value = super.get(keys[0])
        if (value is ConfigSection) {
            return value.getObject(keys[1], defaultValue)
        }
        return defaultValue
    }

    fun getObject(key: String?): Any {
        return this.getObject<Any?>(key, null)!!
    }

    operator fun set(key: String, value: Any?) {
        val subKeys = key.split("\\.".toRegex(), limit = 2).toTypedArray()
        if (subKeys.size > 1) {
            var childSection: ConfigSection? = ConfigSection()
            if (this.containsKey(subKeys[0]) && super.get(subKeys[0]) is ConfigSection) {
                childSection = super.get(subKeys[0]) as ConfigSection?
            }
            childSection!![subKeys[1]] = value
            super.put(subKeys[0], childSection)
        } else super.put(subKeys[0], value!!)
    }

    fun isSection(key: String?): Boolean {
        val value = this.getObject(key)
        return value is ConfigSection
    }

    fun getSection(key: String?): ConfigSection? {
        return this.getObject(key, ConfigSection())
    }

    fun getSections(): ConfigSection {
        return getSections(null)
    }

    fun getSections(key: String?): ConfigSection {
        val sections = ConfigSection()
        val parent = (if (key.isNullOrEmpty()) getAll() else getSection(key)) ?: return sections
        parent.forEach { key1: String, value: Any? ->
            if (value is ConfigSection) sections.put(
                key1,
                value
            )
        }
        return sections
    }

    fun getInt(key: String?): Int {
        return this.getInt(key, 0)
    }

    fun getInt(key: String?, defaultValue: Int): Int {
        return this.getObject(key, defaultValue).toInt()
    }

    fun isInt(key: String?): Boolean {
        val `val` = getObject(key)
        return `val` is Int
    }

    fun getLong(key: String?): Long {
        return this.getLong(key, 0)
    }

    fun getLong(key: String?, defaultValue: Long): Long {
        return this.getObject(key, defaultValue).toLong()
    }

    fun isLong(key: String?): Boolean {
        val `val` = getObject(key)
        return `val` is Long
    }

    fun getDouble(key: String?): Double {
        return this.getDouble(key, 0.0)
    }

    fun getDouble(key: String?, defaultValue: Double): Double {
        return this.getObject(key, defaultValue).toDouble()
    }

    fun isDouble(key: String?): Boolean {
        val `val` = getObject(key)
        return `val` is Double
    }

    fun getString(key: String?): String {
        return this.getString(key, "")
    }

    fun getString(key: String?, defaultValue: String): String {
        val result: Any? = this.getObject(key, defaultValue)
        return result.toString()
    }

    fun isString(key: String?): Boolean {
        val `val` = getObject(key)
        return `val` is String
    }

    fun getBoolean(key: String?): Boolean {
        return this.getBoolean(key, false)
    }

    fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
        return this.getObject(key, defaultValue)
    }

    fun isBoolean(key: String?): Boolean {
        val `val` = getObject(key)
        return `val` is Boolean
    }

    fun getFloat(key: String?): Float {
        return this.getFloat(key, 0f)
    }

    fun getFloat(key: String?, defaultValue: Float): Float {
        return this.getObject(key, defaultValue)
    }

    fun isFloat(key: String?): Boolean {
        val `val` = getObject(key)
        return `val` is Float
    }

    fun getByte(key: String?): Byte {
        return this.getByte(key, 0.toByte())
    }

    fun getByte(key: String?, defaultValue: Byte): Byte {
        return this.getObject(key, defaultValue).toByte()
    }

    fun isByte(key: String?): Boolean {
        val `val` = getObject(key)
        return `val` is Byte
    }

    fun getShort(key: String?): Short {
        return this.getShort(key, 0.toShort())
    }

    fun getShort(key: String?, defaultValue: Short): Short {
        return this.getObject(key, defaultValue).toShort()
    }

    fun isShort(key: String?): Boolean {
        val `val` = getObject(key)
        return `val` is Short
    }

    fun getList(key: String?): List<*>? {
        return this.getList(key, null)
    }

    fun getList(key: String?, defaultList: List<*>?): List<*>? {
        return this.getObject(key, defaultList)
    }

    fun isList(key: String?): Boolean {
        val `val` = getObject(key)
        return `val` is List<*>
    }

    fun getStringList(key: String?): List<String?> {
        val value = this.getList(key) ?: return ArrayList(0)
        val result: MutableList<String?> = ArrayList()
        for (o in value) {
            if (o is String || o is Number || o is Boolean || o is Char) {
                result.add(o.toString())
            }
        }
        return result
    }

    fun getIntegerList(key: String?): List<Int?> {
        val list = getList(key) ?: return ArrayList(0)
        val result: MutableList<Int?> = ArrayList()
        for (listValue in list) {
            when (listValue) {
                is Int -> {
                    result.add(listValue as Int?)
                }
                is String -> {
                    try {
                        result.add(Integer.valueOf(listValue as String?))
                    } catch (ignore: Exception) {}
                }
                is Char -> {
                    result.add(listValue.code)
                }
                is Number -> {
                    result.add(listValue.toInt())
                }
            }
        }
        return result
    }

    fun getBooleanList(key: String?): List<Boolean?> {
        val list = getList(key) ?: return ArrayList(0)
        val result: MutableList<Boolean?> = ArrayList()
        for (listValue in list) {
            if (listValue is Boolean) {
                result.add(listValue)
            } else if (listValue is String) {
                if (java.lang.Boolean.TRUE.toString() == listValue) {
                    result.add(true)
                } else if (java.lang.Boolean.FALSE.toString() == listValue) {
                    result.add(false)
                }
            }
        }
        return result
    }

    fun getDoubleList(key: String?): List<Double?> {
        val list = getList(key) ?: return ArrayList(0)
        val result: MutableList<Double?> = ArrayList()
        for (listValue in list) {
            when (listValue) {
                is Double -> {
                    result.add(listValue as Double?)
                }
                is String -> {
                    try {
                        result.add(java.lang.Double.valueOf(listValue as String?))
                    } catch (ignore: Exception) {}
                }
                is Char -> {
                    result.add(listValue.code.toDouble())
                }
                is Number -> {
                    result.add(listValue.toDouble())
                }
            }
        }
        return result
    }

    fun getFloatList(key: String?): List<Float?> {
        val list = getList(key) ?: return ArrayList(0)
        val result: MutableList<Float?> = ArrayList()
        for (listValue in list) {
            when (listValue) {
                is Float -> {
                    result.add(listValue as Float?)
                }
                is String -> {
                    try {
                        result.add(java.lang.Float.valueOf(listValue as String?))
                    } catch (ignore: Exception) {}
                }
                is Char -> {
                    result.add(listValue.code.toFloat())
                }
                is Number -> {
                    result.add(listValue.toFloat())
                }
            }
        }
        return result
    }

    fun getLongList(key: String?): List<Long?> {
        val list = getList(key) ?: return ArrayList(0)
        val result: MutableList<Long?> = ArrayList()
        for (listValue in list) {
            when (listValue) {
                is Long -> {
                    result.add(listValue as Long?)
                }
                is String -> {
                    try {
                        result.add(java.lang.Long.valueOf(listValue as String?))
                    } catch (ignore: Exception) {}
                }
                is Char -> {
                    result.add(listValue.code.toLong())
                }
                is Number -> {
                    result.add(listValue.toLong())
                }
            }
        }
        return result
    }

    fun getByteList(key: String?): List<Byte?> {
        val list = getList(key) ?: return ArrayList(0)
        val result: MutableList<Byte?> = ArrayList()
        for (listValue in list) {
            when (listValue) {
                is Byte -> {
                    result.add(listValue as Byte?)
                }
                is String -> {
                    try {
                        result.add(java.lang.Byte.valueOf(listValue as String?))
                    } catch (ignore: Exception) {}
                }
                is Char -> {
                    result.add(listValue.code.toByte())
                }
                is Number -> {
                    result.add(listValue.toByte())
                }
            }
        }
        return result
    }

    fun getCharacterList(key: String?): List<Char?> {
        val list = getList(key) ?: return ArrayList(0)
        val result: MutableList<Char?> = ArrayList()
        for (listValue in list) {
            if (listValue is Char) {
                result.add(listValue as Char?)
            } else if (listValue is String) {
                if (listValue.length == 1) {
                    result.add(listValue[0])
                }
            } else if (listValue is Number) {
                result.add(listValue.toInt().toChar())
            }
        }
        return result
    }

    fun getShortList(key: String?): List<Short?> {
        val list = getList(key) ?: return ArrayList(0)
        val result: MutableList<Short?> = ArrayList()
        for (listValue in list) {
            when (listValue) {
                is Short -> {
                    result.add(listValue as Short?)
                }
                is String -> {
                    try {
                        result.add(listValue.toShort())
                    } catch (ignore: Exception) {}
                }
                is Char -> {
                    result.add(listValue.code.toShort())
                }
                is Number -> {
                    result.add(listValue.toShort())
                }
            }
        }
        return result
    }

    fun getMapList(key: String): List<Map<String, Any>> {
        val list: List<*>? = getList(key)
        val result = ArrayList<Map<String, Any>>()
        if (list == null) {
            return result
        }
        for (element in list) {
            if (element is Map<*, *>) {
                @Suppress("UNCHECKED_CAST")
                result.add(element as Map<String, Any>)
            }
        }
        return result
    }

    fun exists(key: String, ignoreCase: Boolean): Boolean {
        var keyValue = key
        if (ignoreCase) {
            keyValue = key.lowercase()
        }
        for (existKey in this.getKeys(true)) {
            var existKeyValue = existKey
            if (ignoreCase) {
                existKeyValue = existKey.lowercase()
            }
            if (existKeyValue == keyValue) return true
        }
        return false
    }

    fun exists(key: String): Boolean {
        return exists(key, false)
    }

    fun remove(key: String?) {
        if (key.isNullOrEmpty()) return
        if (super.containsKey(key)) super.remove(key) else if (this.containsKey(".")) {
            val keys = key.split("\\.".toRegex(), limit = 2).toTypedArray()
            if (super.get(keys[0]) is ConfigSection) {
                val section = super.get(keys[0]) as ConfigSection?
                section!!.remove(keys[1])
            }
        }
    }

    fun getKeys(child: Boolean): Set<String> {
        val keys: MutableSet<String> = LinkedHashSet()
        this.forEach { key: String, value: Any? ->
            keys.add(key)
            if (value is ConfigSection) {
                if (child) value.getKeys(true)
                    .forEach(Consumer { childKey: String ->
                        keys.add(
                            "$key.$childKey"
                        )
                    })
            }
        }
        return keys
    }
}
