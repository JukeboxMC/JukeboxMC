package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemExposedCutCopperSlab;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockExposedDoubleCutCopperSlab extends BlockSlab {

    public BlockExposedDoubleCutCopperSlab() {
        super( "minecraft:exposed_double_cut_copper_slab" );
    }
    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemExposedCutCopperSlab toItem() {
        return new ItemExposedCutCopperSlab();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.EXPOSED_CUT_COPPER_SLAB;
    }
}
