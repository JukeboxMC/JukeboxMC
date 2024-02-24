package org.jukeboxmc.server.entity

import org.cloudburstmc.protocol.bedrock.data.AttributeData

class JukeboxAttribute(
    private var identifier: String,
    private var minValue: Float,
    private var maxValue: Float,
    private val defaultValue: Float,
) {

    private var currentValue: Float = defaultValue
    private var dirty = true

    fun getIdentifier(): String {
        return this.identifier
    }

    fun getMinValue(): Float {
        return this.minValue
    }

    fun setMinValue(minValue: Float) {
        this.minValue = minValue
    }

    fun getMaxValue(): Float {
        return this.maxValue
    }

    fun setMaxValue(maxValue: Float) {
        this.maxValue = maxValue
    }

    fun getCurrentValue(): Float {
        return this.currentValue
    }

    fun setCurrentValue(currentValue: Float) {
        this.currentValue = currentValue
        this.dirty = true
    }

    fun isDirty(): Boolean {
        val value = this.dirty
        this.dirty = false
        return value
    }

    fun reset() {
        this.currentValue = this.defaultValue
        this.dirty = true
    }

    fun toNetwork(): AttributeData {
        return AttributeData(this.identifier, this.minValue, this.maxValue, this.currentValue, this.defaultValue)
    }
}