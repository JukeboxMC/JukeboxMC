package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.api.event.entity.EntityHealEvent
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class RegenerationEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 10
    }

    override fun getEffectType(): EffectType {
        return EffectType.REGENERATION
    }

    override fun getEffectColor(): Color {
        return Color(205, 92, 171)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {
        if (this.canExecute()) {
            entityLiving.heal(1, EntityHealEvent.Cause.REGENERATION_EFFECT)
        }
    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }

    override fun canExecute(): Boolean {
        val interval: Int
        if ((40 shr this.getAmplifier()).also { interval = it } > 0) {
            return (this.getDuration() % interval) == 0
        }
        return false
    }

}