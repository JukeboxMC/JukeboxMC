package org.jukeboxmc.block;

import org.jukeboxmc.math.AxisAlignedBB;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class BlockSlab extends BlockWaterlogable {

    public BlockSlab( String identifier ) {
        super( identifier );
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        if ( this.isTopSlot() ) {
            return new AxisAlignedBB(
                    this.location.getX(),
                    this.location.getY() + 0.5f,
                    this.location.getZ(),
                    this.location.getX() + 1,
                    this.location.getY() + 1,
                    this.location.getZ() + 1
            );
        } else {
            return new AxisAlignedBB(
                    this.location.getX(),
                    this.location.getY(),
                    this.location.getZ(),
                    this.location.getX() + 1,
                    this.location.getY() + 0.5f,
                    this.location.getZ() + 1
            );
        }
    }

    public void setTopSlot( boolean value ) {
        this.setState( "top_slot_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isTopSlot() {
        return this.stateExists( "top_slot_bit" ) && this.getByteState( "top_slot_bit" ) == 1;
    }

}
