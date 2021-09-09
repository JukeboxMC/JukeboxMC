package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPumpkin;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPumpkin extends Item {

    public ItemPumpkin() {
        super ( "minecraft:pumpkin" );
    }

    @Override
    public BlockPumpkin getBlock() {
        return new BlockPumpkin();
    }
}
