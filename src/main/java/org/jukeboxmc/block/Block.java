package org.jukeboxmc.block;

import lombok.SneakyThrows;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.block.type.UpdateReason;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtMapBuilder;
import org.jukeboxmc.network.packet.UpdateBlockPacket;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.*;

import static org.jukeboxmc.block.BlockType.Companion.update;

/**
 * @author LucGamesYT
 * @version 1.0
 */

public abstract class Block implements Cloneable {

    private static final Map<String, Map<NbtMap, Integer>> STATES = new LinkedHashMap<>();

    protected int runtimeId;
    protected String identifier;
    protected NbtMap blockStates;

    protected World world;
    protected boolean placed;
    protected Location location;
    protected int layer = 0;

    public Block( String identifier ) {
        this( identifier, null );
    }

    public Block( String identifier, NbtMap blockStates ) {
        this.identifier = identifier.toLowerCase();

        if ( !STATES.containsKey( this.identifier ) ) {
            Map<NbtMap, Integer> toRuntimeId = new LinkedHashMap<>();
            for ( NbtMap blockMap : BlockPalette.searchBlocks( blockMap -> blockMap.getString( "name" ).toLowerCase().equals( this.identifier ) ) ) {
                toRuntimeId.put( blockMap.getCompound( "states" ), BlockPalette.getRuntimeId( blockMap ) );
            }
            STATES.put( this.identifier, toRuntimeId );
            for ( NbtMap state : toRuntimeId.keySet() ) {
                try {
                    int runtimeId = toRuntimeId.get( state );
                    Block block = this.getClass().newInstance();
                    block.runtimeId = runtimeId;
                    block.identifier = identifier;
                    block.blockStates = state;
                    BlockPalette.RUNTIME_TO_BLOCK.put( runtimeId, block );
                } catch ( InstantiationException | IllegalAccessException e ) {
                    e.printStackTrace();
                }
            }
        }

        if ( blockStates == null ) {
            List<NbtMap> states = new ArrayList<>( STATES.get( this.identifier ).keySet() );
            blockStates = states.isEmpty() ? NbtMap.EMPTY : states.get( 0 );
        }

        this.blockStates = blockStates;
        this.runtimeId = STATES.get( this.identifier ).get( this.blockStates );
        this.placed = false;
    }

    public <B extends Block> B setData( int data ) {
        this.blockStates = new ArrayList<>( STATES.get( this.identifier ).keySet() ).get( data );
        this.runtimeId = STATES.get( this.identifier ).get( this.blockStates );
        if ( Server.getInstance().isIniatiating() ) {
            update( this.runtimeId, this );
        }
        return (B) this;
    }

    public <B extends Block> B setState( String state, Object value ) {
        if ( !this.blockStates.containsKey( state ) ) {
            throw new AssertionError( "State " + state + " was not found in block " + this.identifier );
        }
        if ( this.blockStates.get( state ).getClass() != value.getClass() ) {
            throw new AssertionError( "State " + state + " type is not the same for value  " + value );
        }

        NbtMapBuilder nbtMapBuilder = this.blockStates.toBuilder();
        nbtMapBuilder.put( state, value );
        for ( Map.Entry<NbtMap, Integer> entry : STATES.get( this.identifier ).entrySet() ) {
            NbtMap blockMap = entry.getKey();
            if ( blockMap.equals( nbtMapBuilder ) ) {
                this.blockStates = blockMap;
            }
        }
        this.runtimeId = STATES.get( this.identifier ).get( this.blockStates );
        if ( Server.getInstance().isIniatiating() ) {
            update( this.runtimeId, this );
        }
        if ( this.getWorld() != null && this.placed ) {
            this.getWorld().sendBlockUpdate( this );
            this.getChunk().setBlock( this.location, this.layer, this.runtimeId );
        }
        return (B) this;
    }

    public boolean stateExists( String value ) {
        return this.blockStates.containsKey( value );
    }

    public String getStringState( String value ) {
        return this.blockStates.getString( value ).toUpperCase();
    }

    public byte getByteState( String value ) {
        return this.blockStates.getByte( value );
    }

