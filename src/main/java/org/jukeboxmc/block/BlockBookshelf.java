package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemBook;
import org.jukeboxmc.item.ItemBookshelf;
import org.jukeboxmc.item.type.ItemToolType;

import java.util.Collections;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBookshelf extends Block {

    public BlockBookshelf() {
        super( "minecraft:bookshelf" );
    }

    @Override
    public ItemBookshelf toItem() {
        return new ItemBookshelf();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BOOKSHELF;
    }

    @Override
    public double getHardness() {
        return 1.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
    }

    @Override
    public List<Item> getDrops( Item itemInHand ) {
        return Collections.singletonList( new ItemBook().setAmount( 3 ) );
    }
}
