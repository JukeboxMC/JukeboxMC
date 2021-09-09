package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement100;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement100 extends Item {

    public ItemElement100() {
        super ( "minecraft:element_100" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement100();
    }
}
