package org.jukeboxmc.server.util

import io.netty.buffer.ByteBuf
import org.jukeboxmc.api.world.Dimension
import org.jukeboxmc.server.world.chunk.JukeboxChunk
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToInt

class Utils {

    companion object {

        fun fromHashX(hash: Long): Int {
            return (hash shr 32).toInt()
        }

        fun fromHashZ(hash: Long): Int {
            return hash.toInt()
        }

        fun blockHash(x: Int, y: Int, z: Int, dimension: Dimension): Long {
            if (y >= dimension.getMinY() && y <= dimension.getMaxY()) {
                return x.toLong() and 134217727L shl 37 or ((max(
                    min(y, dimension.getMaxY()),
                    dimension.getMinY()
                ).toLong() + 64) shl 28) or (z.toLong() and 0xFFFFFFFL)
            }
            return -1
        }

        fun ceil(value: Float): Int {
            val truncated: Int = value.toInt()
            return if (value > truncated) truncated + 1 else truncated
        }

        fun toLong(x: Int, z: Int): Long {
            return x.toLong() shl 32 or (z.toLong() and 0xffffffffL)
        }

        fun clamp(value: Int, min: Int, max: Int): Int {
            return if (value < min) min else value.coerceAtMost(max)
        }

        fun indexOf(x: Int, y: Int, z: Int): Int {
            return (x and 15 shl 8) + (z and 15 shl 4) + (y and 15)
        }

        fun round(value: Double, precision: Int): Double {
            val pow = 10.0.pow(precision.toDouble())
            return (value * pow).roundToInt().toDouble() / pow
        }

        fun array(buffer: ByteBuf): ByteArray {
            val array = ByteArray(buffer.readableBytes())
            buffer.readBytes(array)
            return array
        }

        fun ceilOrRound(value: Float): Int {
            val var2 = value.toInt()
            return if (value > var2.toDouble()) {
                var2 + 1
            } else {
                var2
            }
        }

        fun getKey(chunk: JukeboxChunk, encoded: Byte): ByteArray {
            val chunkX = chunk.getX()
            val chunkZ = chunk.getZ()
            return if (chunk.getDimension() == Dimension.OVERWORLD) {
                byteArrayOf(
                    (chunkX and 0xff).toByte(),
                    (chunkX ushr 8 and 0xff).toByte(),
                    (chunkX ushr 16 and 0xff).toByte(),
                    (chunkX ushr 24 and 0xff).toByte(),
                    (chunkZ and 0xff).toByte(),
                    (chunkZ ushr 8 and 0xff).toByte(),
                    (chunkZ ushr 16 and 0xff).toByte(),
                    (chunkZ ushr 24 and 0xff).toByte(),
                    encoded
                )
            } else {
                val dimensionId = chunk.getDimension().ordinal
                byteArrayOf(
                    (chunkX and 0xff).toByte(),
                    (chunkX ushr 8 and 0xff).toByte(),
                    (chunkX ushr 16 and 0xff).toByte(),
                    (chunkX ushr 24 and 0xff).toByte(),
                    (chunkZ and 0xff).toByte(),
                    (chunkZ ushr 8 and 0xff).toByte(),
                    (chunkZ ushr 16 and 0xff).toByte(),
                    (chunkZ ushr 24 and 0xff).toByte(),
                    (dimensionId and 0xff).toByte(),
                    (dimensionId ushr 8 and 0xff).toByte(),
                    (dimensionId ushr 16 and 0xff).toByte(),
                    (dimensionId ushr 24 and 0xff).toByte(),
                    encoded
                )
            }
        }

        fun getSubChunkKey(chunk: JukeboxChunk, key: Byte, subChunk: Byte): ByteArray {
            val dimension = chunk.getDimension()
            val chunkX = chunk.getX()
            val chunkZ = chunk.getZ()
            return if (dimension == Dimension.OVERWORLD) {
                byteArrayOf(
                    (chunkX and 0xff).toByte(),
                    (chunkX ushr 8 and 0xff).toByte(),
                    (chunkX ushr 16 and 0xff).toByte(),
                    (chunkX ushr 24 and 0xff).toByte(),
                    (chunkZ and 0xff).toByte(),
                    (chunkZ ushr 8 and 0xff).toByte(),
                    (chunkZ ushr 16 and 0xff).toByte(),
                    (chunkZ ushr 24 and 0xff).toByte(),
                    key,
                    subChunk
                )
            } else {
                val dimensionId = dimension.ordinal
                byteArrayOf(
                    (chunkX and 0xff).toByte(),
                    (chunkX ushr 8 and 0xff).toByte(),
                    (chunkX ushr 16 and 0xff).toByte(),
                    (chunkX ushr 24 and 0xff).toByte(),
                    (chunkZ and 0xff).toByte(),
                    (chunkZ ushr 8 and 0xff).toByte(),
                    (chunkZ ushr 16 and 0xff).toByte(),
                    (chunkZ ushr 24 and 0xff).toByte(),
                    (dimensionId and 0xff).toByte(),
                    (dimensionId ushr 8 and 0xff).toByte(),
                    (dimensionId ushr 16 and 0xff).toByte(),
                    (dimensionId ushr 24 and 0xff).toByte(),
                    key,
                    subChunk
                )
            }
        }
    }
}