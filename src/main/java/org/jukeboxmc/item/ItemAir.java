package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAir;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAir extends Item {

    public ItemAir() {
        super( "minecraft:air", -158 );
    }

    @Override
    public ItemType getItemType() {
        return ItemType.AIR;
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }

    @Override
    public Block getBlock() {
        return new BlockAir();
    }
}
