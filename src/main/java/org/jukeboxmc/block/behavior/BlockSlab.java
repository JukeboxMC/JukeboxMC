package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSlab extends Block {

    public BlockSlab( Identifier identifier ) {
        super( identifier );
    }

    public BlockSlab( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
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

    public BlockSlab setTopSlot( boolean value ) {
        this.setState( "top_slot_bit", value ? (byte) 1 : (byte) 0 );
        return this;
    }

    public boolean isTopSlot() {
        return this.stateExists( "top_slot_bit" ) && this.getByteState( "top_slot_bit" ) == 1;
    }
}
