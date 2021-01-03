package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement32;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement32 extends Item {

    public ItemElement32() {
        super( "minecraft:element_32", -43 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement32();
    }
}
