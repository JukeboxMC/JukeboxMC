package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAllow;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAllow extends Item {

    public ItemAllow() {
        super( "minecraft:allow", 210 );
    }

    @Override
    public Block getBlock() {
        return new BlockAllow();
    }
}
