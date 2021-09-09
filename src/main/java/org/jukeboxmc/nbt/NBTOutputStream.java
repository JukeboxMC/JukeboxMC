package org.jukeboxmc.nbt;

import java.io.Closeable;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import static org.jukeboxmc.nbt.NbtType.byClass;
import static org.jukeboxmc.nbt.NbtUtils.MAX_DEPTH;

/**
 * @author Cloudburst
 * @version 1.0
 */
public class NBTOutputStream implements Closeable {

    private final DataOutput output;
    private boolean closed = false;

    public NBTOutputStream( DataOutput output ) {
        this.output = Objects.requireNonNull( output, "output" );
    }

    public void writeTag( Object tag ) throws IOException {
        this.writeTag( tag, MAX_DEPTH );
    }

    public void writeTag( Object tag, int maxDepth ) throws IOException {
        Objects.requireNonNull( tag, "tag" );
        if ( closed ) {
            throw new IllegalStateException( "closed" );
        }

        NbtType<?> type = NbtType.byClass( tag.getClass() );

        output.writeByte( type.getId() );
        output.writeUTF( "" );

        this.serialize( tag, maxDepth );
    }

    private void serialize( Object tag, int maxDepth ) throws IOException {
        if ( maxDepth < 0 ) {
            throw new IllegalArgumentException( "Reached depth limit" );
        }
        NbtType<?> type = byClass( tag.getClass() );

        switch ( type.getEnum() ) {
            case END:
                break;
            case BYTE:
                Byte byteVal = (Byte) tag;
                output.writeByte( byteVal );
                break;
            case SHORT:
                Short shortVal = (Short) tag;
                output.writeShort( shortVal );
                break;
            case INT:
                Integer intVal = (Integer) tag;
                output.writeInt( intVal );
                break;
            case LONG:
                Long longVal = (Long) tag;
                output.writeLong( longVal );
                break;
            case FLOAT:
                Float floatVal = (Float) tag;
                output.writeFloat( floatVal );
                break;
            case DOUBLE:
                Double doubleVal = (Double) tag;
                output.writeDouble( doubleVal );
                break;
            case BYTE_ARRAY:
                byte[] byteArray = (byte[]) tag;
                output.writeInt( byteArray.length );
                output.write( byteArray );
                break;
            case STRING:
                String string = (String) tag;
                output.writeUTF( string );
                break;
            case LIST:
                NbtList<?> list = (NbtList<?>) tag;
                NbtType<?> listType = list.getType();
                output.writeByte( listType.getId() );
                output.writeInt( list.size() );
                for ( Object entry : list ) {
                    this.serialize( entry, maxDepth - 1 );
                }
                break;
            case COMPOUND:
                NbtMap map = (NbtMap) tag;

                for ( Map.Entry<String, Object> entry : map.entrySet() ) {
                    NbtType<?> entryType = NbtType.byClass( entry.getValue().getClass() );

                    output.writeByte( entryType.getId() );
                    output.writeUTF( entry.getKey() );

                    this.serialize( entry.getValue(), maxDepth - 1 );
                }
                output.writeByte( 0 ); // End tag
                break;
            case INT_ARRAY:
                int[] intArray = (int[]) tag;
                output.writeInt( intArray.length );
                for ( int val : intArray ) {
                    output.writeInt( val );
                }
                break;
            case LONG_ARRAY:
                long[] longArray = (long[]) tag;
                output.writeInt( longArray.length );
                for ( long val : longArray ) {
                    output.writeLong( val );
                }
                break;
        }
    }

    @Override
    public void close() throws IOException {
        if ( closed ) return;
        closed = true;
        if ( output instanceof Closeable ) {
            ( (Closeable) output ).close();
        }
    }
}
