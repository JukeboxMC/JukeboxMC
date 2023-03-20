package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPowderSnowBucket extends ItemBucket {

    public ItemPowderSnowBucket( Identifier identifier ) {
        super( identifier );
    }

    public ItemPowderSnowBucket( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public @NotNull Block toBlock() {
        return Block.create( BlockType.POWDER_SNOW );
    }
}
