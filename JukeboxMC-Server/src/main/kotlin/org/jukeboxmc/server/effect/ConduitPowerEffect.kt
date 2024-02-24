package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class ConduitPowerEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 26
    }

    override fun getEffectType(): EffectType {
        return EffectType.CONDUIT_POWER
    }

    override fun getEffectColor(): Color {
        return Color(29, 194, 209)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}