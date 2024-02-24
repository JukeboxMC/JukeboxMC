package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class WeaknessEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 18
    }

    override fun getEffectType(): EffectType {
        return EffectType.WEAKNESS
    }

    override fun getEffectColor(): Color {
        return Color(72, 77, 72)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}