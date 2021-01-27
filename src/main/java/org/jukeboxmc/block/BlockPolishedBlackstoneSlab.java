package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

public class BlockPolishedBlackstoneSlab extends BlockSlab {

    public BlockPolishedBlackstoneSlab() {
        super("minecraft:polished_blackstone_slab");
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
    }
}