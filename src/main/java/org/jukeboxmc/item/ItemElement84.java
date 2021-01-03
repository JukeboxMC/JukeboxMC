package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement84;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement84 extends Item {

    public ItemElement84() {
        super( "minecraft:element_84", -95 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement84();
    }
}
