package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement30;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement30 extends Item {

    public ItemElement30() {
        super( "minecraft:element_30", -41 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement30();
    }
}
