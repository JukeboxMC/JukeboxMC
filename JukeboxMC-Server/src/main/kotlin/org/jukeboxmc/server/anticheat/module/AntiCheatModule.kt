package org.jukeboxmc.server.anticheat.module

import org.jukeboxmc.server.anticheat.AntiCheatHelper
import java.util.*
import kotlin.collections.HashMap

/**
 * @author Kaooot
 * @version 1.0
 */
interface AntiCheatModule {

    fun getHelper() = AntiCheatHelper.INSTANCE

    fun registerTimestamp(map: HashMap<UUID, Long>, uuid: UUID) {
        map[uuid] = System.currentTimeMillis()
    }
}