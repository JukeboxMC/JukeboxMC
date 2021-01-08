package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement51;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement51 extends Item {

    public ItemElement51() {
        super( "minecraft:element_51", -62 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement51();
    }
}
