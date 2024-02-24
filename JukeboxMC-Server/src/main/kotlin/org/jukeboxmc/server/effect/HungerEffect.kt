package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class HungerEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 17
    }

    override fun getEffectType(): EffectType {
        return EffectType.HUNGER
    }

    override fun getEffectColor(): Color {
        return Color(46, 139, 87)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}