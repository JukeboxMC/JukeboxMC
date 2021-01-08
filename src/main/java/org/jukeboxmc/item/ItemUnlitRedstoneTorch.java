package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockUnlitRedstoneTorch;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemUnlitRedstoneTorch extends Item {

    public ItemUnlitRedstoneTorch() {
        super( "minecraft:unlit_redstone_torch", 75 );
    }

    @Override
    public BlockUnlitRedstoneTorch getBlock() {
        return new BlockUnlitRedstoneTorch();
    }
}
