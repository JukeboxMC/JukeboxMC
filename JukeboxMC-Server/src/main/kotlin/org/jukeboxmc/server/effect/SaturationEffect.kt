package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class SaturationEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 23
    }

    override fun getEffectType(): EffectType {
        return EffectType.SATURATION
    }

    override fun getEffectColor(): Color {
        return Color(255, 0, 255)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}