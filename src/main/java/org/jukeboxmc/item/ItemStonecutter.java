package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStonecutter;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStonecutter extends Item {

    public ItemStonecutter() {
        super ( "minecraft:stonecutter" );
    }

    @Override
    public BlockStonecutter getBlock() {
        return new BlockStonecutter();
    }
}
