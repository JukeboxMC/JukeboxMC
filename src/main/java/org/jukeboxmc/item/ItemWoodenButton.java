package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWoodenButton;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWoodenButton extends Item implements Burnable {

    public ItemWoodenButton() {
        super ( "minecraft:wooden_button" );
    }

    @Override
    public BlockWoodenButton getBlock() {
        return new BlockWoodenButton();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
