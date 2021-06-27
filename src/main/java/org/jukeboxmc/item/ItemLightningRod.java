package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLightningRod;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLightningRod extends Item{

    public ItemLightningRod() {
        super( "minecraft:lightning_rod" );
    }

    @Override
    public BlockLightningRod getBlock() {
        return new BlockLightningRod();
    }
}
