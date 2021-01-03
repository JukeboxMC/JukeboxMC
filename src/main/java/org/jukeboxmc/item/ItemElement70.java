package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement70;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement70 extends Item {

    public ItemElement70() {
        super( "minecraft:element_70", -81 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement70();
    }
}
