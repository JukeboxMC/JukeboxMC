package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class JumpBoostEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 8
    }

    override fun getEffectType(): EffectType {
        return EffectType.JUMP_BOOST
    }

    override fun getEffectColor(): Color {
        return Color(34, 255, 76)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}