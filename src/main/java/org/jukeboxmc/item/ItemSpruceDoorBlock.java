package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSpruceDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSpruceDoorBlock extends Item {

    public ItemSpruceDoorBlock() {
        super ( "minecraft:item.spruce_door" );
    }

    @Override
    public BlockSpruceDoor getBlock() {
        return new BlockSpruceDoor();
    }
}
