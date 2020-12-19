package org.jukeboxmc.block;

import lombok.Getter;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtMapBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Block {

    private static final Map<String, Map<NbtMap, Integer>> STATES = new HashMap<>();

    @Getter
    private int runtimeId;
    private String name;
    private NbtMap states;

    public Block( String name ) {
        this( name, null );
    }

    public Block( String name, NbtMap nbtMap ) {
        this.name = name;

        if ( !STATES.containsKey( this.name ) ) {
            Map<NbtMap, Integer> toRuntimeId = new HashMap<>();
            for ( NbtMap blockMap : BlockPalette.searchBlocks( blockMap -> blockMap.getString( "name" ).equals( this.name ) ) )
                toRuntimeId.put( blockMap.getCompound( "states" ), BlockPalette.getRuntimeId( blockMap ) );
            STATES.put( this.name, toRuntimeId );
        }

        if ( nbtMap == null )
            nbtMap = new ArrayList<>( STATES.get( this.name ).keySet() ).get( 0 );
        this.states = nbtMap;
        this.runtimeId = STATES.get( this.name ).get( this.states );
    }

    public <B extends Block> B setData( int data ) {
        this.states = new ArrayList<>( STATES.get( this.name ).keySet() ).get( data );
        this.runtimeId = STATES.get( this.name ).get( this.states );
        return (B) this;
    }

    public <B extends Block> B states( String state, Object value ) {
        if ( !this.states.containsKey( state ) )
            throw new AssertionError( "State " + state + " was not found in block " + this.name );
        if ( this.states.get( state ).getClass() != value.getClass() )
            throw new AssertionError( "State " + state + " type is not the same for value  " + value );
        NbtMapBuilder nbtMapBuilder = this.states.toBuilder();
        nbtMapBuilder.put( state, value );
        for ( Map.Entry<NbtMap, Integer> entry : STATES.get( this.name ).entrySet() ) {
            NbtMap blockMap = entry.getKey();
            if ( blockMap.equals( nbtMapBuilder ) ) {
                this.states = blockMap;
            }
        }
        this.runtimeId = STATES.get( this.name ).get( this.states );
        return (B) this;
    }

}
