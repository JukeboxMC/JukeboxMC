package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockTripwireHook;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemTripwireHook extends Item {

    public ItemTripwireHook() {
        super( "minecraft:tripwire_hook", 131 );
    }

    @Override
    public BlockTripwireHook getBlock() {
        return new BlockTripwireHook();
    }
}
