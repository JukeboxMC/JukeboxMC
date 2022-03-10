package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockScaffolding;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemScaffolding extends Item implements Burnable {

    public ItemScaffolding() {
        super ( "minecraft:scaffolding" );
    }

    @Override
    public BlockScaffolding getBlock() {
        return new BlockScaffolding();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 400 );
    }
}
