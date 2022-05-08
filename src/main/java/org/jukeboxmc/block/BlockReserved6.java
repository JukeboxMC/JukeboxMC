package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemReserved6;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockReserved6 extends Block {

    public BlockReserved6() {
        super( "minecraft:reserved6" );
    }

    @Override
    public ItemReserved6 toItem() {
        return new ItemReserved6();
    }

    @Override
    public BlockType getType() {
        return BlockType.RESERVED6;
    }

}
