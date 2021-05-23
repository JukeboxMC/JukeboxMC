package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAcaciaPressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaPressurePlate extends Item {

    public ItemAcaciaPressurePlate() {
        super( -150 );
    }

    @Override
    public BlockAcaciaPressurePlate getBlock() {
        return new BlockAcaciaPressurePlate();
    }
}
