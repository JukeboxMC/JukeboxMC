package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockOxidizedCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemOxidizedCopper extends Item{

    public ItemOxidizedCopper() {
        super( "minecraft:oxidized_copper" );
    }

    @Override
    public BlockOxidizedCopper getBlock() {
        return new BlockOxidizedCopper();
    }
}
