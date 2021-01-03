package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement57;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement57 extends Item {

    public ItemElement57() {
        super( "minecraft:element_57", -68 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement57();
    }
}
