package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSkull;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSkullBlock extends Item {

    public ItemSkullBlock() {
        super( "minecraft:item.skull", 144 );
    }

    @Override
    public BlockSkull getBlock() {
        return new BlockSkull();
    }
}
