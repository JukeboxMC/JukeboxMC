package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class InstantHealthEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 6
    }

    override fun getEffectType(): EffectType {
        return EffectType.INSTANT_HEALTH
    }

    override fun getEffectColor(): Color {
        return Color(248, 36, 35)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}