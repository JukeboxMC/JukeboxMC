package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.item.Burnable;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkOakSign extends Item implements Burnable {

    public ItemDarkOakSign( Identifier identifier ) {
        super( identifier );
    }

    public ItemDarkOakSign( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public @NotNull Block toBlock() {
        return Block.create( BlockType.DARKOAK_STANDING_SIGN );
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 200 );
    }
}
