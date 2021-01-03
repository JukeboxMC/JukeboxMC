package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBubbleColumn;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBubbleColumn extends Item {

    public ItemBubbleColumn() {
        super( "minecraft:bubble_column", -160 );
    }

    @Override
    public Block getBlock() {
        return new BlockBubbleColumn();
    }
}
