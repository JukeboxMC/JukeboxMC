package org.jukeboxmc.utils;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Palette {

    public enum PaletteVersion {
        P1( 1, 32 ),
        P2( 2, 16 ),
        P3( 3, 10, 2 ),
        P4( 4, 8 ),
        P5( 5, 6, 2 ),
        P6( 6, 5, 2 ),
        P8( 8, 4 ),
        P16( 16, 2 );

        private final byte versionId;
        private final byte amountOfWords;
        private final byte amountOfPadding;

        PaletteVersion( int versionId, int amountOfWords ) {
            this( versionId, amountOfWords, 0 );
        }

        PaletteVersion( int versionId, int amountOfWords, int amountOfPadding ) {
            this.versionId = (byte) versionId;
            this.amountOfWords = (byte) amountOfWords;
            this.amountOfPadding = (byte) amountOfPadding;
        }

        public byte getVersionId() {
            return versionId;
        }

        public byte getAmountOfWords() {
            return amountOfWords;
        }

        public byte getAmountOfPadding() {
            return amountOfPadding;
        }
    }

    private BinaryStream data;
    private PaletteVersion paletteVersion = null;

    private short[] output = null;

    private int bits = 0;
    private int wordsWritten = 0;

    public Palette( BinaryStream data, int version, boolean read ) {
        this.data = data;

        for ( PaletteVersion paletteVersionCanidate : PaletteVersion.values() ) {
            if ( ( !read && paletteVersionCanidate.getAmountOfWords() <= version && paletteVersionCanidate.getAmountOfPadding() == 0 ) ||
                ( read && paletteVersionCanidate.getVersionId() == version ) ) {
                this.paletteVersion = paletteVersionCanidate;
                break;
            }
        }

        if ( this.paletteVersion == null ) {
            throw new IllegalArgumentException( "Palette version " + version + " is unknown" );
        }
    }

    public void addIndexIDs( int[] indexIDs ) {
        int shift;
        switch (this.paletteVersion.getVersionId()) {
            case 1:
                shift = 0;
                break;
            case 2:
                shift = 1;
                break;
            case 4:
                shift = 2;
                break;
            case 8:
                shift = 3;
                break;
            case 16:
                shift = 4;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + this.paletteVersion.getVersionId());
        }

        for ( int id : indexIDs ) {
            if ( this.wordsWritten == this.paletteVersion.getAmountOfWords() ) {
                this.data.writeLInt( this.bits );

                this.bits = 0;
                this.wordsWritten = 0;
            }

            this.bits |= id << (this.wordsWritten << shift);

            this.wordsWritten++;
        }
    }

    public void finish() {
        this.data.writeLInt( this.bits );
        this.bits = 0;
    }

    public short[] getIndexes() {
        if ( this.output == null ) {
            this.output = new short[4096];

            int iterations = (int) Math.ceil( 4096 / (float) this.paletteVersion.getAmountOfWords() );
            for ( int i = 0; i < iterations; i++ ) {
                int currentData = this.data.readLInt();
                int index = 0;

                for ( byte b = 0; b < this.paletteVersion.getAmountOfWords(); b++ ) {
                    short val = 0;
                    int innerShiftIndex = 0;

                    for ( byte i1 = 0; i1 < this.paletteVersion.getVersionId(); i1++ ) {
                        if ( ( currentData & ( 1 << index++ ) ) != 0 ) {
                            val ^= 1 << innerShiftIndex;
                        }

                        innerShiftIndex++;
                    }

                    int setIndex = ( i * this.paletteVersion.getAmountOfWords() ) + b;
                    if ( setIndex < 4096 ) {
                        this.output[setIndex] = val;
                    }
                }
            }
        }

        return this.output;
    }

    public BinaryStream getData() {
        return this.data;
    }

    public PaletteVersion getPaletteVersion() {
        return this.paletteVersion;
    }

    public short[] getOutput() {
        return this.output;
    }

    public int getBits() {
        return this.bits;
    }

    public int getWordsWritten() {
        return this.wordsWritten;
    }
}
