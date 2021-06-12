package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBirchStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBirchSign extends Item {

    public ItemBirchSign() {
        super ( "minecraft:birch_sign" );
    }

    @Override
    public BlockBirchStandingSign getBlock() {
        return new BlockBirchStandingSign();
    }
}
