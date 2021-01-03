package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDarkOakDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkOakDoorBlock extends Item {

    public ItemDarkOakDoorBlock() {
        super( "minecraft:item.dark_oak_door", 197 );
    }

    @Override
    public Block getBlock() {
        return new BlockDarkOakDoor();
    }
}
