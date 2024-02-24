package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class DarknessEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 30
    }

    override fun getEffectType(): EffectType {
        return EffectType.DARKNESS
    }

    override fun getEffectColor(): Color {
        return Color(41, 39, 33)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}