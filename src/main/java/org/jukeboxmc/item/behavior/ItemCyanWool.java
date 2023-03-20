package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockCyanWool;
import org.jukeboxmc.item.Burnable;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCyanWool extends Item implements Burnable {

    private final @NotNull BlockCyanWool block;

    public ItemCyanWool(Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.CYAN_WOOL );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemCyanWool(ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.CYAN_WOOL );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 100 );
    }
}
