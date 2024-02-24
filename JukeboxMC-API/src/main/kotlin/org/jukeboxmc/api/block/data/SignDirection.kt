package org.jukeboxmc.api.block.data

enum class SignDirection(
    private val closestBlockFace: BlockFace,
    private val yaw: Float
) {

    SOUTH(BlockFace.SOUTH, 180F),
    SOUTH_SOUTH_WEST(BlockFace.SOUTH, 202.5F),
    SOUTH_WEST(BlockFace.SOUTH, 225F),
    WEST_SOUTH_WEST(BlockFace.WEST, 247.5F),
    WEST(BlockFace.WEST, 270F),
    WEST_NORTH_WEST(BlockFace.WEST, 292.5F),
    NORTH_WEST(BlockFace.WEST, 315F),
    NORTH_NORTH_WEST(BlockFace.NORTH, 337.5F),
    NORTH(BlockFace.NORTH, 0F),
    NORTH_NORTH_EAST(BlockFace.NORTH, 22.5F),
    NORTH_EAST(BlockFace.NORTH, 45F),
    EAST_NORTH_EAST(BlockFace.EAST, 67.5F),
    EAST(BlockFace.EAST, 90F),
    EAST_SOUTH_EAST(BlockFace.EAST, 112.5F),
    SOUTH_EAST(BlockFace.EAST, 135F),
    SOUTH_SOUTH_EAST(BlockFace.SOUTH, 157.5F);

    fun getClosestBlockFace(): BlockFace {
        return this.closestBlockFace
    }

    fun getYaw(): Float {
        return this.yaw
    }

}