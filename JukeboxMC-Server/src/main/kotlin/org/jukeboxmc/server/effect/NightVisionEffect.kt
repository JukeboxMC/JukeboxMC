package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class NightVisionEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 16
    }

    override fun getEffectType(): EffectType {
        return EffectType.NIGHT_VISION
    }

    override fun getEffectColor(): Color {
        return Color(0, 0, 139)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}