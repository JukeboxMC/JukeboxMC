package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockRedWool;
import org.jukeboxmc.item.Burnable;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedWool extends Item implements Burnable {

    private final BlockRedWool block;

    public ItemRedWool(Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.RED_WOOL );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemRedWool(ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.RED_WOOL );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 100 );
    }
}
