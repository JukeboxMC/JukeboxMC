package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemDeepslateBrickSlab;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateBrickDoubleSlab extends BlockSlab {

    public BlockDeepslateBrickDoubleSlab() {
        super( "minecraft:deepslate_brick_double_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemDeepslateBrickSlab toItem() {
        return new ItemDeepslateBrickSlab();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE_BRICK_SLAB;
    }
}
