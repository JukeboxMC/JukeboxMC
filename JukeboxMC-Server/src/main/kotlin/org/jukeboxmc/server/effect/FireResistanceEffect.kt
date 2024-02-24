package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class FireResistanceEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 12
    }

    override fun getEffectType(): EffectType {
        return EffectType.FIRE_RESISTANCE
    }

    override fun getEffectColor(): Color {
        return Color(228, 154, 58)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}