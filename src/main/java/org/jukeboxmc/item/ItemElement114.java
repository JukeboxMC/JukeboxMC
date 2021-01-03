package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement114;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement114 extends Item {

    public ItemElement114() {
        super( "minecraft:element_114", -125 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement114();
    }
}
