package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemCaveVines;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCaveVines extends Block{

    public BlockCaveVines() {
        super( "minecraft:cave_vines" );
    }

    @Override
    public ItemCaveVines toItem() {
        return new ItemCaveVines();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CAVE_VINES;
    }
}
