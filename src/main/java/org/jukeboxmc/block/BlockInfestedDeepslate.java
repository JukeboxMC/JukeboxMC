package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemInfestedDeepslate;
import org.jukeboxmc.item.ItemToolType;
import org.jukeboxmc.math.Axis;

import java.util.Collections;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockInfestedDeepslate extends Block {

    public BlockInfestedDeepslate() {
        super( "minecraft:infested_deepslate" );
    }

    @Override
    public ItemInfestedDeepslate toItem() {
        return new ItemInfestedDeepslate();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.INFESTED_DEEPSLATE;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public List<Item> getDrops( Item itemInHand ) {
        return Collections.emptyList();
    }

    public void setAxis( Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ) ) : Axis.Y;
    }
}
