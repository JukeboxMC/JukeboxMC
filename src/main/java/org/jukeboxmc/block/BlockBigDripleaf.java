package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemBigDripleaf;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBigDripleaf extends Block{

    public BlockBigDripleaf() {
        super( "minecraft:big_dripleaf" );
    }

    @Override
    public ItemBigDripleaf toItem() {
        return new ItemBigDripleaf();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BIG_DRIPLEAF;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
