package org.jukeboxmc.api.world

/**
 * An enum class that represents all available difficulties of a world
 */
enum class Difficulty {

    PEACEFUL,
    EASY,
    NORMAL,
    HARD;

    companion object {
        fun fromId(id: Int): Difficulty {
            return when (id) {
                0 -> PEACEFUL
                1 -> EASY
                2 -> NORMAL
                else -> HARD
            }
        }
    }
}