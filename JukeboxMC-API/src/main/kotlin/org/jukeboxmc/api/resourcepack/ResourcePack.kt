package org.jukeboxmc.api.resourcepack

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

class ResourcePack(
    private val file: File,
    private val name: String,
    private val uuid: String,
    private val version: String,
    private val size: Long,
    private val hash: ByteArray,
    private var chunk: ByteArray
) {

    fun getChunk(offset: Int, length: Int): ByteArray {
        chunk = if (size - offset > length) {
            ByteArray(length)
        } else {
            ByteArray((size - offset).toInt())
        }
        try {
            FileInputStream(file).use { fileInputStream ->
                fileInputStream.skip(offset.toLong())
                fileInputStream.read(chunk)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return chunk
    }

    fun getName(): String {
        return this.name
    }

    fun getUuid(): UUID {
        return UUID.fromString(this.uuid)
    }

    fun getVersion(): String {
        return this.version
    }

    fun getSize(): Long {
        return this.size
    }

    fun getHash(): ByteArray {
        return this.hash
    }
}