package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemPolishedBlackstoneBrickWall;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

public class BlockPolishedBlackstoneBrickWall extends BlockWall {

    public BlockPolishedBlackstoneBrickWall() {
        super("minecraft:polished_blackstone_brick_wall");
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.updateWall();
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemPolishedBlackstoneBrickWall toItem() {
        return new ItemPolishedBlackstoneBrickWall();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.POLISHED_BLACKSTONE_BRICK_WALL;
    }

}