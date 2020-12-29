package org.jukeboxmc.block;

import lombok.Getter;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtMapBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
    protected NbtMap states;

    public Block( String identifer ) {
        this( identifer, null );
    }

    public Block( String identifer, NbtMap nbtMap ) {
        this.identifer = identifer;

        if ( !STATES.containsKey( this.identifer ) ) {
            Map<NbtMap, Integer> toRuntimeId = new LinkedHashMap<>();
            for ( NbtMap blockMap : BlockPalette.searchBlocks( blockMap -> blockMap.getString( "name" ).equals( this.identifer ) ) ) {
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
            nbtMap = new ArrayList<>( STATES.get( this.identifer ).keySet() ).get( 0 );
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

}
