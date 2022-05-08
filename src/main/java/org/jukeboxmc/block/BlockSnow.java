package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemSnow;
import org.jukeboxmc.item.ItemSnowball;
import org.jukeboxmc.item.type.ItemToolType;

import java.util.Collections;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSnow extends Block {

    public BlockSnow() {
        super( "minecraft:snow" );
    }

    @Override
    public ItemSnow toItem() {
        return new ItemSnow();
    }

    @Override
    public BlockType getType() {
        return BlockType.SNOW;
    }

    @Override
    public double getHardness() {
        return 0.2;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.SHOVEL;
    }

    @Override
    public List<Item> getDrops( Item itemInHand ) {
        if ( itemInHand.getTierType().ordinal() >= this.getTierType().ordinal() ) {
            return Collections.singletonList( new ItemSnowball().setAmount( 4 ) );
        }
        return Collections.emptyList();
    }
}
