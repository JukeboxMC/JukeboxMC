package org.jukeboxmc.nbt;

import org.jukeboxmc.nbt.util.UnmodifiableEntrySet;
import org.jukeboxmc.nbt.util.function.*;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

@Immutable
@ParametersAreNonnullByDefault
public class NbtMap extends AbstractMap<String, Object> {

    public static final NbtMap EMPTY = new NbtMap();

    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final long[] EMPTY_LONG_ARRAY = new long[0];

    private final LinkedHashMap<String, Object> map;

    private transient Set<String> keySet;
    private transient Set<Entry<String, Object>> entrySet;
    private transient Collection<Object> values;
    private transient boolean hashCodeGenerated;
    private transient int hashCode;

    private NbtMap() {
        this.map = new LinkedHashMap<>();
    }

    NbtMap( Map<String, Object> map ) {
        this.map = new LinkedHashMap<>( map );
    }

    NbtMap( LinkedHashMap<String, Object> map ) {
        this.map = map;
    }

    public static NbtMapBuilder builder() {
        return new NbtMapBuilder();
    }

    public NbtMapBuilder toBuilder() {
        return NbtMapBuilder.from( this );
    }

    public boolean containsKey( String key, NbtType<?> type ) {
        Object o = this.map.get( key );
        return o != null && o.getClass() == type.getTagClass();
    }

    @Override
    public Object get( Object key ) {
        return NbtUtils.copy( this.map.get( key ) );
    }

    @Override
    public Set<String> keySet() {
        if ( keySet == null ) keySet = Collections.unmodifiableSet( this.map.keySet() );
        return keySet;
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        if ( entrySet == null ) entrySet = new UnmodifiableEntrySet<>( this.map.entrySet() );
        return entrySet;
    }

    @Override
    public Collection<Object> values() {
        if ( values == null ) values = Collections.unmodifiableCollection( this.map.values() );
        return values;
    }

    public boolean getBoolean( String key ) {
        return this.getBoolean( key, false );
    }

    public boolean getBoolean( String key, boolean defaultValue ) {
        Object tag = this.map.get( key );
        if ( tag instanceof Byte ) {
            return ( (byte) tag ) != 0;
        }
        return defaultValue;
    }

    public void listenForBoolean( String key, BooleanConsumer consumer ) {
        Object tag = this.map.get( key );
        if ( tag instanceof Byte ) {
            consumer.accept( ( (byte) tag ) != 0 );
        }
    }

    public byte getByte( String key ) {
        return this.getByte( key, (byte) 0 );
    }

    public byte getByte( String key, byte defaultValue ) {
        Object tag = this.map.get( key );
        if ( tag instanceof Byte ) {
            return (byte) tag;
        }
        return defaultValue;
    }

    public void listenForByte( String key, ByteConsumer consumer ) {
        Object tag = this.map.get( key );
        if ( tag instanceof Byte ) {
            consumer.accept( (byte) tag );
        }
    }

    public short getShort( String key ) {
        return this.getShort( key, (short) 0 );
    }

    public short getShort( String key, short defaultValue ) {
        Object tag = this.map.get( key );
        if ( tag instanceof Short ) {
            return (short) tag;
        }
        return defaultValue;
    }

    public void listenForShort( String key, ShortConsumer consumer ) {
        Object tag = this.map.get( key );
        if ( tag instanceof Short ) {
            consumer.accept( (short) tag );
        }
    }

    public int getInt( String key ) {
        return this.getInt( key, 0 );
    }

    public int getInt( String key, int defaultValue ) {
        Object tag = this.map.get( key );
        if ( tag instanceof Integer ) {
            return (int) tag;
        }
        return defaultValue;
    }

    public void listenForInt( String key, IntConsumer consumer ) {
        Object tag = this.map.get( key );
        if ( tag instanceof Integer ) {
            consumer.accept( (int) tag );
        }
    }

    public long getLong( String key ) {
        return this.getLong( key, 0L );
    }

    public long getLong( String key, long defaultValue ) {
        Object tag = this.map.get( key );
        if ( tag instanceof Long ) {
            return (long) tag;
        }
        return defaultValue;
    }

    public void listenForLong( String key, LongConsumer consumer ) {
        Object tag = this.map.get( key );
        if ( tag instanceof Long ) {
            consumer.accept( (long) tag );
        }
    }

    public float getFloat( String key ) {
        return this.getFloat( key, 0F );
    }

    public float getFloat( String key, float defaultValue ) {
        Object tag = this.map.get( key );
        if ( tag instanceof Float ) {
            return (float) tag;
        }
        return defaultValue;
    }

    public void listenForFloat( String key, FloatConsumer consumer ) {
        Object tag = this.map.get( key );
        if ( tag instanceof Float ) {
            consumer.accept( (float) tag );
        }
    }

    public double getDouble( String key ) {
        return this.getDouble( key, 0D );
    }

    public double getDouble( String key, double defaultValue ) {
        Object tag = this.map.get( key );
        if ( tag instanceof Double ) {
            return (double) tag;
        }
        return defaultValue;
    }

    public void listenForDouble( String key, DoubleConsumer consumer ) {
        Object tag = this.map.get( key );
        if ( tag instanceof Double ) {
            consumer.accept( (double) tag );
        }
    }

    public String getString( String key ) {
        return this.getString( key, "" );
    }

    public String getString( String key, @Nullable String defaultValue ) {
        Object tag = this.map.get( key );
        if ( tag instanceof String ) {
            return (String) tag;
        }
        return defaultValue;
    }

    public void listenForString( String key, Consumer<String> consumer ) {
        Object tag = this.map.get( key );
        if ( tag instanceof String ) {
            consumer.accept( (String) tag );
        }
    }

