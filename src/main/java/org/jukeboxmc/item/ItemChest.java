package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockChest;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChest extends Item {

    public ItemChest() {
        super( "minecraft:chest", 54 );
    }

    @Override
    public BlockChest getBlock() {
        return new BlockChest();
    }
}
