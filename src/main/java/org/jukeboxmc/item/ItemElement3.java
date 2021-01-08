package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement3;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement3 extends Item {

    public ItemElement3() {
        super( "minecraft:element_3", -14 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement3();
    }
}
