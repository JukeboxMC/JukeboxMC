package org.jukeboxmc.server.entity

import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataType
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag

class Metadata {
    private val entityDataMap = EntityDataMap()

    fun setByte(entityData: EntityDataType<Byte>, value: Byte): Metadata {
        val oldValue = getByte(entityData)
        if (oldValue != value) {
            entityDataMap[entityData] = value
        }
        return this
    }

    fun getByte(entityData: EntityDataType<Byte>): Byte {
        return if (entityDataMap.containsKey(entityData)) entityDataMap[entityData]!! as Byte else 0
    }

    fun setLong(entityData: EntityDataType<Long>, value: Long): Metadata {
        val oldValue = getLong(entityData)
        if (oldValue != value) {
            entityDataMap[entityData] = value
        }
        return this
    }

    fun getLong(entityData: EntityDataType<Long>): Long {
        return if (entityDataMap.containsKey(entityData)) entityDataMap[entityData]!! as Long else 0L
    }

    fun setShort(entityData: EntityDataType<Short>, value: Short): Metadata {
        val oldValue = getShort(entityData)
        if (oldValue != value) {
            entityDataMap[entityData] = value
        }
        return this
    }

    fun getShort(entityData: EntityDataType<Short>): Short {
        return if (entityDataMap.containsKey(entityData)) entityDataMap[entityData]!! as Short else 0
    }

    fun setString(entityData: EntityDataType<String>, value: String): Metadata {
        val oldValue = getString(entityData)
        if (oldValue != value) {
            entityDataMap[entityData] = value
        }
        return this
    }

    fun getString(entityData: EntityDataType<String>): String {
        return if (entityDataMap.containsKey(entityData)) entityDataMap[entityData]!! as String else ""
    }

    fun setFloat(entityData: EntityDataType<Float>, value: Float): Metadata {
        val oldValue = getFloat(entityData)
        if (oldValue != value) {
            entityDataMap[entityData] = value
        }
        return this
    }

    fun getFloat(entityData: EntityDataType<Float>): Float {
        return if (entityDataMap.containsKey(entityData)) entityDataMap[entityData]!! as Float else 0F
    }

    fun setInt(entityData: EntityDataType<Int>, value: Int): Metadata {
        val oldValue = getInt(entityData)
        if (oldValue != value) {
            entityDataMap[entityData] = value
        }
        return this
    }

    fun getInt(entityData: EntityDataType<Int>): Int {
        return if (entityDataMap.containsKey(entityData)) entityDataMap[entityData]!! as Int else 0
    }

    fun setFlag(entityFlag: EntityFlag, value: Boolean): Metadata {
        val oldValue = getFlag(entityFlag)
        if (oldValue != value) {
            entityDataMap.setFlag(entityFlag, value)
        }
        return this
    }

    fun getFlag(entityFlag: EntityFlag): Boolean {
        return entityDataMap.getOrCreateFlags().contains(entityFlag)
    }

    fun getEntityDataMap(): EntityDataMap {
        return entityDataMap
    }
}
