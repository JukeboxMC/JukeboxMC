package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockJungleStandingSign;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemJungleSign extends Item implements Burnable {

    public ItemJungleSign() {
        super ( "minecraft:jungle_sign" );
    }

    @Override
    public BlockJungleStandingSign getBlock() {
        return new BlockJungleStandingSign();
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
