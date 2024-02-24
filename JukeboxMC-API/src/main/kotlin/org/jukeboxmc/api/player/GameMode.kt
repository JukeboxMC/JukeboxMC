package org.jukeboxmc.api.player

/**
 * An enum class representing the player's game type
 */
enum class GameMode(val identifier: String) {

    SURVIVAL("Survival"),
    CREATIVE("Creative"),
    ADVENTURE("Adventure"),
    SPECTATOR("Spectator");

    companion object {
        fun fromId(id: Int) : GameMode {
            return when(id) {
                0 -> SURVIVAL
                1 -> CREATIVE
                2 -> ADVENTURE
                else -> SPECTATOR
            }
        }
    }
}