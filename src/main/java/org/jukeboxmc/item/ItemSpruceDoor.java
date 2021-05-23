package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSpruceDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSpruceDoor extends Item {

    public ItemSpruceDoor() {
        super( 543 );
    }

    @Override
    public BlockSpruceDoor getBlock() {
        return new BlockSpruceDoor();
    }
}
