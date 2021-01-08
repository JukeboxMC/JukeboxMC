package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockTwistingVines;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemTwistingVines extends Item {

    public ItemTwistingVines() {
        super( "minecraft:twisting_vines", -287 );
    }

    @Override
    public BlockTwistingVines getBlock() {
        return new BlockTwistingVines();
    }
}
