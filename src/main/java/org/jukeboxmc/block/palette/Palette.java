package org.jukeboxmc.block.palette;

import com.nukkitx.nbt.NBTInputStream;
import com.nukkitx.nbt.NBTOutputStream;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.network.VarInts;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jukeboxmc.block.palette.bitarray.BitArray;
import org.jukeboxmc.block.palette.bitarray.BitArrayVersion;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Getter
@EqualsAndHashCode
public class Palette<V> {

    public static final int SIZE = 4096;

    private final @NotNull List<V> palette;
    private BitArray bitArray;

    public Palette(V first) {
        this(first, BitArrayVersion.V2);
    }

    public Palette(V first, @NotNull BitArrayVersion version) {
        this.bitArray = version.createArray(SIZE);
        this.palette = new ReferenceArrayList<>(16);
        this.palette.add(first);
    }

    private static int getPaletteHeader(@NotNull BitArrayVersion version, boolean runtime) {
        return (version.bits << 1) | (runtime ? 1 : 0);
    }

    private static @Nullable BitArrayVersion getVersionFromHeader(short header) {
        return BitArrayVersion.get(header >> 1, true);
    }

    private static boolean hasCopyLastFlag(short header) {
        return (header >> 1) == 0x7F;
    }

    private static int getCopyLastFlagHeader() {
        return (0x7F << 1) | 1;
    }

    private static boolean isPersistent(short header) {
        return (header & 1) == 0;
    }

    public V get(int index) {
        return this.palette.get(this.bitArray.get(index));
    }

    public void set(int index, V value) {
        final int paletteIndex = this.paletteIndexFor(value);
        this.bitArray.set(index, paletteIndex);
    }

    public void writeToNetwork(@NotNull ByteBuf byteBuf, @NotNull RuntimeDataSerializer<V> serializer) {
        byteBuf.writeByte(getPaletteHeader(this.bitArray.getVersion(), true));

        for(int word : this.bitArray.getWords()) byteBuf.writeIntLE(word);

        this.bitArray.writeSizeToNetwork(byteBuf, this.palette.size());
        for(V value : this.palette) VarInts.writeInt(byteBuf, serializer.serialize(value));
    }

    public void writeToNetwork(@NotNull ByteBuf byteBuf, @NotNull RuntimeDataSerializer<V> serializer, @Nullable Palette<V> last) {
        if(last != null && last.palette.equals(this.palette)) {
            byteBuf.writeByte(Palette.getCopyLastFlagHeader());
            return;
        }

        if(this.isEmpty()) {
            byteBuf.writeByte(Palette.getPaletteHeader(BitArrayVersion.V0, true));
            byteBuf.writeIntLE(serializer.serialize(this.palette.get(0)));
            return;
        }

        byteBuf.writeByte(getPaletteHeader(this.bitArray.getVersion(), true));

        for(int word : this.bitArray.getWords()) byteBuf.writeIntLE(word);

        this.bitArray.writeSizeToNetwork(byteBuf, this.palette.size());
        for(V value : this.palette) VarInts.writeInt(byteBuf, serializer.serialize(value));
    }

    public void readFromNetwork(@NotNull ByteBuf byteBuf, @NotNull RuntimeDataDeserializer<V> deserializer) {
        final short header = byteBuf.readUnsignedByte();

        final BitArrayVersion version = Objects.requireNonNull(Palette.getVersionFromHeader(header));
        final int wordCount = version.getWordsForSize(SIZE);
        final int[] words = new int[wordCount];
        for(int i = 0; i < wordCount; i++) words[i] = byteBuf.readIntLE();

        this.bitArray = version.createArray(SIZE, words);
        this.palette.clear();

        final int size = this.bitArray.readSizeFromNetwork(byteBuf);
        for(int i = 0; i < size; i++) this.palette.add(deserializer.deserialize(VarInts.readInt(byteBuf)));
    }

    public void writeToStoragePersistent(@NotNull ByteBuf byteBuf, @NotNull PersistentDataSerializer<V> serializer) {
        byteBuf.writeByte(Palette.getPaletteHeader(this.bitArray.getVersion(), false));

        for(int word : this.bitArray.getWords()) byteBuf.writeIntLE(word);

        byteBuf.writeIntLE(this.palette.size());

        try(final ByteBufOutputStream bufOutputStream = new ByteBufOutputStream(byteBuf);
            final NBTOutputStream outputStream = NbtUtils.createWriterLE(bufOutputStream)) {
            for(V value : this.palette) outputStream.writeTag(serializer.serialize(value));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeToStorageRuntime(@NotNull ByteBuf byteBuf, @NotNull RuntimeDataSerializer<V> serializer, @Nullable Palette<V> last) {
        if(last != null && last.palette.equals(this.palette)) {
            byteBuf.writeByte(Palette.getCopyLastFlagHeader());
            return;
        }

        if(this.isEmpty()) {
            byteBuf.writeByte(Palette.getPaletteHeader(BitArrayVersion.V0, true));
            byteBuf.writeIntLE(serializer.serialize(this.palette.get(0)));
            return;
        }

        byteBuf.writeByte(Palette.getPaletteHeader(this.bitArray.getVersion(), true));

        for(int word : this.bitArray.getWords()) byteBuf.writeIntLE(word);

        byteBuf.writeIntLE(this.palette.size());
        for(V value : this.palette) byteBuf.writeIntLE(serializer.serialize(value));
    }

    public void readFromStoragePersistent(@NotNull ByteBuf byteBuf, @NotNull PersistentDataDeserializer<V> deserializer) {
        final short header = byteBuf.readUnsignedByte();

        final BitArrayVersion version = Objects.requireNonNull(Palette.getVersionFromHeader(header));
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

    public void readFromStorageRuntime(@NotNull ByteBuf byteBuf, @NotNull RuntimeDataDeserializer<V> deserializer, @Nullable Palette<V> last) {
        final short header = byteBuf.readUnsignedByte();

        if(Palette.hasCopyLastFlag(header)) {
            Objects.requireNonNull(last).copyTo(this);
            return;
        }

        final BitArrayVersion version = Objects.requireNonNull(Palette.getVersionFromHeader(header));
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

    private void onResize(@NotNull BitArrayVersion version) {
        final BitArray newBitArray = version.createArray(SIZE);
        for(int i = 0; i < SIZE; i++)
            newBitArray.set(i, this.bitArray.get(i));

        this.bitArray = newBitArray;
    }

    public int paletteIndexFor(V value) {
        int index = this.palette.indexOf(value);
        if(index != -1) return index;

        index = this.palette.size();
        this.palette.add(value);

        final BitArrayVersion version = this.bitArray.getVersion();
        if(index > version.maxEntryValue) {
            final BitArrayVersion next = version.next;
            if(next != null) this.onResize(next);
        }

        return index;
    }

    public boolean isEmpty() {
        if(this.palette.size() == 1) return true;

        for(int word : this.bitArray.getWords())
            if(Integer.toUnsignedLong(word) != 0L)
                return false;

        return true;
    }

    public void copyTo(@NotNull Palette<V> palette) {
        palette.bitArray = this.bitArray.copy();
        palette.palette.clear();
        palette.palette.addAll(this.palette);
    }

}
