package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStandingSign;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemOakSign extends Item implements Burnable {

    public ItemOakSign() {
        super ( "minecraft:oak_sign" );
    }

    @Override
    public BlockStandingSign getBlock() {
        return new BlockStandingSign();
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
