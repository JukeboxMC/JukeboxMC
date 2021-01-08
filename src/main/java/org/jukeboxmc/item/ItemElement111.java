package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement111;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement111 extends Item {

    public ItemElement111() {
        super( "minecraft:element_111", -122 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement111();
    }
}
