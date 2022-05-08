package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDriedKelp;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDriedKelpBlock extends Block {

    public BlockDriedKelpBlock() {
        super( "minecraft:dried_kelp_block" );
    }

    @Override
    public ItemDriedKelp toItem() {
        return new ItemDriedKelp();
    }

    @Override
    public BlockType getType() {
        return BlockType.DRIED_KELP_BLOCK;
    }

    @Override
    public double getHardness() {
        return 0.5;
    }

}
