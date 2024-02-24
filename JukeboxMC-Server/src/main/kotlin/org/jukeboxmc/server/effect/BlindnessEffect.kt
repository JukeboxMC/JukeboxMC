package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class BlindnessEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 15
    }

    override fun getEffectType(): EffectType {
        return EffectType.BLINDNESS
    }

    override fun getEffectColor(): Color {
        return Color(191, 192, 192)
    }

    override fun update(currentTick: Long) {

    }
    
    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}