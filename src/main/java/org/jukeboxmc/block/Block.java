package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAir;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtMapBuilder;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */

public class Block {

    private static final Map<String, Map<NbtMap, Integer>> STATES = new HashMap<>();

    private int runtimeId;
    private String identifier;
    protected NbtMap blockStates;

    protected World world;
    protected BlockPosition position;
    protected int layer = 0;

    public Block( String identifier ) {
        this( identifier, null );
    }

    public Block( String identifier, NbtMap blockStates ) {
        this.identifier = identifier.toLowerCase();

        if ( !STATES.containsKey( this.identifier ) ) {
            Map<NbtMap, Integer> toRuntimeId = new HashMap<>();
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
    }

    public <B extends Block> B setData( int data ) {
        this.blockStates = new ArrayList<>( STATES.get( this.identifier ).keySet() ).get( data );
        this.runtimeId = STATES.get( this.identifier ).get( this.blockStates );
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
        return (B) this;
    }

    public boolean stateExists( String value ) {
        return this.blockStates.containsKey( value );
    }

    public String getStringState( String value ) {
        return this.blockStates.getString( value );
    }

    public byte getByteState( String value ) {
        return this.blockStates.getByte( value );
    }

    public int getIntState( String value ) {
        return this.blockStates.getInt( value );
    }

    //Other
    public void placeBlock( Player player, World world, BlockPosition placePosition, Item itemIndHand, BlockFace blockFace ) {
        if ( this.getBlockType() != BlockType.AIR ) {
            world.setBlock( placePosition, this );
        } else {
            System.out.println( "Try to place block -> " + this.getName() );
        }
    }

    public boolean interact( Player player, BlockPosition blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        return false;
    }

    public boolean canBeReplaced() {
        return false;
    }

    public Item toItem() {
        for ( Item item : ItemType.getItems() ) {
            if ( item.getIdentifier().equals( this.identifier ) ) {
                return item;
            }
        }
        return new ItemAir();
    }

    public BlockType getBlockType() {
        for ( BlockType value : BlockType.values() ) {
            if ( value.getBlockClass() == this.getClass() ) {
                return value;
            }
        }
        return BlockType.AIR;
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

    public BlockPosition getPosition() {
        return this.position;
    }

    public int getLayer() {
        return this.layer;
    }

    public void setLayer( int layer ) {
        this.layer = layer;
    }

    public void setPosition( BlockPosition position ) {
        this.position = position;
    }

    public void setWorld( World world ) {
        this.world = world;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public AxisAlignedBB getBoundingBox() {
        return new AxisAlignedBB(
                this.position.getX(),
                this.position.getY(),
                this.position.getZ(),
                this.position.getX() + 1,
                this.position.getY() + 1,
                this.position.getZ() + 1
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

    @Override
    public String toString() {
        return "Block{" +
                "runtimeId=" + runtimeId +
                ", identifier='" + identifier + '\'' +
                ", blockStates=" + blockStates.toString() +
                ", world=" + world +
                ", position=" + position +
                ", layer=" + layer +
                '}';
    }
}
