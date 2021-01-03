package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAnvil;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAnvil extends Item {

    public ItemAnvil() {
        super( "minecraft:anvil", 145 );
    }

    @Override
    public Block getBlock() {
        return new BlockAnvil();
    }
}
