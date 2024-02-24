package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class InstantDamageEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 7
    }

    override fun getEffectType(): EffectType {
        return EffectType.INSTANT_DAMAGE
    }

    override fun getEffectColor(): Color {
        return Color(67, 10, 9)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}