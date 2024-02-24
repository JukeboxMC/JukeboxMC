package org.jukeboxmc.server.block.palette.bitarray

import org.jukeboxmc.server.util.Utils

class PaddedBitArray(
    private val version: BitArrayVersion,
    private val size: Int,
    private val words: IntArray?
) : BitArray {

    init {
        val expectedWordsLength: Int = Utils.ceil(size.toFloat() / version.entriesPerWord)
        require((words?.size ?: 0) == expectedWordsLength) {
            "Invalid length given for storage, got: " + (words?.size ?: 0) +
                    " but expected: " + expectedWordsLength
        }
    }

    override fun set(index: Int, value: Int) {
        val arrayIndex = index / version.entriesPerWord
        val offset = index % version.entriesPerWord * version.bits
        words!![arrayIndex] =
            words[arrayIndex] and (version.maxEntryValue shl offset).inv() or (value and version.maxEntryValue shl offset)
    }

    override fun get(index: Int): Int {
        val arrayIndex = index / version.entriesPerWord
        val offset = index % version.entriesPerWord * version.bits
        return words!![arrayIndex] ushr offset and version.maxEntryValue
    }

    override fun getSize(): Int {
        return this.size
    }

    override fun getWords(): IntArray? {
        return this.words
    }

    override fun getVersion(): BitArrayVersion {
        return this.version
    }

    override fun copy(): BitArray {
        return PaddedBitArray(version, size, words?.let { words.copyOf(it.size) })
    }
}