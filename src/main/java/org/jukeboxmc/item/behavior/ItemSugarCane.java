package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSugarCane extends Item {

    public ItemSugarCane( Identifier identifier ) {
        super( identifier );
    }

    public ItemSugarCane( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public @NotNull Block toBlock() {
        return Block.create( BlockType.SUGAR_CANE );
    }
}
