package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemPowderSnow;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPowderSnow extends Block{

    public BlockPowderSnow() {
        super( "minecraft:powder_snow" );
    }

    @Override
    public Item toItem() {
        return new ItemPowderSnow();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.POWDER_SNOW;
    }
}
