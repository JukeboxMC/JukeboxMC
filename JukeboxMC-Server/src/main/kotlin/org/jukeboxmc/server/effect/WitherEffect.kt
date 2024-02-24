package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class WitherEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 20
    }

    override fun getEffectType(): EffectType {
        return EffectType.WITHER
    }

    override fun getEffectColor(): Color {
        return Color(53, 42, 39)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}