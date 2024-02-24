package org.jukeboxmc.api.block.data

enum class Direction {

    NORTH,
    EAST,
    SOUTH,
    WEST;

    fun opposite(): Direction {
        return when (this) {
            NORTH -> SOUTH
            WEST -> EAST
            EAST -> WEST
            else -> NORTH
        }
    }

    fun getRightDirection(): Direction {
        return when (this) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            else -> NORTH
        }
    }

    fun getLeftDirection(): Direction {
        return when (this) {
            NORTH -> WEST
            EAST -> NORTH
            SOUTH -> EAST
            else -> SOUTH
        }
    }

    fun toBlockFace(): BlockFace {
        return BlockFace.valueOf(this.name)
    }

    fun toCrossDirection(): WeirdoDirection {
        return when (this) {
            SOUTH -> WeirdoDirection.SOUTH
            WEST -> WeirdoDirection.WEST
            NORTH -> WeirdoDirection.NORTH
            else -> WeirdoDirection.EAST
        }
    }

    companion object {
        fun fromAngle(value: Float): Direction {
            var result = value
            result -= 90f
            result %= 360f
            if (result < 0) {
                result += 360.0.toFloat()
            }
            if (0 <= result && result < 45 || 315 <= result && result < 360) {
                return NORTH
            }
            if (45 <= result && result < 135) {
                return EAST
            }
            return if (135 <= result && result < 225) {
                SOUTH
            } else WEST
        }
    }

}