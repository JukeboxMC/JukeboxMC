package org.jukeboxmc.block;


import org.jukeboxmc.item.ItemSculkVein;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSculkVein extends Block {

    public BlockSculkVein() {
        super( "minecraft:sculk_vein" );
    }

    @Override
    public ItemSculkVein toItem() {
        return new ItemSculkVein();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SCULK_VEIN;
    }
}