    public byte[] getByteArray( String key ) {
        return this.getByteArray( key, EMPTY_BYTE_ARRAY );
    }

    public byte[] getByteArray( String key, @Nullable byte[] defaultValue ) {
        Object tag = this.map.get( key );
        if ( tag instanceof byte[] ) {
            byte[] bytes = (byte[]) tag;
            return Arrays.copyOf( bytes, bytes.length );
        }
        return defaultValue;
    }

    public void listenForByteArray( String key, Consumer<byte[]> consumer ) {
        Object tag = this.map.get( key );
        if ( tag instanceof byte[] ) {
            byte[] bytes = (byte[]) tag;
            consumer.accept( Arrays.copyOf( bytes, bytes.length ) );
        }
    }

    public int[] getIntArray( String key ) {
        return this.getIntArray( key, EMPTY_INT_ARRAY );
    }

    public int[] getIntArray( String key, @Nullable int[] defaultValue ) {
        Object tag = this.map.get( key );
        if ( tag instanceof int[] ) {
            int[] ints = (int[]) tag;
            return Arrays.copyOf( ints, ints.length );
        }
        return defaultValue;
    }

    public void listenForIntArray( String key, Consumer<int[]> consumer ) {
        Object tag = this.map.get( key );
        if ( tag instanceof int[] ) {
            int[] ints = (int[]) tag;
            consumer.accept( Arrays.copyOf( ints, ints.length ) );
        }
    }

    public long[] getLongArray( String key ) {
        return this.getLongArray( key, EMPTY_LONG_ARRAY );
    }

    public long[] getLongArray( String key, @Nullable long[] defaultValue ) {
        Object tag = this.map.get( key );
        if ( tag instanceof long[] ) {
            long[] longs = (long[]) tag;
            return Arrays.copyOf( longs, longs.length );
        }
        return defaultValue;
    }

    public void listenForLongArray( String key, Consumer<long[]> consumer ) {
        Object tag = this.map.get( key );
        if ( tag instanceof long[] ) {
            long[] longs = (long[]) tag;
            consumer.accept( Arrays.copyOf( longs, longs.length ) );
        }
    }

    public NbtMap getCompound( String key ) {
        return this.getCompound( key, EMPTY );
    }

    public NbtMap getCompound( String key, @Nullable NbtMap defaultValue ) {
        Object tag = this.map.get( key );
        if ( tag instanceof NbtMap ) {
            return (NbtMap) tag;
        }
        return defaultValue;
    }

    public void listenForCompound( String key, Consumer<NbtMap> consumer ) {
        Object tag = this.map.get( key );
        if ( tag instanceof NbtMap ) {
            consumer.accept( (NbtMap) tag );
        }
    }

    public <T> List<T> getList( String key, NbtType<T> type ) {
        return this.getList( key, type, Collections.emptyList() );
    }

    public <T> List<T> getList( String key, NbtType<T> type, @Nullable List<T> defaultValue ) {
        Object tag = this.map.get( key );
        if ( tag instanceof NbtList && ( (NbtList<?>) tag ).getType() == type ) {
            return (NbtList<T>) tag;
        }
        return defaultValue;
    }

    public <T> void listenForList( String key, NbtType<T> type, Consumer<List<T>> consumer ) {
        Object tag = this.map.get( key );
        if ( tag instanceof NbtList<?> && ( (NbtList<?>) tag ).getType() == type ) {
            consumer.accept( (NbtList<T>) tag );
        }
    }

    public Number getNumber( String key ) {
        return this.getNumber( key, 0 );
    }

    public Number getNumber( String key, Number defaultValue ) {
        Object tag = this.map.get( key );
        if ( tag instanceof Number ) {
            return (Number) tag;
        }
        return defaultValue;
    }

    public void listenForNumber( String key, NumberConsumer consumer ) {
        Object tag = this.map.get( key );
        if ( tag instanceof Number ) {
            consumer.accept( (Number) tag );
        }
    }

    @Override
    public boolean equals( Object o ) {
        if ( o == this )
            return true;

        if ( !( o instanceof Map ) )
            return false;
        Map<?, ?> m = (Map<?, ?>) o;
        if ( m.size() != this.size() )
            return false;

        try {
            for ( Entry<String, Object> e : this.entrySet() ) {
                String key = e.getKey();
                Object value = e.getValue();
                if ( value == null ) {
                    if ( !( m.get( key ) == null && m.containsKey( key ) ) )
                        return false;
                } else {
                    if ( !Objects.deepEquals( value, m.get( key ) ) )
                        return false;
                }
            }
        } catch ( ClassCastException | NullPointerException unused ) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        if ( this.hashCodeGenerated )
            return this.hashCode;
        int h = 0;
        for ( Entry<String, Object> stringObjectEntry : this.map.entrySet() )
            h += stringObjectEntry.hashCode();
        this.hashCode = h;
        this.hashCodeGenerated = true;
        return h;
    }

    @Override
    public String toString() {
        return mapToString( this.map );
    }

    static String mapToString( Map<String, Object> map ) {
        Iterator<Entry<String, Object>> i = map.entrySet().iterator();
        if ( !i.hasNext() )
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append( '{' ).append( '\n' );
        for ( ; ; ) {
            Entry<String, Object> e = i.next();
            String key = e.getKey();
            String value = NbtUtils.toString( e.getValue() );

            String string = NbtUtils.indent( "\"" + key + "\": " + value );
            sb.append( string );
            if ( !i.hasNext() )
                return sb.append( '\n' ).append( '}' ).toString();
            sb.append( ',' ).append( '\n' );
        }
    }


}
