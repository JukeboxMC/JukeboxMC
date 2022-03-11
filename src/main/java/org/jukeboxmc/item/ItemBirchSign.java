package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBirchStandingSign;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBirchSign extends Item implements Burnable {

    public ItemBirchSign() {
        super ( "minecraft:birch_sign" );
    }

    @Override
    public BlockBirchStandingSign getBlock() {
        return new BlockBirchStandingSign();
    }

    @Override
    public int getMaxAmount() {
        return 16;
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 200 );
    }
}
