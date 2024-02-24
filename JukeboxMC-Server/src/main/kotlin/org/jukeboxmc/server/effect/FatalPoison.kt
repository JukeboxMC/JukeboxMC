package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.api.event.entity.EntityDamageEvent
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class FatalPoison : JukeboxEffect() {

    override fun getId(): Int {
        return 25
    }

    override fun getEffectType(): EffectType {
        return EffectType.FATAL_POISON
    }

    override fun getEffectColor(): Color {
        return Color(78, 147, 49)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {
        if (this.canExecute()) {
            if (entityLiving.getHealth() > 2) {
                entityLiving.damage(EntityDamageEvent(entityLiving, 1F, EntityDamageEvent.DamageSource.MAGIC_EFFECT))
            }
        }
    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }

    override fun canExecute(): Boolean {
        val interval: Int
        if ((25 shr this.getAmplifier()).also { interval = it } > 0) {
            return (this.getDuration() % interval) == 0
        }
        return false
    }
}