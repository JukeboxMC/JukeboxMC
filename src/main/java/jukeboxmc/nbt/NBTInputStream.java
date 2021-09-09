package jukeboxmc.nbt;

import java.io.Closeable;
import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import static org.jukeboxmc.nbt.NbtUtils.MAX_DEPTH;

/**
 * @author Cloudburst
 * @version 1.0
 */
public class NBTInputStream implements Closeable {
    private final DataInput input;
    private boolean closed = false;

    public NBTInputStream( DataInput input ) {
        this.input = Objects.requireNonNull( input, "input" );
    }

    public Object readTag() throws IOException {
        return this.readTag( MAX_DEPTH );
    }

    public Object readTag( int maxDepth ) throws IOException {
        if ( closed ) {
            throw new IllegalStateException( "Trying to read from a closed reader!" );
        }
        int typeId = input.readUnsignedByte();
        NbtType<?> type = NbtType.byId( typeId );
        input.readUTF(); // Root tag name

        return this.deserialize( type, maxDepth );
    }

    @SuppressWarnings ( { "rawtypes" } )
    private Object deserialize( NbtType<?> type, int maxDepth ) throws IOException {
        if ( maxDepth < 0 ) {
            throw new IllegalArgumentException( "NBT compound is too deeply nested" );
        }

        switch ( type.getEnum() ) {
            case END:
                return null;
            case BYTE:
                return input.readByte();
            case SHORT:
                return input.readShort();
            case INT:
                return input.readInt();
            case LONG:
                return input.readLong();
            case FLOAT:
                return input.readFloat();
            case DOUBLE:
                return input.readDouble();
            case BYTE_ARRAY:
                int arraySize = input.readInt();
                byte[] bytes = new byte[arraySize];
                input.readFully( bytes );
                return bytes;
            case STRING:
                return input.readUTF();
            case COMPOUND:
                LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                NbtType<?> nbtType;
                while ( ( nbtType = NbtType.byId( input.readUnsignedByte() ) ) != NbtType.END ) {
                    String name = input.readUTF();
                    map.put( name, this.deserialize( nbtType, maxDepth - 1 ) );
                }
                return new NbtMap( map );
            case LIST:
                int typeId = input.readUnsignedByte();
                NbtType<?> listType = NbtType.byId( typeId );
                List<? super Object> list = new ArrayList<>();
                int listLength = input.readInt();
                for ( int i = 0; i < listLength; i++ ) {
                    list.add( this.deserialize( listType, maxDepth - 1 ) );
                }
                return new NbtList( listType, list );
            case INT_ARRAY:
                arraySize = input.readInt();
                int[] ints = new int[arraySize];
                for ( int i = 0; i < arraySize; i++ ) {
                    ints[i] = input.readInt();
                }
                return ints;
            case LONG_ARRAY:
                arraySize = input.readInt();
                long[] longs = new long[arraySize];
                for ( int i = 0; i < arraySize; i++ ) {
                    longs[i] = input.readLong();
                }
                return longs;
        }

        throw new IllegalArgumentException( "Unknown type " + type );
    }

    @Override
    public void close() throws IOException {
        if ( closed ) return;
        closed = true;
        if ( input instanceof Closeable ) {
            ( (Closeable) input ).close();
        }
    }
}
