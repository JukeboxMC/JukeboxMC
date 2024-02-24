package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class NauseaEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 9
    }

    override fun getEffectType(): EffectType {
        return EffectType.NAUSEA
    }

    override fun getEffectColor(): Color {
        return Color(85, 29, 74)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}