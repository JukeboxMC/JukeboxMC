package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement97;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement97 extends Item {

    public ItemElement97() {
        super ( "minecraft:element_97" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement97();
    }
}
