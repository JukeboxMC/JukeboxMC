package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDriedKelpBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDriedKelpBlock extends Item {

    public ItemDriedKelpBlock() {
        super( "minecraft:dried_kelp_block", -139 );
    }

    @Override
    public BlockDriedKelpBlock getBlock() {
        return new BlockDriedKelpBlock();
    }
}
