package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

public class BlockWarpedSlab extends BlockSlab {

    public BlockWarpedSlab() {
        super("minecraft:warped_slab");
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
    }
}