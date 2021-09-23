package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemShroomlight;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockShroomlight extends Block {

    public BlockShroomlight() {
        super( "minecraft:shroomlight" );
    }

    @Override
    public ItemShroomlight toItem() {
        return new ItemShroomlight();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SHROOMLIGHT;
    }

    @Override
    public double getHardness() {
        return 1;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.HOE;
    }
}
