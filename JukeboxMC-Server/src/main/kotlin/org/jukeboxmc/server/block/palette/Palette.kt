package org.jukeboxmc.server.block.palette

import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufInputStream
import io.netty.buffer.ByteBufOutputStream
import it.unimi.dsi.fastutil.objects.ReferenceArrayList
import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.nbt.NbtUtils
import org.cloudburstmc.protocol.common.util.VarInts
import org.jukeboxmc.server.block.palette.bitarray.BitArray
import org.jukeboxmc.server.block.palette.bitarray.BitArrayVersion
import java.io.IOException

class Palette<V>  {

    private val palette: MutableList<V>
    private var bitArray: BitArray

    constructor(first: V, version: BitArrayVersion) {
        this.bitArray = version.createArray(SIZE)!!
        this.palette = ReferenceArrayList(16)
        this.palette.add(first)
    }

    constructor(first: V) :this(first, BitArrayVersion.V2)

    fun getPalette(): List<V> {
        return this.palette
    }

    fun get(index: Int): V {
        return this.palette[this.bitArray.get(index)]
    }

    fun set(index: Int, value: V) {
        val paletteIndex: Int = this.paletteIndexFor(value)
        this.bitArray.set(index, paletteIndex)
    }

    fun writeToNetwork(byteBuf: ByteBuf, serializer: RuntimeDataSerializer<V>) {
        byteBuf.writeByte(getPaletteHeader(this.bitArray.getVersion(), true))
        for (word in this.bitArray.getWords()!!) byteBuf.writeIntLE(word)
        this.bitArray.writeSizeToNetwork(byteBuf, this.palette.size)
        for (value in this.palette) VarInts.writeInt(byteBuf, serializer.serialize(value))
    }

    fun writeToNetwork(byteBuf: ByteBuf, serializer: RuntimeDataSerializer<V>, last: Palette<V>?) {
        if (last != null && last.palette == this.palette) {
            byteBuf.writeByte(getCopyLastFlagHeader())
            return
        }
        if (this.isEmpty()) {
            byteBuf.writeByte(getPaletteHeader(BitArrayVersion.V0, true))
            byteBuf.writeIntLE(serializer.serialize(this.palette[0]))
            return
        }
        byteBuf.writeByte(getPaletteHeader(this.bitArray.getVersion(), true))
        for (word in this.bitArray.getWords()!!) byteBuf.writeIntLE(word)
        this.bitArray.writeSizeToNetwork(byteBuf, this.palette.size)
        for (value in this.palette) VarInts.writeInt(byteBuf, serializer.serialize(value))
    }

    fun readFromNetwork(byteBuf: ByteBuf, deserializer: RuntimeDataDeserializer<V>) {
        val header = byteBuf.readUnsignedByte()
        val version = getVersionFromHeader(header)
        val wordCount = version!!.getWordsForSize(SIZE)
        val words = IntArray(wordCount)
        for (i in 0 until wordCount) words[i] = byteBuf.readIntLE()
        this.bitArray = version.createArray(SIZE, words)!!
        this.palette.clear()
        val size = this.bitArray.readSizeFromNetwork(byteBuf)
        for (i in 0 until size) this.palette.add(deserializer.deserialize(VarInts.readInt(byteBuf)))
    }

