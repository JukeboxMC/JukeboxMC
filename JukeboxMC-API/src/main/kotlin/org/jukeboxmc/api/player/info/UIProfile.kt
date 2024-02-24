package org.jukeboxmc.api.player.info

/**
 * The ui profile that can be defined in the client's game settings
 */
enum class UIProfile {

    CLASSIC,
    POCKET;

    companion object {
        fun getById(id: Int): UIProfile? {
            return when (id) {
                0 -> CLASSIC
                1 -> POCKET
                else -> null
            }
        }
    }
}