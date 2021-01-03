package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement105;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement105 extends Item {

    public ItemElement105() {
        super( "minecraft:element_105", -116 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement105();
    }
}
