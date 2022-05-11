package org.jukeboxmc.world.palette.integer;

import com.nukkitx.nbt.NBTInputStream;
import com.nukkitx.nbt.NBTOutputStream;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.network.VarInts;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jukeboxmc.world.palette.bitarray.BitArray;
import org.jukeboxmc.world.palette.bitarray.BitArrayVersion;

import java.io.IOException;

@Getter
@EqualsAndHashCode
public class IntPalette {

    public static final int SIZE = 4096;

    private final IntList palette;
    private BitArray bitArray;

    public IntPalette( int first ) {
        this( first, BitArrayVersion.V2 );
    }

    public IntPalette( int first, BitArrayVersion version ) {
        this.bitArray = version.createArray( SIZE );
        this.palette = new IntArrayList( 16 );
        this.palette.add( first );
    }

    private static int getPaletteHeader( BitArrayVersion version, boolean runtime ) {
        return ( version.bits << 1 ) | ( runtime ? 1 : 0 );
    }

    private static BitArrayVersion getVersionFromHeader( short header ) {
        return BitArrayVersion.get( header >> 1, true );
    }

    private static boolean hasCopyLastFlag( short header ) {
        return ( header >> 1 ) == 0x7F;
    }

    private static int getCopyLastFlagHeader() {
        return ( 0x7F << 1 ) | 1;
    }

    public static boolean isPersistent( short header ) {
        return ( header & 1 ) == 0;
    }

    public int get( int index ) {
        return this.palette.getInt( this.bitArray.get( index ) );
    }

    public void set( int index, int value ) {
        int indexFor = this.paletteIndexFor( value );
        this.bitArray.set( index, indexFor );
    }

    public void writeToNetwork( ByteBuf byteBuf, IntRuntimeDataSerializer serializer ) {
        byteBuf.writeByte(getPaletteHeader(this.bitArray.getVersion(), true));

        for(int word : this.bitArray.getWords()) byteBuf.writeIntLE(word);

        this.bitArray.writeSizeToNetwork(byteBuf, this.palette.size());
        for(int value : this.palette) {
            VarInts.writeInt(byteBuf, serializer.serialize(value));
        }
    }

    public void readFromNetwork( ByteBuf byteBuf, IntRuntimeDataDeserializer deserializer ) {
        byte header = byteBuf.readByte();

        final BitArrayVersion version = IntPalette.getVersionFromHeader(header);
        final int wordCount = version.getWordsForSize(SIZE);
        final int[] words = new int[wordCount];
        for(int i = 0; i < wordCount; i++) words[i] = byteBuf.readIntLE();

        this.bitArray = version.createArray(SIZE, words);
        this.palette.clear();

        int size = this.bitArray.readSizeFromNetwork( byteBuf );
        for ( int i = 0; i < size; i++ ) {
            this.palette.add( deserializer.deserialize( VarInts.readInt( byteBuf ) ) );
        }
    }

    public void readFromStoragePersistent( ByteBuf byteBuf, IntPersistentDataDeserializer deserializer ) {
        final short header = byteBuf.readUnsignedByte();

        if(!isPersistent(header)) throw new IllegalArgumentException("Palette is not persistent!");

        final BitArrayVersion version = IntPalette.getVersionFromHeader(header);
        final int wordCount = version.getWordsForSize(SIZE);
        final int[] words = new int[wordCount];
        for(int i = 0; i < wordCount; i++) words[i] = byteBuf.readIntLE();

        this.bitArray = version.createArray(SIZE, words);
        this.palette.clear();

        final int paletteSize = byteBuf.readIntLE();
        try(final ByteBufInputStream bufInputStream = new ByteBufInputStream(byteBuf);
            final NBTInputStream inputStream = NbtUtils.createReaderLE(bufInputStream)) {
            for(int i = 0; i < paletteSize; i++)
                this.palette.add(deserializer.deserialize((NbtMap) inputStream.readTag()));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeToStoragePersistent( ByteBuf byteBuf, IntPersistentDataSerializer serializer ) {
        byteBuf.writeByte(IntPalette.getPaletteHeader(this.bitArray.getVersion(), false));

        for(int word : this.bitArray.getWords()) byteBuf.writeIntLE(word);

        byteBuf.writeIntLE(this.palette.size());

        try(final ByteBufOutputStream bufOutputStream = new ByteBufOutputStream(byteBuf);
            final NBTOutputStream outputStream = NbtUtils.createWriterLE(bufOutputStream)) {
            for(int value : this.palette) outputStream.writeTag(serializer.serialize(value));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeToStorageRuntime( ByteBuf byteBuf, IntRuntimeDataSerializer serializer, IntPalette last ) {
        if(last != null && last.palette.equals(this.palette)) {
            byteBuf.writeByte(IntPalette.getCopyLastFlagHeader());
            return;
        }

        if(this.isEmpty()) {
            byteBuf.writeByte(IntPalette.getPaletteHeader(BitArrayVersion.V0, true));
            byteBuf.writeIntLE(serializer.serialize(this.palette.get(0)));
            return;
        }

        byteBuf.writeByte(IntPalette.getPaletteHeader(this.bitArray.getVersion(), true));

        for(int word : this.bitArray.getWords()) byteBuf.writeIntLE(word);

        byteBuf.writeIntLE(this.palette.size());
        for(int value : this.palette) byteBuf.writeIntLE(serializer.serialize(value));
    }

    public void readFromStorageRuntime( ByteBuf byteBuf, IntRuntimeDataDeserializer deserializer, IntPalette last ) {
        final short header = byteBuf.readUnsignedByte();

        if( isPersistent(header)) throw new IllegalArgumentException("Palette is persistent!");

        if(IntPalette.hasCopyLastFlag(header)) {
            last.copyTo(this);
            return;
        }

        final BitArrayVersion version = IntPalette.getVersionFromHeader(header);
        if(version == BitArrayVersion.V0) {
            this.bitArray = version.createArray(SIZE, null);
            this.palette.clear();
            this.palette.add(deserializer.deserialize(byteBuf.readIntLE()));

            this.onResize(BitArrayVersion.V2);
            return;
        }

        final int wordCount = version.getWordsForSize(SIZE);
        final int[] words = new int[wordCount];
        for(int i = 0; i < wordCount; i++) words[i] = byteBuf.readIntLE();

        this.bitArray = version.createArray(SIZE, words);
        this.palette.clear();

        final int paletteSize = byteBuf.readIntLE();
        for(int i = 0; i < paletteSize; i++) this.palette.add(deserializer.deserialize(byteBuf.readIntLE()));
    }

    private void onResize( BitArrayVersion version ) {
        final BitArray newBitArray = version.createArray( SIZE );
        for ( int i = 0; i < SIZE; i++ )
            newBitArray.set( i, this.bitArray.get( i ) );

        this.bitArray = newBitArray;
    }

    public int paletteIndexFor( int value ) {
        int index = this.palette.indexOf( value );
        if ( index != -1 ) return index;

        index = this.palette.size();
        this.palette.add( value );

        final BitArrayVersion version = this.bitArray.getVersion();
        if ( index > version.maxEntryValue ) {
            final BitArrayVersion next = version.next;
            if ( next != null ) this.onResize( next );
        }

        return index;
    }

    public boolean isEmpty() {
        if ( this.palette.size() == 1 ) return true;

        for ( int word : this.bitArray.getWords() )
            if ( Integer.toUnsignedLong( word ) != 0L )
                return false;

        return true;
    }

    public void copyTo( IntPalette palette ) {
        palette.bitArray = this.bitArray.copy();
        palette.palette.clear();
        palette.palette.addAll( this.palette );
    }

}
