package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class BadOmenEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 28
    }

    override fun getEffectType(): EffectType {
        return EffectType.BAD_OMEN
    }

    override fun getEffectColor(): Color {
        return Color(11, 97, 56)
    }

    override fun update(currentTick: Long) {

    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}