    fun writeToStoragePersistent(byteBuf: ByteBuf, serializer: PersistentDataSerializer<V>) {
        byteBuf.writeByte(getPaletteHeader(this.bitArray.getVersion(), false))
        for (word in this.bitArray.getWords()!!) byteBuf.writeIntLE(word)
        byteBuf.writeIntLE(this.palette.size)
        try {
            ByteBufOutputStream(byteBuf).use { bufOutputStream ->
                NbtUtils.createWriterLE(bufOutputStream).use { outputStream ->
                    for (value in this.palette) outputStream.writeTag(serializer.serialize(value))
                }
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    fun writeToStorageRuntime(byteBuf: ByteBuf, serializer: RuntimeDataSerializer<V>, last: Palette<V>?) {
        if (last != null && last.palette == this.palette) {
            byteBuf.writeByte(getCopyLastFlagHeader())
            return
        }
        if (this.isEmpty()) {
            byteBuf.writeByte(getPaletteHeader(BitArrayVersion.V0, true))
            byteBuf.writeIntLE(serializer.serialize(this.palette[0]))
            return
        }
        byteBuf.writeByte(getPaletteHeader(bitArray.getVersion(), true))
        for (word in bitArray.getWords()!!) byteBuf.writeIntLE(word)
        byteBuf.writeIntLE(this.palette.size)
        for (value in this.palette) byteBuf.writeIntLE(serializer.serialize(value))
    }

    fun readFromStoragePersistent(byteBuf: ByteBuf, deserializer: PersistentDataDeserializer<V>) {
        val header = byteBuf.readUnsignedByte()
        val version = getVersionFromHeader(header)
        val wordCount = version!!.getWordsForSize(SIZE)

        if (version === BitArrayVersion.V0) {
            this.bitArray = version.createArray(SIZE, null)!!
            this.palette.clear()

            ByteBufInputStream(byteBuf).use { bufInputStream ->
                NbtUtils.createReaderLE(bufInputStream).use { inputStream ->
                    this.palette.add(deserializer.deserialize(inputStream.readTag() as NbtMap))
                }
            }
            this.onResize(BitArrayVersion.V2)
            return
        }

        val words = IntArray(wordCount)
        for (i in 0 until wordCount) words[i] = byteBuf.readIntLE()
        this.bitArray = version.createArray(SIZE, words)!!
        this.palette.clear()
        val paletteSize = byteBuf.readIntLE()
        try {
            ByteBufInputStream(byteBuf).use { bufInputStream ->
                NbtUtils.createReaderLE(bufInputStream).use { inputStream ->
                    for (i in 0 until paletteSize) this.palette.add(
                        deserializer.deserialize(inputStream.readTag() as NbtMap)
                    )
                }
            }
        } catch (e: IOException) {
            throw java.lang.RuntimeException(e)
        }
    }

    fun readFromStorageRuntime(byteBuf: ByteBuf, deserializer: RuntimeDataDeserializer<V>, last: Palette<V>) {
        val header = byteBuf.readUnsignedByte()
        if (hasCopyLastFlag(header)) {
            last.copyTo(this)
            return
        }
        val version = getVersionFromHeader(header)
        if (version === BitArrayVersion.V0) {
            this.bitArray = version.createArray(SIZE, null)!!
            this.palette.clear()
            this.palette.add(deserializer.deserialize(byteBuf.readIntLE()))
            this.onResize(BitArrayVersion.V2)
            return
        }
        val wordCount = version!!.getWordsForSize(SIZE)
        val words = IntArray(wordCount)
        for (i in 0 until wordCount) words[i] = byteBuf.readIntLE()
        this.bitArray = version.createArray(SIZE, words)!!
        this.palette.clear()
        val paletteSize = byteBuf.readIntLE()
        for (i in 0 until paletteSize) this.palette.add(deserializer.deserialize(byteBuf.readIntLE()))
    }

    fun paletteIndexFor(value: V): Int {
        var index = this.palette.indexOf(value)
        if (index != -1) return index
        index = this.palette.size
        this.palette.add(value)
        val version = this.bitArray.getVersion()
        if (index > version.maxEntryValue) {
            val next = version.next
            if (next != null) this.onResize(next)
        }
        return index
    }

    private fun onResize(version: BitArrayVersion) {
        val newBitArray = version.createArray(SIZE)
        for (i in 0 until SIZE) newBitArray?.set(i, this.bitArray.get(i))
        if (newBitArray != null) {
            this.bitArray = newBitArray
        }
    }

    fun isEmpty(): Boolean {
        if (this.palette.size == 1) return true
        for (word in this.bitArray.getWords()!!) if (Integer.toUnsignedLong(word) != 0L) return false
        return true
    }

    fun copyTo(palette: Palette<V>) {
        palette.bitArray = this.bitArray.copy()
        palette.palette.clear()
        palette.palette.addAll(this.palette)
    }

    companion object {
        const val SIZE = 4096

        private fun getPaletteHeader(version: BitArrayVersion, runtime: Boolean): Int {
            return (version.bits.toInt() shl 1) or if (runtime) 1 else 0
        }

        private fun getVersionFromHeader(header: Short): BitArrayVersion? {
            return BitArrayVersion.get(header.toInt() shr 1, true)
        }

        private fun hasCopyLastFlag(header: Short): Boolean {
            return header.toInt() shr 1 == 0x7F
        }

        private fun getCopyLastFlagHeader(): Int {
            return 0x7F shl 1 or 1
        }

        private fun isPersistent(header: Short): Boolean {
            return header.toInt() and 1 == 0
        }
    }
}
