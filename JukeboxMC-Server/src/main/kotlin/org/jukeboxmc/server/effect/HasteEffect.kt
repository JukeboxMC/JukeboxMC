package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class HasteEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 3
    }

    override fun getEffectType(): EffectType {
        return EffectType.HASTE
    }

    override fun getEffectColor(): Color {
        return Color(217, 192, 67)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}