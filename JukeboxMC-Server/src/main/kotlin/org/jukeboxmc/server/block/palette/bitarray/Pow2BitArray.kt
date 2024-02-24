package org.jukeboxmc.server.block.palette.bitarray

import org.jukeboxmc.server.util.Utils

class Pow2BitArray(
    private val version: BitArrayVersion,
    private val size: Int,
    private val words: IntArray
) : BitArray {

    init {
        val expectedWordsLength: Int = Utils.ceil(size.toFloat() / version.entriesPerWord)
        require(words.size == expectedWordsLength) {
            "Invalid length given for storage, got: " + words.size +
                    " but expected: " + expectedWordsLength
        }
    }

    override fun set(index: Int, value: Int) {
        val bitIndex = index * version.bits
        val arrayIndex = bitIndex shr 5
        val offset = bitIndex and 31
        words[arrayIndex] =
            words[arrayIndex] and (version.maxEntryValue shl offset).inv() or (value and version.maxEntryValue shl offset)
    }

    override fun get(index: Int): Int {
        val bitIndex = index * version.bits
        val arrayIndex = bitIndex shr 5
        val wordOffset = bitIndex and 31
        return words[arrayIndex] ushr wordOffset and version.maxEntryValue
    }

    override fun getSize(): Int {
        return this.size
    }

    override fun getWords(): IntArray {
        return this.words
    }

    override fun getVersion(): BitArrayVersion {
        return this.version
    }

    override fun copy(): BitArray {
        return PaddedBitArray(version, size, words.copyOf(words.size))
    }
}