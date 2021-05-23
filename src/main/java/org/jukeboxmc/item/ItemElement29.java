package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement29;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement29 extends Item {

    public ItemElement29() {
        super( -40 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement29();
    }
}
