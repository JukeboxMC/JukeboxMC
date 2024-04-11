package org.jukeboxmc.server.anticheat

import org.jukeboxmc.api.config.Config
import org.jukeboxmc.api.config.ConfigType
import java.io.File

/**
 * @author Kaooot
 * @version 1.0
 */
class AntiCheatConfigDataProvider {

    private val config: Config = Config(File("./anticheat.json"), ConfigType.JSON)

    init {
        this.config.addDefault("enable-combat-detection", true)
        this.config.addDefault("max-combat-reach", 7.0f)
        this.config.addDefault("enable-block-breaking-detection", true)
        this.config.addDefault("max-block-breaking-reach", 7.0f)
        this.config.addDefault("notify-permission", "jukeboxmc.anticheat.notify")
        this.config.save()
    }

    fun isCombatDetectionEnabled() = this.config.getBoolean("enable-combat-detection")

    fun getMaxCombatReach() = this.config.getFloat("max-combat-reach")

    fun isBlockBreakingDetectionEnabled() = this.config.getBoolean("enable-block-breaking-detection")

    fun getMaxBlockBreakingReach() = this.config.getFloat("max-block-breaking-reach")

    fun getNotifyPermission() = this.config.getString("notify-permission")
}