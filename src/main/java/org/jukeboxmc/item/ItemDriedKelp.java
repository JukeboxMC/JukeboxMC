package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDriedKelpBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDriedKelp extends Item {

    public ItemDriedKelp() {
        super( "minecraft:dried_kelp", 270 );
    }

    @Override
    public Block getBlock() {
        return new BlockDriedKelpBlock();
    }
}
