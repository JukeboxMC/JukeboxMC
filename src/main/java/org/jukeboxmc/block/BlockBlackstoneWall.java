package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemBlackstoneWall;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

public class BlockBlackstoneWall extends BlockWall {

    public BlockBlackstoneWall() {
        super("minecraft:blackstone_wall");
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.updateWall();
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemBlackstoneWall toItem() {
        return new ItemBlackstoneWall();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BLACKSTONE_WALL;
    }

}