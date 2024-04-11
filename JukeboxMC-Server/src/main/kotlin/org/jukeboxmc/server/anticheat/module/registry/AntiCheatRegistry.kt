package org.jukeboxmc.server.anticheat.module.registry

import org.jukeboxmc.server.anticheat.module.AntiCheatModule
import org.jukeboxmc.server.anticheat.module.combat.AntiCheatBlockBreakingModule
import org.jukeboxmc.server.anticheat.module.combat.AntiCheatCombatModule
import org.jukeboxmc.server.anticheat.module.combat.AntiCheatMovementModule

/**
 * @author Kaooot
 * @version 1.0
 */
class AntiCheatRegistry {

    private val modules = hashMapOf<Class<out AntiCheatModule>, AntiCheatModule>()

    init {
        this.modules[AntiCheatBlockBreakingModule::class.java] = AntiCheatBlockBreakingModule()
        this.modules[AntiCheatCombatModule::class.java] = AntiCheatCombatModule()
        this.modules[AntiCheatMovementModule::class.java] = AntiCheatMovementModule()
    }

    fun <T : AntiCheatModule> getModule(clazz: Class<T>): T = this.modules[clazz] as T
}