package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement39;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement39 extends Item {

    public ItemElement39() {
        super ( "minecraft:element_39" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement39();
    }
}
