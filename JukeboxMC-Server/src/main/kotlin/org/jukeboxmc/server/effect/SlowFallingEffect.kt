package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class SlowFallingEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 25
    }

    override fun getEffectType(): EffectType {
        return EffectType.SLOW_FALLING
    }

    override fun getEffectColor(): Color {
        return Color(206, 255, 255)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {
    
    }
}