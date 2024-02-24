package org.jukeboxmc.api.world

/**
 * Player abilities that are also displayed in the client and control the game variety for a player
 */
class AbilityData(
    var attackmobs: Boolean = true,
    var attackPlayers: Boolean = true,
    var build: Boolean= true,
    var doorsAndSwitches: Boolean = true,
    var flySpeed: Float = 0.05f,
    var flying: Boolean = false,
    var instaBuild: Boolean = false,
    var invulnerable: Boolean = false,
    var lightning: Boolean = false,
    var mayFly: Boolean = false,
    var mine: Boolean = true,
    var op: Boolean = false,
    var openContainers: Boolean = true,
    var teleport: Boolean = false,
    var walkSpeed: Float = 0.1f,
)