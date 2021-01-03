package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement103;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement103 extends Item {

    public ItemElement103() {
        super( "minecraft:element_103", -114 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement103();
    }
}
