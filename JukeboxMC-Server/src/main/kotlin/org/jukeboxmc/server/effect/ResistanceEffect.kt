package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class ResistanceEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 11
    }

    override fun getEffectType(): EffectType {
        return EffectType.RESISTANCE
    }

    override fun getEffectColor(): Color {
        return Color(153, 69, 58)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}