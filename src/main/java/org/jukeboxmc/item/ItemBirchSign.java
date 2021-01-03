package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBirchStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBirchSign extends Item {

    public ItemBirchSign() {
        super( "minecraft:birch_sign", 567 );
    }

    @Override
    public Block getBlock() {
        return new BlockBirchStandingSign();
    }
}
