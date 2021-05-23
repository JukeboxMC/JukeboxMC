package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBirchStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBirchSign extends Item {

    public ItemBirchSign() {
        super( 567 );
    }

    @Override
    public BlockBirchStandingSign getBlock() {
        return new BlockBirchStandingSign();
    }
}
