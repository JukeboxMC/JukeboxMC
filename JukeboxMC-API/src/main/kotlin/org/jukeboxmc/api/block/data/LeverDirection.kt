package org.jukeboxmc.api.block.data

enum class LeverDirection {

    DOWN_EAST_WEST,
    EAST,
    WEST,
    SOUTH,
    NORTH,
    UP_NORTH_SOUTH,
    UP_EAST_WEST,
    DOWN_NORTH_SOUTH;

    companion object {
        fun forDirection(blockFace: BlockFace, playerDirection: Direction): LeverDirection {
            return when (blockFace) {
                BlockFace.DOWN -> when (playerDirection) {
                    Direction.WEST, Direction.EAST -> DOWN_EAST_WEST
                    Direction.NORTH, Direction.SOUTH -> DOWN_NORTH_SOUTH
                }
                BlockFace.UP -> when (playerDirection) {
                    Direction.WEST, Direction.EAST -> UP_EAST_WEST
                    Direction.NORTH, Direction.SOUTH -> UP_NORTH_SOUTH
                }
                BlockFace.NORTH -> NORTH
                BlockFace.SOUTH -> SOUTH
                BlockFace.WEST -> WEST
                BlockFace.EAST -> EAST
            }
        }
    }

}