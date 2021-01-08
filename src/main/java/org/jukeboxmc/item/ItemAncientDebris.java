package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAncientDebris;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAncientDebris extends Item {

    public ItemAncientDebris() {
        super( "minecraft:ancient_debris", -271 );
    }

    @Override
    public BlockAncientDebris getBlock() {
        return new BlockAncientDebris();
    }
}