    public int getIntState( String value ) {
        return this.blockStates.getInt( value );
    }

    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        if ( this.getBlockType() != BlockType.AIR ) {
            world.setBlock( placePosition, this, 0, player.getDimension(), true );
            return true;
        } else {
            Server.getInstance().getLogger().debug( "Try to place block -> " + this.getName() );
        }
        return false;
    }

    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        return false;
    }

    public boolean onBlockBreak( Vector breakPosition ) {
        this.world.setBlock( breakPosition, new BlockAir() );
        return true;
    }

    public void sendBlockUpdate( Player player ) {
        UpdateBlockPacket updateBlockPacket = new UpdateBlockPacket();
        updateBlockPacket.setBlockId( this.runtimeId );
        updateBlockPacket.setPosition( this.location );
        updateBlockPacket.setFlags( UpdateBlockPacket.FLAG_ALL_PRIORITY );
        updateBlockPacket.setLayer( this.layer );
        player.getPlayerConnection().sendPacket( updateBlockPacket );
    }

    public boolean canBeReplaced( Block block ) {
        return false;
    }

    public abstract Item toItem();

    public abstract BlockType getBlockType();

    public Block getSide( Direction direction, int layer ) {
        switch ( direction ) {
            case SOUTH:
                return this.getRelative( Vector.SOUTH, layer );
            case NORTH:
                return this.getRelative( Vector.NORTH, layer );
            case EAST:
                return this.getRelative( Vector.EAST, layer );
            case WEST:
                return this.getRelative( Vector.WEST, layer );
            default:
                return null;
        }
    }

    public Block getSide( Direction direction ) {
        return this.getSide( direction, 0 );
    }

    public Block getSide( BlockFace blockFace, int layer ) {
        switch ( blockFace ) {
            case DOWN:
                return this.getRelative( Vector.DOWN, layer );
            case UP:
                return this.getRelative( Vector.UP, layer );
            case SOUTH:
                return this.getRelative( Vector.SOUTH, layer );
            case NORTH:
                return this.getRelative( Vector.NORTH, layer );
            case EAST:
                return this.getRelative( Vector.EAST, layer );
            case WEST:
                return this.getRelative( Vector.WEST, layer );
            default:
                return null;
        }
    }

    public Block getSide( BlockFace blockFace ) {
        return this.getSide( blockFace, 0 );
    }

    public Block getRelative( Vector position, int layer ) {
        int x = this.location.getFloorX() + position.getFloorX();
        int y = this.location.getFloorY() + position.getFloorY();
        int z = this.location.getFloorZ() + position.getFloorZ();
        return this.world.getBlockAt( x, y, z, this.location.getDimension(), layer );
    }

    public int getRuntimeId() {
        return this.runtimeId;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public NbtMap getBlockStates() {
        return this.blockStates;
    }

    public World getWorld() {
        return this.world;
    }

    public void setLocation( Location location ) {
        this.world = location.getWorld();
        this.location = location;
    }

    public Chunk getChunk() {
        return this.world.getChunk( this.location.getFloorX() >> 4, this.location.getFloorZ() >> 4, this.location.getDimension() );
    }

    public Location getLocation() {
        return this.location;
    }

    public void setPlaced( boolean placed ) {
        this.placed = placed;
    }

    public int getLayer() {
        return this.layer;
    }

    public void setLayer( int layer ) {
        this.layer = layer;
    }

    public void setWorld( World world ) {
        this.world = world;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public AxisAlignedBB getBoundingBox() {
        return new AxisAlignedBB(
                this.location.getX(),
                this.location.getY(),
                this.location.getZ(),
                this.location.getX() + 1,
                this.location.getY() + 1,
                this.location.getZ() + 1
        );
    }

    public boolean hasBlockEntity() {
        return false;
    }

    public BlockEntity getBlockEntity() {
        return null;
    }

    public boolean isSolid() {
        return true;
    }

    public boolean isTransparent() {
        return false;
    }

    public List<Item> getDrops() {
        return Collections.emptyList();
    }

    public long onUpdate( UpdateReason updateReason ) {
        return -1;
    }

    public void enterBlock() {

    }

    public void leaveBlock() {

    }

    public boolean isWater() {
        return this instanceof BlockWater || this instanceof BlockFlowingWater;
    }

    public int getTickRate() {
        return 10;
    }

    public boolean canBeFlowedInto() {
        return false;
    }

    public final boolean canWaterloggingFlowInto() {
        return this.canBeFlowedInto() || (this instanceof BlockWaterlogable && ((BlockWaterlogable) this).getWaterloggingLevel() > 1);
    }

    @Override
    public String toString() {
        return "Block{" +
                "runtimeId=" + runtimeId +
                ", identifier='" + identifier + '\'' +
                ", blockStates=" + blockStates.toString() +
                ", world=" + world +
                ", position=" + location +
                ", layer=" + layer +
                '}';
    }

    @Override
    @SneakyThrows
    public Block clone() {
        Block block = (Block) super.clone();
        block.identifier = this.identifier;
        block.runtimeId = this.runtimeId;
        block.layer = this.layer;
        block.blockStates = this.blockStates.toBuilder().build();
        return block;
    }

}
