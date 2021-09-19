package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSeaLantern;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSealantern extends Item {

    public ItemSealantern() {
        super ( "minecraft:sealantern" );
    }

    @Override
    public BlockSeaLantern getBlock() {
        return new BlockSeaLantern();
    }
}
