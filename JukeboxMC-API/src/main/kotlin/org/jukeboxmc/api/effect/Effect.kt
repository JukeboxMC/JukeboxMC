package org.jukeboxmc.api.effect

import org.jukeboxmc.api.JukeboxMC
import java.awt.Color
import java.util.concurrent.TimeUnit

interface Effect {

    companion object {
        fun create(effectType: EffectType): Effect {
            return JukeboxMC.getServer().createEffect(effectType)
        }
    }

    fun getId(): Int

    fun getEffectType(): EffectType

    fun getEffectColor(): Color

    fun getAmplifier(): Int

    fun setAmplifier(value: Int)

    fun isVisible(): Boolean

    fun setVisible(value: Boolean)

    fun getDuration(): Int

    fun setDuration(value: Int)

    fun setDuration(value: Int, timeUnit: TimeUnit)

}