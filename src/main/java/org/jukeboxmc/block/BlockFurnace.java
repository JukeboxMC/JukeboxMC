package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.blockentity.BlockEntityFurnace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFurnace extends Block {

    public BlockFurnace() {
        super( "minecraft:furnace" );
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition placePosition, Item itemIndHand, BlockFace blockFace ) {
        this.setBlockFace( player.getDirection().toBlockFace().opposite() );
        world.setBlock( placePosition, this );
    }

    @Override
    public boolean interact( Player player, BlockPosition blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        BlockEntityFurnace blockEntity = (BlockEntityFurnace) this.getBlockEntity();
        if ( blockEntity != null ) {
            blockEntity.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
            return true;
        }
        return false;
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntity getBlockEntity() {
        BlockEntity blockEntity = this.world.getBlockEntity( this.position );
        if ( blockEntity == null ) {
            return new BlockEntityFurnace( this );
        }
        return blockEntity;
    }

    public void setBlockFace( BlockFace blockFace ) {
        switch ( blockFace ) {
            case DOWN:
                this.setState( "facing_direction", 0 );
                break;
            case UP:
                this.setState( "facing_direction", 1 );
                break;
            case NORTH:
                this.setState( "facing_direction", 2 );
                break;
            case SOUTH:
                this.setState( "facing_direction", 3 );
                break;
            case WEST:
                this.setState( "facing_direction", 4 );
                break;
            case EAST:
                this.setState( "facing_direction", 5 );
                break;
            default:
                break;
        }
    }

    public BlockFace getBlockFace() {
        int value = this.stateExists( "facing_direction" ) ? this.getIntState( "facing_direction" ) : 2;
        switch ( value ) {
            case 0:
                return BlockFace.DOWN;
            case 1:
                return BlockFace.UP;
            case 2:
                return BlockFace.NORTH;
            case 3:
                return BlockFace.SOUTH;
            case 4:
                return BlockFace.WEST;
            default:
                return BlockFace.EAST;
        }
    }
}
