package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement86;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement86 extends Item {

    public ItemElement86() {
        super ( "minecraft:element_86" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement86();
    }
}
