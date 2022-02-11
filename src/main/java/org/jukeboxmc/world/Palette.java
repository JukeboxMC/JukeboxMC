package org.jukeboxmc.world;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jukeboxmc.utils.BinaryStream;
import org.jukeboxmc.utils.Utils;

import java.util.*;

/**
 * @author Kevims KCodeYT
 * @version 1.0
 */
public class Palette {

    private final int[] values;

    public Palette( int first ) {
        Arrays.fill( this.values = new int[4096], first );
    }

    public void set( int index, int value ) {
        this.values[index] = value;
    }

    public int get( int index ) {
        return this.values[index];
    }

    public void copyTo( Palette palette ) {
        System.arraycopy( this.values, 0, palette.values, 0, this.values.length );
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Palette palette = (Palette) o;
        return Arrays.equals( values, palette.values );
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode( values );
    }

    public Map<Integer, Integer> writeTo( BinaryStream binaryStream, WriteType writeType ) {
        Integer foundIndex = 0;
        int nextIndex = 0;
        int lastRuntimeId = -1;

        final int[] indices = new int[4096];
        final Map<Integer, Integer> indexList = new LinkedHashMap<>();
        final List<Integer> runtimeIds = new ArrayList<>();

        for ( short index = 0; index < this.values.length; index++ ) {
            final int runtimeId = this.values[index];
            if ( runtimeId != lastRuntimeId ) {
                foundIndex = indexList.get( runtimeId );
                if ( foundIndex == null ) {
                    runtimeIds.add( runtimeId );
                    indexList.put( runtimeId, nextIndex );
                    foundIndex = nextIndex;
                    nextIndex++;
                }

                lastRuntimeId = runtimeId;
            }

            indices[index] = foundIndex;
        }

        Palette.writeWords( binaryStream, (int) Math.floor( 32 / ( Utils.log2( indexList.size() ) + 1D ) ), indices );

        switch(writeType) {
            case WRITE_NETWORK:
                binaryStream.writeSignedVarInt( runtimeIds.size() );
                for ( int runtimeId : runtimeIds )
                    binaryStream.writeSignedVarInt( runtimeId );
                break;
            case WRITE_DISK:
                binaryStream.writeLInt( runtimeIds.size() );
                for ( int runtimeId : runtimeIds )
                    binaryStream.writeLInt( runtimeId );
                break;
        }

        return indexList;
    }

    public static short[] parseIndices( BinaryStream binaryStream, int versionId ) {
        PaletteVersion version = null;
        for ( PaletteVersion value : PaletteVersion.values() ) {
            if ( value.id == versionId ) {
                version = value;
                break;
            }
        }

        if ( version == null )
            throw new IllegalArgumentException( "Palette version " + versionId + " is unknown" );

        final short[] indices = new short[4096];
        final int words = version.words;
        final int iterations = (int) Math.ceil( indices.length / (float) words );
        for ( int i = 0; i < iterations; i++ ) {
            final int data = binaryStream.readLInt();
            int pos = 0;

            for ( int w = 0; w < words; w++ ) {
                short val = 0;
                for ( byte shift = 0; shift < versionId; shift++ ) {
                    if ( ( data & ( 1 << pos++ ) ) != 0 )
                        val ^= 1 << shift;
                }

                final int arrayIndex = ( i * words ) + w;
                if ( arrayIndex < 4096 )
                    indices[arrayIndex] = val;
            }
        }

        return indices;
    }

    public static void writeWords( BinaryStream binaryStream, int version, int[] words ) {
        PaletteVersion paletteVersion = null;
        for ( PaletteVersion value : PaletteVersion.values() ) {
            if ( value.words <= version && value.writable ) {
                paletteVersion = value;
                break;
            }
        }

        if ( paletteVersion == null )
            throw new IllegalArgumentException( "Palette version " + version + " is unknown" );

        binaryStream.writeByte( ( paletteVersion.id << 1 ) | 1 );

        final int shift = Utils.divisible( paletteVersion.id, 2 );
        int bits = 0;
        int wordsWritten = 0;

        for ( int word : words ) {
            if ( wordsWritten == paletteVersion.words ) {
                binaryStream.writeLInt( bits );
                bits = 0;
                wordsWritten = 0;
            }

            bits |= word << ( wordsWritten << shift );
            wordsWritten++;
        }

        binaryStream.writeLInt( bits );
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private enum PaletteVersion {
        P1( 1, 32, true ),
        P2( 2, 16, true ),
        P3( 3, 10, false ),
        P4( 4, 8, true ),
        P5( 5, 6, false ),
        P6( 6, 5, false ),
        P8( 8, 4, true ),
        P16( 16, 2, true );

        private final int id;
        private final int words;
        private final boolean writable;
    }

    public enum WriteType {
        NONE,
        WRITE_NETWORK,
        WRITE_DISK
    }

}
