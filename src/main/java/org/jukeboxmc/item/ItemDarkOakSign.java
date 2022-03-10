package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDarkOakStandingSign;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkOakSign extends Item implements Burnable {

    public ItemDarkOakSign() {
        super ( "minecraft:dark_oak_sign" );
    }

    @Override
    public BlockDarkOakStandingSign getBlock() {
        return new BlockDarkOakStandingSign();
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
