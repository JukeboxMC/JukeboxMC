package org.jukeboxmc.api.world

import org.jukeboxmc.api.JukeboxMC
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.api.world.gamerule.GameRule
import org.jukeboxmc.api.world.gamerule.GameRuleValue

class WorldData(
    var gameRuleData: List<GameRuleValue> = GameRule.entries.map { GameRuleValue(it.identifier, it.defaultValue) }.toMutableList(),
    var biomeOverride: String = "",
    var centerMapsToOrigin: Boolean = false,
    var confirmedPlatformLockedContent: Boolean = false,
    var difficulty: Difficulty = Difficulty.NORMAL,
    var flatWorldLayers: String = "",
    var forceGameType: Boolean = false,
    var gameType: GameMode = JukeboxMC.getServer().getDefaultGameMode(),
    var generator: Int = 1,
    var inventoryVersion: String = JukeboxMC.getServer().getMinecraftVersion(),
    var lANBroadcast: Boolean = true,
    var lANBroadcastIntent: Boolean = true,
    var lastPlayed: Long = 0,
    var levelName: String = "world",
    var limitedWorldOriginX: Int = 0,
    var limitedWorldOriginY: Int = 0,
    var limitedWorldOriginZ: Int = 0,
    var minimumCompatibleClientVersion: IntArray = JukeboxMC.getServer().getMinecraftVersion().split(".").map { it.toInt() }
        .toIntArray(),
    var multiplayerGame: Boolean = true,
    var multiplayerGameIntent: Boolean = false,
    var netherScale: Int = 8,
    var networkVersion: Int = 618,
    var platform: Int = 2,
    var platformBroadcastIntent: Int = 0,
    var randomSeed: Long = Math.random().toLong(),
    var spawnV1Villagers: Boolean = false,
    var spawnX: Int = 0,
    var spawnY: Int = 70, //TODO
    var spawnZ: Int = 0,
    var storageVersion: Int = 10,
    var time: Int = 0,
    var worldVersion: Int = 1,
    var XBLBroadcastIntent: Int = 0,
    var abilities: AbilityData = AbilityData(),
    var baseGameVersion: String = "*",
    var bonusChestEnabled: Boolean = false,
    var bonusChestSpawned: Boolean = false,
    var cheatsEnabled: Boolean = false,
    var commandsEnabled: Boolean = false,
    var currentTick: Long = 0,
    var daylightCycle: Int = 0,
    var editorWorldType: Int = 0,
    var eduOffer: Int = 0,
    var educationFeaturesEnabled: Boolean = false,
    var experiments: Map<String, Any> = mutableMapOf(),
    var hasBeenLoadedInCreative: Boolean = false,
    var hasLockedBehaviorPack: Boolean = false,
    var hasLockedResourcePack: Boolean = false,
    var immutableWorld: Boolean = false,
    var isCreatedInEditor: Boolean = false,
    var isExportedFromEditor: Boolean = false,
    var isFromLockedTemplate: Boolean = false,
    var isFromWorldTemplate: Boolean = false,
    var isRandomSeedAllowed: Boolean = false,
    var isSingleUseWorld: Boolean = false,
    var isWorldTemplateOptionLocked: Boolean = false,
    var lastOpenedWithVersion: IntArray = JukeboxMC.getServer().getMinecraftVersion().split(".").map { it.toInt() }.toIntArray(),
    var lightningLevel: Float = 0.0f,
    var lightningTime: Int = 0,
    var limitedWorldDepth: Int = 16,
    var limitedWorldWidth: Int = 16,
    var permissionsLevel: Int = 0,
    var playerPermissionsLevel: Int = 1,
    var prid: String = "",
    var rainLevel: Float = 0.0f,
    var rainTime: Int = 0,
    var requiresCopiedPackRemovalCheck: Boolean = false,
    var serverChunkTickRange: Int = 4,
    var spawnMobs: Boolean = true,
    var startWithMapEnabled: Boolean = false,
    var texturePacksRequired: Boolean = false,
    var useMsaGamertagsOnly: Boolean = true,
    var worldStartCount: Long = System.currentTimeMillis(),
    var worldPolicies: MutableList<Any> = mutableListOf(),
) {



    override fun toString(): String {
        return "WorldData(gameRuleData=$gameRuleData, biomeOverride=$biomeOverride, centerMapsToOrigin=$centerMapsToOrigin, confirmedPlatformLockedContent=$confirmedPlatformLockedContent, difficulty=$difficulty, flatWorldLayers=$flatWorldLayers, forceGameType=$forceGameType, gameType=$gameType, generator=$generator, inventoryVersion=$inventoryVersion, lANBroadcast=$lANBroadcast, lANBroadcastIntent=$lANBroadcastIntent, lastPlayed=$lastPlayed, levelName=$levelName, limitedWorldOriginX=$limitedWorldOriginX, limitedWorldOriginY=$limitedWorldOriginY, limitedWorldOriginZ=$limitedWorldOriginZ, minimumCompatibleClientVersion=${minimumCompatibleClientVersion.contentToString()}, multiplayerGame=$multiplayerGame, multiplayerGameIntent=$multiplayerGameIntent, netherScale=$netherScale, networkVersion=$networkVersion, platform=$platform, platformBroadcastIntent=$platformBroadcastIntent, randomSeed=$randomSeed, spawnV1Villagers=$spawnV1Villagers, spawnX=$spawnX, spawnY=$spawnY, spawnZ=$spawnZ, storageVersion=$storageVersion, time=$time, worldVersion=$worldVersion, XBLBroadcastIntent=$XBLBroadcastIntent, abilities=$abilities, baseGameVersion=$baseGameVersion, bonusChestEnabled=$bonusChestEnabled, bonusChestSpawned=$bonusChestSpawned, cheatsEnabled=$cheatsEnabled, commandsEnabled=$commandsEnabled, currentTick=$currentTick, daylightCycle=$daylightCycle, editorWorldType=$editorWorldType, eduOffer=$eduOffer, educationFeaturesEnabled=$educationFeaturesEnabled, experiments=$experiments, hasBeenLoadedInCreative=$hasBeenLoadedInCreative, hasLockedBehaviorPack=$hasLockedBehaviorPack, hasLockedResourcePack=$hasLockedResourcePack, immutableWorld=$immutableWorld, isCreatedInEditor=$isCreatedInEditor, isExportedFromEditor=$isExportedFromEditor, isFromLockedTemplate=$isFromLockedTemplate, isFromWorldTemplate=$isFromWorldTemplate, isRandomSeedAllowed=$isRandomSeedAllowed, isSingleUseWorld=$isSingleUseWorld, isWorldTemplateOptionLocked=$isWorldTemplateOptionLocked, lastOpenedWithVersion=${lastOpenedWithVersion.contentToString()}, lightningLevel=$lightningLevel, lightningTime=$lightningTime, limitedWorldDepth=$limitedWorldDepth, limitedWorldWidth=$limitedWorldWidth, permissionsLevel=$permissionsLevel, playerPermissionsLevel=$playerPermissionsLevel, prid=$prid, rainLevel=$rainLevel, rainTime=$rainTime, requiresCopiedPackRemovalCheck=$requiresCopiedPackRemovalCheck, serverChunkTickRange=$serverChunkTickRange, spawnMobs=$spawnMobs, startWithMapEnabled=$startWithMapEnabled, texturePacksRequired=$texturePacksRequired, useMsaGamertagsOnly=$useMsaGamertagsOnly, worldStartCount=$worldStartCount, worldPolicies=$worldPolicies)"
    }
}