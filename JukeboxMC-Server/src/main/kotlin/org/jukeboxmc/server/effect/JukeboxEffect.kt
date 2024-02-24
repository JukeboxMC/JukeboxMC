package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.Effect
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.util.concurrent.TimeUnit

abstract class JukeboxEffect : Effect {

    private var amplifier = 0
    private var visible = true
    private var duration = 0

    abstract fun update(currentTick: Long)
    
    abstract fun apply(entityLiving: JukeboxEntityLiving)

    abstract fun remove(entityLiving: JukeboxEntityLiving)

    open fun canExecute(): Boolean {
        return false
    }

    override fun getAmplifier(): Int {
        return this.amplifier
    }

    override fun setAmplifier(value: Int) {
        this.amplifier = value
    }

    override fun isVisible(): Boolean {
        return this.visible
    }

    override fun setVisible(value: Boolean) {
        this.visible = value
    }

    override fun getDuration(): Int {
        return this.duration
    }

    override fun setDuration(value: Int) {
        this.duration = value
    }

    override fun setDuration(value: Int, timeUnit: TimeUnit) {
        this.duration = (timeUnit.toMillis(value.toLong()) / 50).toInt()
    }

    fun getColor(): IntArray {
        val color = this.getEffectColor()
        return intArrayOf(color.red, color.green, color.blue)
    }
}