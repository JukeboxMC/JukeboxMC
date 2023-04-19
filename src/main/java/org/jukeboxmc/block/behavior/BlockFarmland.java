package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.UpdateReason;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFarmland extends Block {

    public BlockFarmland( Identifier identifier ) {
        super( identifier );
    }

    public BlockFarmland( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public long onUpdate( UpdateReason updateReason ) {
        if ( updateReason.equals( UpdateReason.RANDOM ) ) {
            World world = this.location.getWorld();
            Block blockUp = this.getSide( BlockFace.UP );

            if ( blockUp.isSolid() ) {
                world.setBlock( this.location, Block.create( BlockType.DIRT ) );
                return -1;
            }

            boolean found = false;

            for ( int x = this.location.getBlockX() - 4; x <= this.location.getBlockX() + 4; x++ ) {
                for ( int z = this.location.getBlockZ() - 4; z <= this.location.getBlockZ() + 4; z++ ) {
                    for ( int y = this.location.getBlockY(); y <= this.location.getBlockY() + 1; y++ ) {
                        if ( z == this.location.getBlockZ() && x == this.location.getBlockX() && y == this.location.getBlockY() ) {
                            continue;
                        }
                        BlockType blockType = world.getBlock( new Vector( x, y, z ) ).getType();
                        if ( blockType.equals( BlockType.WATER ) || blockType.equals( BlockType.FLOWING_WATER ) ) {
                            found = true;
                            break;
                        }
                    }
                }
            }

            Block blockDown = this.getSide( BlockFace.DOWN );
            if ( found || blockDown.getType().equals( BlockType.WATER ) || blockDown.getType().equals( BlockType.FLOWING_WATER ) ) {
                if ( this.getMoisturizedAmount() < 7 ) {
                    this.setMoisturizedAmount( 7 );
                }
                return -1;
            }
            if ( this.getMoisturizedAmount() > 0 ) {
                this.setMoisturizedAmount( this.getMoisturizedAmount() - 1 );
            } else {
                world.setBlock( this.location, Block.create( BlockType.DIRT ) );
            }
        }
        return -1;
    }

    public void setMoisturizedAmount( int value ) {
        this.setState( "moisturized_amount", value );
    }

    public int getMoisturizedAmount() {
        return this.stateExists( "moisturized_amount" ) ? this.getIntState( "moisturized_amount" ) : 0;
    }
}
