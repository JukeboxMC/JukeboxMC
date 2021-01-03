package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement68;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement68 extends Item {

    public ItemElement68() {
        super( "minecraft:element_68", -79 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement68();
    }
}
