package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRespawnAnchor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRespawnAnchor extends Item {

    public ItemRespawnAnchor() {
        super ( "minecraft:respawn_anchor" );
    }

    @Override
    public BlockRespawnAnchor getBlock() {
        return new BlockRespawnAnchor();
    }
}
