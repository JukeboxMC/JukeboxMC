package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement15;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement15 extends Item {

    public ItemElement15() {
        super( "minecraft:element_15", -26 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement15();
    }
}
