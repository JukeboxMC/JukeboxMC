package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemAcaciaDoor;
import org.jukeboxmc.item.ItemType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAcaciaDoor extends BlockDoor {

    public BlockAcaciaDoor() {
        super( "minecraft:acacia_door" );
    }

    @Override
    public BlockAcaciaDoor newDoor() {
        return new BlockAcaciaDoor();
    }

    @Override
    public ItemAcaciaDoor toItem() {
        return new ItemAcaciaDoor();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ACACIA_DOOR;
    }

}
