package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCutCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCutCopper extends Item{

    public ItemCutCopper() {
        super( "minecraft:cut_copper" );
    }

    @Override
    public BlockCutCopper getBlock() {
        return new BlockCutCopper();
    }
}
