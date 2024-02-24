package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class HealthBoostEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 21
    }

    override fun getEffectType(): EffectType {
        return EffectType.HEALTH_BOOST
    }

    override fun getEffectColor(): Color {
        return Color(248, 125, 35)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}