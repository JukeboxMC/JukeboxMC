package org.jukeboxmc.block;

import lombok.Getter;
import org.jukeboxmc.item.Item;
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
@Getter
public class Block {

    private static final Map<String, Map<NbtMap, Integer>> STATES = new HashMap<>();

    private int runtimeId;
    private String identifer;
    private NbtMap states;

    private World world;
    private BlockPosition position;

    public Block( String identifer ) {
        this( identifer, null );
    }

    public Block( String identifer, NbtMap nbtMap ) {
        this.identifer = identifer.toLowerCase();

        if ( !STATES.containsKey( this.identifer ) ) {
            Map<NbtMap, Integer> toRuntimeId = new HashMap<>();
            for ( NbtMap blockMap : BlockPalette.searchBlocks( blockMap -> blockMap.getString( "name" ).toLowerCase().equals( this.identifer ) ) ) {
                toRuntimeId.put( blockMap.getCompound( "states" ), BlockPalette.getRuntimeId( blockMap ) );
            }
            STATES.put( this.identifer, toRuntimeId );
            for ( NbtMap state : toRuntimeId.keySet() ) {
                try {
                    int runtimeId = toRuntimeId.get( state );
                    Block block = this.getClass().newInstance();
                    block.runtimeId = runtimeId;
                    block.identifer = identifer;
                    block.states = state;
                    BlockPalette.RUNTIME_TO_BLOCK.put( runtimeId, block );
                } catch ( InstantiationException | IllegalAccessException e ) {
                    e.printStackTrace();
                }
            }
        }

        if ( nbtMap == null ) {
            List<NbtMap> states = new ArrayList<>( STATES.get( this.identifer ).keySet() );
            nbtMap = states.isEmpty() ? NbtMap.EMPTY : states.get( 0 );
        }

        this.states = nbtMap;
        this.runtimeId = STATES.get( this.identifer ).get( this.states );
    }

    public <B extends Block> B setData( int data ) {
        this.states = new ArrayList<>( STATES.get( this.identifer ).keySet() ).get( data );
        this.runtimeId = STATES.get( this.identifer ).get( this.states );
        return (B) this;
    }

    public <B extends Block> B setStates( String state, Object value ) {
        if ( !this.states.containsKey( state ) ) {
            throw new AssertionError( "State " + state + " was not found in block " + this.identifer );
        }
        if ( this.states.get( state ).getClass() != value.getClass() ) {
            throw new AssertionError( "State " + state + " type is not the same for value  " + value );
        }
        NbtMapBuilder nbtMapBuilder = this.states.toBuilder();
        nbtMapBuilder.put( state, value );
        for ( Map.Entry<NbtMap, Integer> entry : STATES.get( this.identifer ).entrySet() ) {
            NbtMap blockMap = entry.getKey();
            if ( blockMap.equals( nbtMapBuilder ) ) {
                this.states = blockMap;
            }
        }
        this.runtimeId = STATES.get( this.identifer ).get( this.states );
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

    public void placeBlock( World world, BlockPosition placePosition, Item itemIndHand ) {
        world.setBlock( placePosition, this );
    }

    public World getWorld() {
        return this.world;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public BlockPosition getPosition() {
        return this.position;
    }
}
