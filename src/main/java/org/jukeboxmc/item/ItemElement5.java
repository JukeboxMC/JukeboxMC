package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement5;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement5 extends Item {

    public ItemElement5() {
        super( "minecraft:element_5", -16 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement5();
    }
}
