package org.jukeboxmc.server.block.palette.bitarray

import io.netty.buffer.ByteBuf
import org.cloudburstmc.protocol.common.util.VarInts


interface BitArray {

    fun set(index: Int, value: Int)

    fun get(index: Int): Int

    fun getSize(): Int

    fun getWords(): IntArray?

    fun getVersion(): BitArrayVersion

    fun copy(): BitArray

    fun writeSizeToNetwork(buffer: ByteBuf?, size: Int) {
        VarInts.writeInt(buffer, size)
    }

    fun readSizeFromNetwork(buffer: ByteBuf?): Int {
        return VarInts.readInt(buffer)
    }
}