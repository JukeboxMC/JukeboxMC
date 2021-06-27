package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSporeBlossom;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSporeBlossom extends Item{

    public ItemSporeBlossom() {
        super( "minecraft:spore_blossom" );
    }

    @Override
    public BlockSporeBlossom getBlock() {
        return new BlockSporeBlossom();
    }
}
