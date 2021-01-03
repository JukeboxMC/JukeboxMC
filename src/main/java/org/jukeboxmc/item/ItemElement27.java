package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement27;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement27 extends Item {

    public ItemElement27() {
        super( "minecraft:element_27", -38 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement27();
    }
}
