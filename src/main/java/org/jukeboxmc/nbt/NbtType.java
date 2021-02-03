package org.jukeboxmc.nbt;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Cloudburst
 * @version 1.0
 */
public final class NbtType<T> {

    public static final NbtType<Void> END = new NbtType<>( Void.class, Enum.END );
    public static final NbtType<Byte> BYTE = new NbtType<>( Byte.class, Enum.BYTE );
    public static final NbtType<Short> SHORT = new NbtType<>( Short.class, Enum.SHORT );
    public static final NbtType<Integer> INT = new NbtType<>( Integer.class, Enum.INT );
    public static final NbtType<Long> LONG = new NbtType<>( Long.class, Enum.LONG );
    public static final NbtType<Float> FLOAT = new NbtType<>( Float.class, Enum.FLOAT );
    public static final NbtType<Double> DOUBLE = new NbtType<>( Double.class, Enum.DOUBLE );
    public static final NbtType<byte[]> BYTE_ARRAY = new NbtType<>( byte[].class, Enum.BYTE_ARRAY );
    public static final NbtType<String> STRING = new NbtType<>( String.class, Enum.STRING );
    public static final NbtType<NbtList> LIST = new NbtType<>( NbtList.class, Enum.LIST );
    public static final NbtType<NbtMap> COMPOUND = new NbtType<>( NbtMap.class, Enum.COMPOUND );
    public static final NbtType<int[]> INT_ARRAY = new NbtType<>( int[].class, Enum.INT_ARRAY );
    public static final NbtType<long[]> LONG_ARRAY = new NbtType<>( long[].class, Enum.LONG_ARRAY );

    private static final NbtType<?>[] BY_ID =
            { END, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, BYTE_ARRAY, STRING, LIST, COMPOUND, INT_ARRAY, LONG_ARRAY };

    private static final Map<Class<?>, NbtType<?>> BY_CLASS = new HashMap<>();

    static {
        for ( NbtType<?> type : BY_ID ) {
            BY_CLASS.put( type.getTagClass(), type );
        }
    }

    private final Class<T> tagClass;
    private final Enum enumeration;

    private NbtType( Class<T> tagClass, Enum enumeration ) {
        this.tagClass = tagClass;
        this.enumeration = enumeration;
    }

    public static NbtType<?> byId( int id ) {
        if ( id >= 0 && id < BY_ID.length ) {
            return BY_ID[id];
        } else {
            throw new IndexOutOfBoundsException( "Tag type id must be greater than 0 and less than " + ( BY_ID.length - 1 ) );
        }
    }

    public static <T> NbtType<T> byClass( Class<T> tagClass ) {
        NbtType<T> type = (NbtType<T>) BY_CLASS.get( tagClass );
        if ( type == null ) {
            throw new IllegalArgumentException( "Tag of class " + tagClass + " does not exist" );
        }
        return type;
    }


    public Class<T> getTagClass() {
        return tagClass;
    }

    public int getId() {
        return enumeration.ordinal();
    }

    public String getTypeName() {
        return enumeration.getName();
    }

    public Enum getEnum() {
        return enumeration;
    }

    public enum Enum {
        END,
        BYTE,
        SHORT,
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        BYTE_ARRAY,
        STRING,
        LIST,
        COMPOUND,
        INT_ARRAY,
        LONG_ARRAY;

        private final String name;

        Enum() {
            this.name = "TAG_" + this.name();
        }

        public String getName() {
            return name;
        }
    }
}
