package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemOxidizedCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockOxidizedCopper extends Block{

    public BlockOxidizedCopper() {
        super( "minecraft:oxidized_copper" );
    }

    @Override
    public ItemOxidizedCopper toItem() {
        return new ItemOxidizedCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.OXIDIZED_COPPER;
    }
}
