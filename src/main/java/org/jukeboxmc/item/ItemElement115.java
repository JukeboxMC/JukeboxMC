package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement115;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement115 extends Item {

    public ItemElement115() {
        super( "minecraft:element_115", -126 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement115();
    }
}
