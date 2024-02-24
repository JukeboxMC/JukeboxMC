package org.jukeboxmc.server.world.chunk

import io.netty.buffer.ByteBuf
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.world.Biome
import org.jukeboxmc.api.world.chunk.SubChunk
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.block.palette.Palette
import org.jukeboxmc.server.extensions.toJukeboxBlock
import org.jukeboxmc.server.world.chunk.serializer.NetworkIdSerializer

class JukeboxSubChunk(
    private val subChunkIndex: Int
) : SubChunk {

    private var layer: Int = 0
    private var subChunkVersion: Int = 9
    private var biomePalette: Palette<Biome> = Palette(Biome.PLAINS)
    private val blockPalette: Array<Palette<JukeboxBlock>?> = arrayOfNulls(2)

    init {
        for (layer in 0 until 2) {
            this.blockPalette[layer] = Palette(JukeboxBlock.AIR)
        }
    }

    companion object {
        const val SUBCHUNK_VERSION = 9
    }

    fun setLayer(layer: Int) {
        this.layer = layer
    }

    override fun getBlock(x: Int, y: Int, z: Int, layer: Int): JukeboxBlock {
        return this.blockPalette[layer]!!.get(this.indexOf(x, y, z)).clone()
    }

    override fun getBlock(x: Int, y: Int, z: Int): Block {
        return this.blockPalette[this.layer]!!.get(this.indexOf(x, y, z)).clone()
    }

    override fun getBlock(location: Vector, layer: Int): Block {
        return this.blockPalette[layer]!!.get(
            this.indexOf(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
            )
        ).clone()
    }

    override fun getBlock(location: Vector): Block {
        return this.blockPalette[this.layer]!!.get(
            this.indexOf(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
            )
        ).clone()
    }

    override fun setBlock(x: Int, y: Int, z: Int, layer: Int, block: Block) {
        this.blockPalette[layer]!!.set(this.indexOf(x, y, z), block.toJukeboxBlock())
    }

    override fun setBlock(x: Int, y: Int, z: Int, block: Block) {
        this.blockPalette[this.layer]!!.set(this.indexOf(x, y, z), block as JukeboxBlock)
    }

    override fun setBlock(location: Vector, layer: Int, block: Block) {
        this.blockPalette[layer]!!.set(
            this.indexOf(location.getBlockX(), location.getBlockY(), location.getBlockZ()),
            block as JukeboxBlock
        )
    }

    override fun setBlock(location: Vector, block: Block) {
        this.blockPalette[this.layer]!!.set(
            this.indexOf(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
            ), block as JukeboxBlock
        )
    }

    override fun getBiome(x: Int, y: Int, z: Int): Biome {
        return this.biomePalette.get(this.indexOf(x, y, z))
    }

    override fun getBiome(location: Vector): Biome {
        return this.biomePalette.get(this.indexOf(location.getBlockX(), location.getBlockY(), location.getBlockZ()))
    }

    override fun setBiome(x: Int, y: Int, z: Int, biome: Biome) {
        this.biomePalette.set(this.indexOf(x, y, z), biome)
    }

    override fun setBiome(location: Vector, biome: Biome) {
        this.biomePalette.set(this.indexOf(location.getBlockX(), location.getBlockY(), location.getBlockZ()), biome)
    }

    override fun isEmptySubChunk(): Boolean {
        var count = 0
        for (block in this.blockPalette) {
            for (value in block!!.getPalette()) {
                if (value.getType() != BlockType.AIR) {
                    count++
                }
            }
        }
        return count <= 0
    }

    override fun getLayer(): Int {
        return this.layer
    }

    override fun getSubChunkVersion(): Int {
        return this.subChunkVersion
    }

    override fun getSubChunkIndex(): Int {
        return this.subChunkIndex
    }

    fun setSubChunkVersion(subChunkVersion: Int) {
        this.subChunkVersion = subChunkVersion
    }

    private fun indexOf(x: Int, y: Int, z: Int): Int {
        return (x and 15 shl 8) + (z and 15 shl 4) + (y and 15)
    }

    fun getBiomePalette(): Palette<Biome> {
        return this.biomePalette
    }

    fun getBlockPalette(): Array<Palette<JukeboxBlock>?> {
        return this.blockPalette
    }

    fun writeToNetwork(byteBuf: ByteBuf) {
        byteBuf.writeByte(this.subChunkVersion)
        byteBuf.writeByte(this.blockPalette.size)
        byteBuf.writeByte(this.subChunkIndex)
        for (blockPalette in this.blockPalette) {
            blockPalette!!.writeToNetwork(byteBuf, NetworkIdSerializer())
        }
    }

}