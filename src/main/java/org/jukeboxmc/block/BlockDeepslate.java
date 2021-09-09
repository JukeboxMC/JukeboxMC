package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDeepslate;
import org.jukeboxmc.math.Axis;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslate extends Block {

    public BlockDeepslate() {
        super( "minecraft:deepslate" );
    }

    @Override
    public ItemDeepslate toItem() {
        return new ItemDeepslate();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE;
    }

    public void setAxis( Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ) ) : Axis.Y;
    }
}
