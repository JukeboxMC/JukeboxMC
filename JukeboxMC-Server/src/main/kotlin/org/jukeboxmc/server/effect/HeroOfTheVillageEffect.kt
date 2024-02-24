package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import java.awt.Color

class HeroOfTheVillageEffect : JukeboxEffect() {

    override fun getId(): Int {
        return 29
    }

    override fun getEffectType(): EffectType {
        return EffectType.HERO_OF_THE_VILLAGE
    }

    override fun getEffectColor(): Color {
        return Color(68, 255, 68)
    }

    override fun apply(entityLiving: JukeboxEntityLiving) {

    }

    override fun update(currentTick: Long) {

    }

    override fun remove(entityLiving: JukeboxEntityLiving) {

    }
}