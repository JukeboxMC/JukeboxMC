package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement35;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement35 extends Item {

    public ItemElement35() {
        super( "minecraft:element_35", -46 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement35();
    }
}
