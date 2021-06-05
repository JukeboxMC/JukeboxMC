package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.block.type.BlockColor;
import org.jukeboxmc.blockentity.BlockEntityBed;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemBed;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBed extends BlockWaterlogable {

    public BlockBed() {
        super( "minecraft:bed" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        BlockColor blockColor = BlockColor.values()[itemIndHand.getMeta()];
        Block block = world.getBlock( placePosition );

        Block blockDirection = this.getSide( player.getDirection() );
        Block blockNext = world.getBlock( blockDirection.getLocation() );

        if ( blockDirection.canBeReplaced( this ) && blockNext.isTransparent() ) {
            this.setDirection( player.getDirection() );

            BlockBed blockBed = new BlockBed();
            blockBed.setLocation( blockNext.getLocation() );
            blockBed.setDirection( this.getDirection() );
            blockBed.setHeadPiece( true );

            world.setBlock( blockNext.getLocation(), blockBed );
            world.setBlock( placePosition, this );

            if( block instanceof BlockFlowingWater || block instanceof BlockWater ) {
                world.setBlock( blockNext.getLocation(), block, 1, player.getDimension() );
                world.setBlock( placePosition, block, 1, player.getDimension() );
            }

            BlockEntityType.BED.<BlockEntityBed>createBlockEntity( blockBed ).setColor( blockColor ).spawn();
            BlockEntityType.BED.<BlockEntityBed>createBlockEntity( this ).setColor( blockColor ).spawn();
            return true;
        }
        return false;
    }

    @Override
    public boolean onBlockBreak( Vector breakPosition, boolean isCreative ) {
        Block block = this.world.getBlock( breakPosition, 1 );
        Direction direction = this.getDirection();
        if ( this.isHeadPiece() ) {
            direction = direction.opposite();
        }
        Block otherBlock = this.getSide( direction );
        if ( otherBlock instanceof BlockBed ) {
            BlockBed blockBed = (BlockBed) otherBlock;
            if ( blockBed.isHeadPiece() != this.isHeadPiece() ) {
                this.world.setBlock( otherBlock.getLocation(), block );
                this.world.setBlock( otherBlock.getLocation(), new BlockAir(), 1 );
            }
        }
        this.world.setBlock( breakPosition, block );
        this.world.setBlock( breakPosition, new BlockAir(), 1 );
        return true;
    }

    @Override
    public ItemBed toItem() {
        return new ItemBed();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BED;
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntityBed getBlockEntity() {
        return (BlockEntityBed) this.world.getBlockEntity( this.getLocation(), this.location.getDimension() );
    }

    public BlockColor getColor() {
        return this.getBlockEntity().getColor();
    }

    public void setHeadPiece( boolean value ) {
        this.setState( "head_piece_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isHeadPiece() {
        return this.stateExists( "head_piece_bit" ) && this.getByteState( "head_piece_bit" ) == 1;
    }

    public void setOccupied( boolean value ) {
        this.setState( "occupied_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isOccupied() {
        return this.stateExists( "occupied_bit" ) && this.getByteState( "occupied_bit" ) == 1;
    }

    public void setDirection( Direction direction ) {
        switch ( direction ) {
            case SOUTH:
                this.setState( "direction", 0 );
                break;
            case WEST:
                this.setState( "direction", 1 );
                break;
            case NORTH:
                this.setState( "direction", 2 );
                break;
            case EAST:
                this.setState( "direction", 3 );
                break;
        }
    }

    public Direction getDirection() {
        int value = this.stateExists( "direction" ) ? this.getIntState( "direction" ) : 0;
        switch ( value ) {
            case 0:
                return Direction.SOUTH;
            case 1:
                return Direction.WEST;
            case 2:
                return Direction.NORTH;
            default:
                return Direction.EAST;
        }
    }
}
