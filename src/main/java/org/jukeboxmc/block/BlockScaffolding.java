package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemScaffolding;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockScaffolding extends BlockWaterlogable {

    public BlockScaffolding() {
        super( "minecraft:scaffolding" );
    }

    @Override
    public ItemScaffolding toItem() {
        return new ItemScaffolding();
    }

    @Override
    public BlockType getType() {
        return BlockType.SCAFFOLDING;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public double getHardness() {
        return 0.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
    }

    public void setStability( int value ) { //0-7
        this.setState( "stability", value );
    }

    public int getStability() {
        return this.stateExists( "stability" ) ? this.getIntState( "stability" ) : 0;
    }

    public void setStabilityCheck( boolean value ) {
        this.setState( "stability_check", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isStabilityCheck() {
        return this.stateExists( "stability_check" ) && this.getByteState( "stability_check" ) == 1;
    }
}
