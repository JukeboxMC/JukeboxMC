package org.jukeboxmc.nbt;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class NbtMapBuilder extends LinkedHashMap<String, Object> {

    NbtMapBuilder() {
    }

    public static NbtMapBuilder from( NbtMap map ) {
        NbtMapBuilder builder = new NbtMapBuilder();
        builder.putAll( map );
        return builder;
    }

    @Override
    public Object put( String key, Object value ) {
        requireNonNull( value, "value" );

        if ( value instanceof Boolean ) {
            value = (byte) ( (boolean) value ? 1 : 0 );
        }

        NbtType.byClass( value.getClass() ); // Make sure value is valid
        return super.put( key, NbtUtils.copy( value ) );
    }

    public NbtMapBuilder putBoolean( String name, boolean value ) {
        this.put( name, (byte) ( value ? 1 : 0 ) );
        return this;
    }

    public NbtMapBuilder putByte( String name, byte value ) {
        this.put( name, value );
        return this;
    }

    public NbtMapBuilder putByteArray( String name, byte[] value ) {
        this.put( name, value );
        return this;
    }

    public NbtMapBuilder putDouble( String name, double value ) {
        this.put( name, value );
        return this;
    }

    public NbtMapBuilder putFloat( String name, float value ) {
        this.put( name, value );
        return this;
    }

    public NbtMapBuilder putIntArray( String name, int[] value ) {
        this.put( name, value );
        return this;
    }

    public NbtMapBuilder putLongArray( String name, long[] value ) {
        this.put( name, value );
        return this;
    }

    public NbtMapBuilder putInt( String name, int value ) {
        this.put( name, value );
        return this;
    }

    public NbtMapBuilder putLong( String name, long value ) {
        this.put( name, value );
        return this;
    }

    public NbtMapBuilder putShort( String name, short value ) {
        this.put( name, value );
        return this;
    }

    public NbtMapBuilder putString( String name, String value ) {
        this.put( name, value );
        return this;
    }

    public NbtMapBuilder putCompound( String name, NbtMap value ) {
        this.put( name, value );
        return this;
    }

    @SafeVarargs
    public final <T> NbtMapBuilder putList( String name, NbtType<T> type, T... values ) {
        this.put( name, new NbtList<>( type, values ) );
        return this;
    }

    public <T> NbtMapBuilder putList( String name, NbtType<T> type, List<T> list ) {
        if ( !( list instanceof NbtList ) ) {
            list = new NbtList<>( type, list );
        }
        this.put( name, list );
        return this;
    }

    public NbtMapBuilder rename( String oldName, String newName ) {
        Object o = this.remove( oldName );
        if ( o != null ) this.put( newName, o );
        return this;
    }

    public NbtMap build() {
        if ( this.isEmpty() ) {
            return NbtMap.EMPTY;
        }
        return new NbtMap( (Map<String, Object>) this );
    }

    @Override
    public String toString() {
        return NbtMap.mapToString( this );
    }
}
