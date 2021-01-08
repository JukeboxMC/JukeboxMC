package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockReserved6;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemReserved6 extends Item {

    public ItemReserved6() {
        super( "minecraft:reserved6", 255 );
    }

    @Override
    public BlockReserved6 getBlock() {
        return new BlockReserved6();
    }
}
