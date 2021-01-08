package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBeehive;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBeehive extends Item {

    public ItemBeehive() {
        super( "minecraft:beehive", -219 );
    }

    @Override
    public BlockBeehive getBlock() {
        return new BlockBeehive();
    }
}
