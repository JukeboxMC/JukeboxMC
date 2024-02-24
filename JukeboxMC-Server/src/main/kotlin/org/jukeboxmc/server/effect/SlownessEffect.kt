package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class SlownessEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 2
    }

    override fun getEffectType(): EffectType {
        return EffectType.SLOWNESS
    }

    override fun getEffectColor(): Color {
        return Color(90, 108, 129)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {
        entityLiving.setMovement(entityLiving.getMovement() * (1 - 0.15f * (this.getAmplifier() + 1)))
    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {
        entityLiving.setMovement(entityLiving.getMovement() / (1 - 0.15f * (this.getAmplifier() + 1)))
    }
}