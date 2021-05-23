package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement74;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement74 extends Item {

    public ItemElement74() {
        super( -85 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement74();
    }
}
