package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement90;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement90 extends Item {

    public ItemElement90() {
        super( "minecraft:element_90", -101 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement90();
    }
}
