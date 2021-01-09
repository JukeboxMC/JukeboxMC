package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtMapBuilder;
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
    private NbtMap states;

    private World world;
    private BlockPosition position;
    private int layer = 0;

    public Block( String identifier ) {
        this( identifier, null );
    }

    public Block( String identifier, NbtMap nbtMap ) {
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
                    block.states = state;
                    BlockPalette.RUNTIME_TO_BLOCK.put( runtimeId, block );
                } catch ( InstantiationException | IllegalAccessException e ) {
                    e.printStackTrace();
                }
            }
        }

        if ( nbtMap == null ) {
            List<NbtMap> states = new ArrayList<>( STATES.get( this.identifier ).keySet() );
            nbtMap = states.isEmpty() ? NbtMap.EMPTY : states.get( 0 );
        }

        this.states = nbtMap;
        this.runtimeId = STATES.get( this.identifier ).get( this.states );
    }

    public <B extends Block> B setData( int data ) {
        this.states = new ArrayList<>( STATES.get( this.identifier ).keySet() ).get( data );
        this.runtimeId = STATES.get( this.identifier ).get( this.states );
        return (B) this;
    }

    public <B extends Block> B setState( String state, Object value ) {
        if ( !this.states.containsKey( state ) ) {
            throw new AssertionError( "State " + state + " was not found in block " + this.identifier );
        }
        if ( this.states.get( state ).getClass() != value.getClass() ) {
            throw new AssertionError( "State " + state + " type is not the same for value  " + value );
        }
        NbtMapBuilder nbtMapBuilder = this.states.toBuilder();
        nbtMapBuilder.put( state, value );
        for ( Map.Entry<NbtMap, Integer> entry : STATES.get( this.identifier ).entrySet() ) {
            NbtMap blockMap = entry.getKey();
            if ( blockMap.equals( nbtMapBuilder ) ) {
                this.states = blockMap;
            }
        }
        this.runtimeId = STATES.get( this.identifier ).get( this.states );
        return (B) this;
    }

    public boolean stateExists( String value ) {
        return this.states.containsKey( value );
    }

    public String getStringState( String value ) {
        return this.states.getString( value );
    }

    public byte getByteState( String value ) {
        return this.states.getByte( value );
    }

    public int getIntState( String value ) {
        return this.states.getInt( value );
    }

    //Other
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

    public NbtMap getStates() {
        return this.states;
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

    public void placeBlock( World world, Vector placePosition, Item itemIndHand ) {
        if ( this.getBlockType() != BlockType.AIR ) {
            world.setBlock( placePosition, this );
        } else {
            System.out.println( "Try to place block -> " + this.getName() );
        }
    }
}
