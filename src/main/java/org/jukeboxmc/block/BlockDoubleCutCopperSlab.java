package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemCutCopperSlab;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDoubleCutCopperSlab extends BlockSlab {

    public BlockDoubleCutCopperSlab() {
        super( "minecraft:double_cut_copper_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemCutCopperSlab toItem() {
        return new ItemCutCopperSlab();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CUT_COPPER_SLAB;
    }
}
