package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemCaveVinesHeadWithBerries;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCaveVinesHeadWithBerries extends Block{

    public BlockCaveVinesHeadWithBerries() {
        super( "minecraft:cave_vines_head_with_berries" );
    }

    @Override
    public ItemCaveVinesHeadWithBerries toItem() {
        return new ItemCaveVinesHeadWithBerries();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CAVE_VINES_HEAD_WITH_BERRIES;
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
