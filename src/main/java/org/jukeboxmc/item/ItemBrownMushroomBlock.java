package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBrownMushroomBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBrownMushroomBlock extends Item {

    public ItemBrownMushroomBlock() {
        super( "minecraft:brown_mushroom_block", 99 );
    }

    @Override
    public Block getBlock() {
        return new BlockBrownMushroomBlock();
    }
}
