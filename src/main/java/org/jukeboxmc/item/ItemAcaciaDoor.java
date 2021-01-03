package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAcaciaDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaDoor extends Item {

    public ItemAcaciaDoor() {
        super( "minecraft:acacia_door", 546 );
    }

    @Override
    public Block getBlock() {
        return new BlockAcaciaDoor();
    }
}
