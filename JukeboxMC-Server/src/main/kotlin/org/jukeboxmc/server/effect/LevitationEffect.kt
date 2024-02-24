package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class LevitationEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 24
    }

    override fun getEffectType(): EffectType {
        return EffectType.LEVITATION
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