package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCoralFan;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoralFan extends Item {

    public ItemCoralFan() {
        super( "minecraft:coral_fan", -133 );
    }

    @Override
    public Block getBlock() {
        return new BlockCoralFan();
    }
}
