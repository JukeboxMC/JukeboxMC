package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class InvisibilityEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 14
    }

    override fun getEffectType(): EffectType {
        return EffectType.INVISIBILITY
    }

    override fun getEffectColor(): Color {
        return Color(127, 131, 146)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}