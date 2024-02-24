package org.jukeboxmc.server.block.palette.bitarray

import io.netty.buffer.ByteBuf

class SingletonBitArray : BitArray {

    override fun set(index: Int, value: Int) {

    }

    override fun get(index: Int): Int {
        return 0
    }

    override fun writeSizeToNetwork(buffer: ByteBuf?, size: Int) {

    }

    override fun getSize(): Int {
        return 1
    }

    override fun getWords(): IntArray {
        return IntArray(0)
    }

    override fun getVersion(): BitArrayVersion {
        return BitArrayVersion.V0
    }

    override fun copy(): BitArray {
        return SingletonBitArray()
    }
}