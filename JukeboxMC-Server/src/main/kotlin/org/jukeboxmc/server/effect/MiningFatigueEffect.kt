package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class MiningFatigueEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 4
    }

    override fun getEffectType(): EffectType {
        return EffectType.MINING_FATIGUE
    }

    override fun getEffectColor(): Color {
        return Color(74, 66, 23)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}