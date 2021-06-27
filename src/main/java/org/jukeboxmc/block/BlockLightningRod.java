package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemLightningRod;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLightningRod extends Block {

    public BlockLightningRod() {
        super( "minecraft:lightning_rod" );
    }

    @Override
    public ItemLightningRod toItem() {
        return new ItemLightningRod();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LIGHTNING_ROD;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
