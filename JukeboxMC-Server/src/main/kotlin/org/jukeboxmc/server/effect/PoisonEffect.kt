package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class PoisonEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 19
    }

    override fun getEffectType(): EffectType {
        return EffectType.POISON
    }

    override fun getEffectColor(): Color {
        return Color(78, 147, 49)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}