package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement33;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement33 extends Item {

    public ItemElement33() {
        super( "minecraft:element_33", -44 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement33();
    }
}
