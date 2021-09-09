package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBrownMushroomBlock;
import org.jukeboxmc.block.BlockType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBrownMushroomBlock extends Item {

    public ItemBrownMushroomBlock( int blockRuntimeId ) {
        super( "minecraft:brown_mushroom_block", blockRuntimeId );
    }

    @Override
    public BlockBrownMushroomBlock getBlock() {
        return (BlockBrownMushroomBlock) BlockType.getBlock( this.blockRuntimeId );
    }

}
