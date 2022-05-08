package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemPolishedBlackstoneWall;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

public class BlockPolishedBlackstoneWall extends BlockWall {

    public BlockPolishedBlackstoneWall() {
        super("minecraft:polished_blackstone_wall");
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.updateWall();
        return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
    }

    @Override
    public ItemPolishedBlackstoneWall toItem() {
        return new ItemPolishedBlackstoneWall();
    }

    @Override
    public BlockType getType() {
        return BlockType.POLISHED_BLACKSTONE_WALL;
    }

}