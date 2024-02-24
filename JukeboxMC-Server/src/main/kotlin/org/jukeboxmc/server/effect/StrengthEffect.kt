package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class StrengthEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 5
    }

    override fun getEffectType(): EffectType {
        return EffectType.STRENGTH
    }
    
    override fun getEffectColor(): Color {
        return Color(147, 36, 35)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}