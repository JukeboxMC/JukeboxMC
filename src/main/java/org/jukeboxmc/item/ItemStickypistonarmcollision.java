package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStickypistonarmcollision;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStickypistonarmcollision extends Item {

    public ItemStickypistonarmcollision() {
        super ( "minecraft:sticky_piston_arm_collision" );
    }

    @Override
    public BlockStickypistonarmcollision getBlock() {
        return new BlockStickypistonarmcollision();
    }
}
