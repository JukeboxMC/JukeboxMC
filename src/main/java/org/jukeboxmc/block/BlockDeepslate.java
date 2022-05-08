package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDeepslate;
import org.jukeboxmc.item.type.ItemToolType;
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
    public BlockType getType() {
        return BlockType.DEEPSLATE;
    }

    @Override
    public double getHardness() {
        return 3;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }


    public void setAxis( Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ) ) : Axis.Y;
    }
}
