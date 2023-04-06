package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.DripstoneThickness;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPointedDripstone extends Block {

    public BlockPointedDripstone( Identifier identifier ) {
        super( identifier );
    }

    public BlockPointedDripstone( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block upBlock = world.getBlock( placePosition ).getSide( BlockFace.UP );
        Block downBlock = world.getBlock( placePosition ).getSide( BlockFace.DOWN );
        if ( upBlock.isSolid() ) {
            this.setHanging( true );
            world.setBlock( placePosition, this );
            return true;
        } else if ( downBlock.isSolid() ) {
            this.setHanging( false );
            world.setBlock( placePosition, this );
        }
        return false;
    }

    public void setDripstoneThickness( DripstoneThickness dripstoneThickness ) {
        this.setState( "dripstone_thickness", dripstoneThickness.name().toLowerCase() );
    }

    public DripstoneThickness getDripstoneThickness() {
        return this.stateExists( "dripstone_thickness" ) ? DripstoneThickness.valueOf( this.getStringState( "dripstone_thickness" ) ) : DripstoneThickness.TIP;
    }

    public void setHanging( boolean value ) {
        this.setState( "hanging", value ? 1 : 0 );
    }

    public boolean isHanging() {
        return this.stateExists( "hanging" ) && this.getIntState( "hanging" ) == 1;
    }

}
