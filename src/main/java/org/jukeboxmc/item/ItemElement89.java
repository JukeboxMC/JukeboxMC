package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement89;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement89 extends Item {

    public ItemElement89() {
        super( "minecraft:element_89", -100 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement89();
    }
}
