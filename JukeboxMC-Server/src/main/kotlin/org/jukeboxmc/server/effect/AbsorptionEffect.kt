package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class AbsorptionEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 22
    }

    override fun getEffectType(): EffectType {
        return EffectType.ABSORPTION
    }

    override fun getEffectColor(): Color {
        return Color(36, 107, 251)
    }

    override fun update(currentTick: Long) {

    }
    
    override fun apply(entityLiving: JukeboxEntityLiving) {
        val value = (this.getAmplifier() + 1) * 4
        if (value > entityLiving.getAbsorption()) {
            entityLiving.setAbsorption(value.toFloat())
        }
    }

    override fun remove(entityLiving: JukeboxEntityLiving) {
        entityLiving.setAbsorption(0F)
    }

}