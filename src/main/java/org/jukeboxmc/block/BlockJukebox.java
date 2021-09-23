package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemJukebox;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockJukebox extends Block {

    public BlockJukebox() {
        super( "minecraft:jukebox" );
    }

    @Override
    public ItemJukebox toItem() {
        return new ItemJukebox();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.JUKEBOX;
    }

    @Override
    public double getHardness() {
        return 1;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

}
