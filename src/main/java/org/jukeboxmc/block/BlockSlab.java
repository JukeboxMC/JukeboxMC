package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSlab extends Block {

    public BlockSlab( String identifier ) {
        super( identifier );
        this.setTopSlot( false );
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        if ( ( clickedPosition.getY() > 0.5 && blockFace != BlockFace.UP ) || blockFace == BlockFace.DOWN ) {
            this.setTopSlot( true );
        } else {
            this.setTopSlot( false );
        }
    }

    @Override
    public boolean canBeReplaced() {
        return true;
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
