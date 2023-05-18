package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
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
public class BlockDoor extends Block implements Waterlogable {

    public BlockDoor( Identifier identifier ) {
        super( identifier );
    }

    public BlockDoor( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        this.setDirection( Direction.fromAngle( player.getYaw() ) );

        BlockDoor blockAbove = Block.create( this.getType() );
        blockAbove.setLocation( new Location( world, placePosition.add( 0, 1, 0 ) ) );
        blockAbove.setDirection( this.getDirection() );
        blockAbove.setUpperBlock( true );
        blockAbove.setOpen( false );

        Block blockLeft = world.getBlock( placePosition ).getSide( player.getDirection().getLeftDirection() );

        if ( blockLeft.getIdentifier().equals( this.identifier ) ) {
            blockAbove.setDoorHinge( true );
            this.setDoorHinge( true );
        } else {
            blockAbove.setDoorHinge( false );
            this.setDoorHinge( false );
        }

        this.setUpperBlock( false );
        this.setOpen( false );

        world.setBlock( placePosition.add( 0, 1, 0 ), blockAbove, 0 );
        world.setBlock( placePosition, this, 0 );

        if (world.getBlock(placePosition) instanceof BlockWater blockWater && blockWater.getLiquidDepth() == 0) {
            world.setBlock(placePosition.add(0, 1, 0), Block.create(BlockType.WATER), 1, false);
            world.setBlock(placePosition, Block.create(BlockType.WATER), 1, false);
        }
        return true;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        this.setOpen( !this.isOpen() );
        this.location.getWorld().sendLevelEvent( this.location, LevelEvent.SOUND_DOOR_OPEN, 0 );
        return true;
    }

    @Override
    public void onBlockBreak( Vector breakPosition ) {
        Block block = this.location.getWorld().getBlock( breakPosition, 1 );
        if ( this.isUpperBlock() ) {
            this.location.getWorld().setBlock( this.location.subtract( 0, 1, 0 ), block , 0);
            this.location.getWorld().setBlock( this.location.subtract( 0, 1, 0 ), Block.create( BlockType.AIR ), 1 );
        } else {
            this.location.getWorld().setBlock( this.location.add( 0, 1, 0 ), block, 0 );
            this.location.getWorld().setBlock( this.location.add( 0, 1, 0 ), Block.create( BlockType.AIR ), 1 );
        }
        this.location.getWorld().setBlock( this.location, block, 0 );
        this.location.getWorld().setBlock( this.location, Block.create( BlockType.AIR ), 1 );
    }

    @Override
    public int getWaterLoggingLevel() {
        return 1;
    }

    public BlockDoor setOpen(boolean value ) {
        return this.setState( "open_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isOpen() {
        return this.stateExists( "open_bit" ) && this.getByteState( "open_bit" ) == 1;
    }

    public BlockDoor setUpperBlock( boolean value ) {
        return this.setState( "upper_block_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isUpperBlock() {
        return this.stateExists( "upper_block_bit" ) && this.getByteState( "upper_block_bit" ) == 1;
    }

    public BlockDoor setDoorHinge( boolean value ) {
        return this.setState( "door_hinge_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isDoorHinge() {
        return this.stateExists( "door_hinge_bit" ) && this.getByteState( "door_hinge_bit" ) == 1;
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
