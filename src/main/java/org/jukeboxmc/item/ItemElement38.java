package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement38;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement38 extends Item {

    public ItemElement38() {
        super( -49 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement38();
    }
}
