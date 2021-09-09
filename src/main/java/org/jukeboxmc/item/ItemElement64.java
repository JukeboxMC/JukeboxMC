package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement64;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement64 extends Item {

    public ItemElement64() {
        super ( "minecraft:element_64" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement64();
    }
}
