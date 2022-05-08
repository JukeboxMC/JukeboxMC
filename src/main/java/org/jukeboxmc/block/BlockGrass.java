package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemGrass;
import org.jukeboxmc.item.type.ItemToolType;

import java.util.Collections;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGrass extends Block {

    public BlockGrass() {
        super( "minecraft:grass" );
    }

    @Override
    public ItemGrass toItem() {
        return new ItemGrass();
    }

    @Override
    public BlockType getType() {
        return BlockType.GRASS;
    }

    @Override
    public boolean canPassThrough() {
        return false;
    }

    @Override
    public double getHardness() {
        return 0.6;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.SHOVEL;
    }

    @Override
    public List<Item> getDrops( Item itemInHand ) {
        return Collections.singletonList( BlockType.DIRT.toItem() );
    }
}
