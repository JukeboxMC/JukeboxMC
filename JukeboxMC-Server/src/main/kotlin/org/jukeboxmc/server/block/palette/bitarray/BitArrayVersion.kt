package org.jukeboxmc.server.block.palette.bitarray

import org.jukeboxmc.server.util.Utils

enum class BitArrayVersion(var bits: Byte, var entriesPerWord: Byte, var next: BitArrayVersion?) {
    V16(16, 2, null),
    V8(8, 4, V16),
    V6(6, 5, V8), // 2 bit padding
    V5(5, 6, V6), // 2 bit padding
    V4(4, 8, V5),
    V3(3, 10, V4), // 2 bit padding
    V2(2, 16, V3),
    V1(1, 32, V2),
    V0(0, 0, V1);

    var maxEntryValue = 0

    init {
        this.maxEntryValue = (1 shl this.bits.toInt()) - 1
    }

    fun getWordsForSize(size: Int): Int {
        return Utils.ceil(size.toFloat() / entriesPerWord)
    }

    fun createArray(size: Int): BitArray? {
        return this.createArray(size, IntArray(Utils.ceil(size.toFloat() / entriesPerWord)))
    }

    open fun createArray(size: Int, words: IntArray?): BitArray? {
        return if (this == V3 || this == V5 || this == V6) PaddedBitArray(
            this,
            size,
            words
        ) else if (this == V0) SingletonBitArray() else words?.let { Pow2BitArray(this, size, it) }
    }

    companion object {
        fun get(version: Int, read: Boolean): BitArrayVersion? {
            for (ver in entries) if (!read && ver.entriesPerWord <= version || read && ver.bits.toInt() == version) return ver
            if (version == 0x7F && read) return null
            throw IllegalArgumentException("Invalid palette version: $version")
        }

        fun forBitsCeil(bits: Int): BitArrayVersion? {
            for (i in entries.toTypedArray().indices.reversed()) {
                val version: BitArrayVersion = entries[i]
                if (version.bits >= bits) return version
            }
            return null
        }
    }
}

