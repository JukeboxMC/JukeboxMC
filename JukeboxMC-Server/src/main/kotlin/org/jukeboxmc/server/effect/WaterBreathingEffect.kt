package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class WaterBreathingEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 13
    }

    override fun getEffectType(): EffectType {
        return EffectType.WATER_BREATHING
    }

    override fun getEffectColor(): Color {
        return Color(46, 82, 153)
    }
    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {
    }
}