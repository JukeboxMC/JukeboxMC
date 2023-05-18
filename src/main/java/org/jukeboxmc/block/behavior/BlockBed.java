package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.BlockColor;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.blockentity.BlockEntityBed;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBed extends Block implements Waterlogable {

    public BlockBed( Identifier identifier ) {
        super( identifier );
    }

    public BlockBed( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        BlockColor blockColor = BlockColor.values()[itemInHand.getMeta()];

        Block blockDirection = this.getSide( player.getDirection() );
        Location directionLocation = blockDirection.getLocation();

        if ( blockDirection.canBeReplaced( this ) && blockDirection.isTransparent() ) {
            if (world.getBlock(placePosition) instanceof BlockWater blockWater && blockWater.getLiquidDepth() == 0 &&
                    world.getBlock(directionLocation) instanceof BlockWater blockWaterDirection && blockWaterDirection.getLiquidDepth() == 0) {
                world.setBlock(placePosition, Block.create(BlockType.WATER), 1, false);
                world.setBlock(directionLocation, Block.create(BlockType.WATER), 1, false);
            }

            this.setDirection( player.getDirection() );

            BlockBed blockBed = Block.create( BlockType.BED );
            blockBed.setLocation( directionLocation );
            blockBed.setDirection( this.getDirection() );
            blockBed.setHeadPiece( true );

            world.setBlock( directionLocation, blockBed, 0 );
            world.setBlock( placePosition, this, 0 );

            BlockEntity.<BlockEntityBed>create( BlockEntityType.BED, blockBed ).setColor( blockColor ).spawn();
            BlockEntity.<BlockEntityBed>create( BlockEntityType.BED, this ).setColor( blockColor ).spawn();
            return true;
        }
        return false;
    }

    @Override
    public void onBlockBreak( Vector breakPosition ) {
        Direction direction = this.getDirection();
        if ( this.isHeadPiece() ) {
            direction = direction.opposite();
        }
        Block otherBlock = this.getSide( direction );
        if ( otherBlock instanceof BlockBed blockBed ) {
            if ( blockBed.isHeadPiece() != this.isHeadPiece() ) {
                this.location.getWorld().setBlock( otherBlock.getLocation(), Block.create( BlockType.AIR ), 0 );
            }
        }
        this.location.getWorld().setBlock( breakPosition, Block.create( BlockType.AIR ), 0 );
    }

    @Override
    public BlockEntityBed getBlockEntity() {
        return (BlockEntityBed) this.location.getWorld().getBlockEntity( this.location, this.location.getDimension() );
    }

    @Override
    public int getWaterLoggingLevel() {
        return 1;
    }

    public void setHeadPiece(boolean value ) {
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
            case SOUTH -> this.setState( "direction", 0 );
            case WEST -> this.setState( "direction", 1 );
            case NORTH -> this.setState( "direction", 2 );
            case EAST -> this.setState( "direction", 3 );
        }
    }

    public Direction getDirection() {
        int value = this.stateExists( "direction" ) ? this.getIntState( "direction" ) : 0;
        return switch ( value ) {
            case 0 -> Direction.SOUTH;
            case 1 -> Direction.WEST;
            case 2 -> Direction.NORTH;
            default -> Direction.EAST;
        };
    }
}
