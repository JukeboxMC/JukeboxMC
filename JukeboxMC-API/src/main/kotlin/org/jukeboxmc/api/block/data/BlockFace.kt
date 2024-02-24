package org.jukeboxmc.api.block.data

import org.jukeboxmc.api.math.Vector

enum class BlockFace(
    private val horizontalIndex: Int,
    private val offset: Vector
) {

    DOWN(-1, Vector(0, -1, 0)),
    UP(-1, Vector(0, 1, 0)),
    NORTH(2, Vector(0, 0, -1)),
    SOUTH(0, Vector(0, 0, 1)),
    WEST(1, Vector(-1, 0, 0)),
    EAST(3, Vector(1, 0, 0));

    fun getHorizontalIndex(): Int {
        return horizontalIndex
    }

    fun getOffset(): Vector {
        return offset
    }

    fun opposite(): BlockFace {
        return when (this) {
            DOWN -> UP
            UP -> DOWN
            NORTH -> SOUTH
            WEST -> EAST
            EAST -> WEST
            else -> NORTH
        }
    }

    fun getRightDirection(): BlockFace {
        return when (this) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            else -> NORTH
        }
    }

    fun getLeftDirection(): BlockFace {
        return when (this) {
            NORTH -> WEST
            EAST -> NORTH
            SOUTH -> EAST
            else -> SOUTH
        }
    }

    fun isHorizontal(): Boolean {
        return this == NORTH || this == EAST || this == SOUTH || this == WEST
    }

    fun toAxis(): Axis {
        return when(this) {
            UP, DOWN -> Axis.Y
            NORTH, SOUTH -> Axis.Z
            else -> Axis.X
        }
    }

    fun toDirection(): Direction {
        return Direction.valueOf(this.name)
    }

    fun torchFacing(): TorchFacing {
        return when (this) {
            UP -> TorchFacing.TOP
            WEST -> TorchFacing.WEST
            EAST -> TorchFacing.EAST
            NORTH -> TorchFacing.NORTH
            SOUTH -> TorchFacing.SOUTH
            else -> TorchFacing.UNKNOWN
        }
    }

    companion object {
        fun fromAngle(value: Float): BlockFace {
            var angle = value - 90F
            angle %= 360F
            if (angle < 0) {
                angle += 360.0F
            }
            return when {
                (0 <= angle && angle < 45) || (315 <= angle && angle < 360) -> NORTH
                45 <= angle && angle < 135 -> EAST
                135 <= angle && angle < 225 -> SOUTH
                else -> WEST
            }
        }

        fun fromId(value: Int): BlockFace? {
            return when (value) {
                0 -> DOWN
                1 -> UP
                2 -> NORTH
                3 -> SOUTH
                4 -> WEST
                5 -> EAST
                else -> null
            }
        }

        fun getHorizontal(): Array<BlockFace> {
            return arrayOf(NORTH, EAST, SOUTH, WEST)
        }
    }
}