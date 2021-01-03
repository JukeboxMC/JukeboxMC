package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement88;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement88 extends Item {

    public ItemElement88() {
        super( "minecraft:element_88", -99 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement88();
    }
}
