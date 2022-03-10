package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSpruceStandingSign;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSpruceSign extends Item implements Burnable {

    public ItemSpruceSign() {
        super ( "minecraft:spruce_sign" );
    }

    @Override
    public BlockSpruceStandingSign getBlock() {
        return new BlockSpruceStandingSign();
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
