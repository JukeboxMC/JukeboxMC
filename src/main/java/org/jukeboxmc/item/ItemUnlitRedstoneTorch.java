package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockUnlitRedstoneTorch;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemUnlitRedstoneTorch extends Item {

    public ItemUnlitRedstoneTorch() {
        super ( "minecraft:unlit_redstone_torch" );
    }

    @Override
    public BlockUnlitRedstoneTorch getBlock() {
        return new BlockUnlitRedstoneTorch();
    }
}
