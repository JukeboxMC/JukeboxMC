package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement77;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement77 extends Item {

    public ItemElement77() {
        super( -88 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement77();
    }
}
