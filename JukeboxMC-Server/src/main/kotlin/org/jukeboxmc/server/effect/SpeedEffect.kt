package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.Attribute
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class SpeedEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 1
    }

    override fun getEffectType(): EffectType {
        return EffectType.SPEED
    }

    override fun getEffectColor(): Color {
        return Color(124, 175, 198)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {
        entityLiving.setMovement((this.getAmplifier() + 1) * 0.2f)
    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {
        entityLiving.getAttribute(Attribute.MOVEMENT).reset()
    }
}