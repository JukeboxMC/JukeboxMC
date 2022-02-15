package org.jukeboxmc.world;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jukeboxmc.utils.BinaryStream;
import org.jukeboxmc.utils.Utils;

import java.util.Arrays;

/**
 * @author Kevims KCodeYT
 * @version 1.0
 */
public class Palette {

    private static final ThreadLocal<Integer[]> INDICES = ThreadLocal.withInitial( () -> new Integer[4096] );
    private static final int PALETTE_SIZE = 4096;
    private static final Integer ZERO = 0;

    private int[] values;
    private int first;
    private boolean allEqual;

    public Palette( int first ) {
        this.setFirst( first );
    }

    public void setFirst( int first ) {
        this.values = null;
        this.first = first;
        this.allEqual = true;
    }

    public void set( int index, int value ) {
        if ( this.allEqual && this.first == value ) {
            return;
        }

        if ( this.allEqual ) {
            this.allEqual = false;
            Arrays.fill( this.values = new int[PALETTE_SIZE], this.first );
        }

        this.values[index] = value;
    }

    public int get( int index ) {
        if ( this.allEqual ) {
            return this.first;
        }

        return this.values[index];
    }

    public void copyTo( Palette palette ) {
        if ( this.allEqual && palette.allEqual ) {
            palette.values = this.values;
        } else if ( this.allEqual && !palette.allEqual ) {
            palette.values = this.values;
            palette.allEqual = true;
        } else if ( !this.allEqual && palette.allEqual ) {
            System.arraycopy( this.values, 0, palette.values = new int[PALETTE_SIZE], 0, this.values.length );
            palette.allEqual = false;
        } else {
            System.arraycopy( this.values, 0, palette.values = new int[PALETTE_SIZE], 0, this.values.length );
        }
    }

    public boolean isAllEqual() {
        return this.allEqual;
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

    //Dachte hier fehlt vlt auch irgendwo nh .release hmm eigentlich nicht, aber sollte auch nicht daran liegen

    public Int2ObjectMap<Integer> writeTo( BinaryStream binaryStream, WriteType writeType ) {
        final Integer[] indices = INDICES.get();
        final Int2ObjectMap<Integer> indexList = new Int2ObjectOpenHashMap<>();
        final IntList runtimeIds = new IntArrayList();

        if ( this.allEqual ) {
            runtimeIds.add( this.first );
            indexList.put( this.first, ZERO );
            Arrays.fill( indices, ZERO );
        } else {
            Integer foundIndex = 0;
            Integer nextIndex = 0;
            int lastRuntimeId = -1;

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
        }

        Palette.writeWords( binaryStream, (int) Math.floor( 32 / ( Utils.log2( indexList.size() ) + 1D ) ), indices );

        switch ( writeType ) {
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

    public static void writeWords( BinaryStream binaryStream, int version, Integer[] words ) {
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

        for ( Integer word : words ) {
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

    @RequiredArgsConstructor ( access = AccessLevel.PRIVATE )
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
