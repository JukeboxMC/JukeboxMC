package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBirchStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBirchStandingSign extends Item {

    public ItemBirchStandingSign() {
        super( -186 );
    }

    @Override
    public BlockBirchStandingSign getBlock() {
        return new BlockBirchStandingSign();
    }
}
