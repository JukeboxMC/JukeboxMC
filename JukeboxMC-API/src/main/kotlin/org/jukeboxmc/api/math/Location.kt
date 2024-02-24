package org.jukeboxmc.api.math

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.world.Biome
import org.jukeboxmc.api.world.Dimension
import org.jukeboxmc.api.world.World
import org.jukeboxmc.api.world.chunk.Chunk
import kotlin.math.cos
import kotlin.math.sin


class Location : Vector, Cloneable {

    private var world: World
    private var yaw: Float
    private var pitch: Float

    constructor(world: World, x: Float, y: Float, z: Float, yaw: Float, pitch: Float, dimension: Dimension) : super(x, y, z, dimension) {
        this.world = world
        this.yaw = yaw
        this.pitch = pitch
    }

    constructor(world: World, x: Float, y: Float, z: Float, dimension: Dimension) : super(x, y, z, dimension){
        this.world = world
        this.yaw = 0F
        this.pitch = 0F
    }

    constructor(world: World, x: Float, y: Float, z: Float) : super(x, y, z, Dimension.OVERWORLD){
        this.world = world
        this.yaw = 0F
        this.pitch = 0F
    }

    constructor(world: World, x: Int, y: Int, z: Int, yaw: Float, pitch: Float, dimension: Dimension) : super(x, y, z, dimension){
        this.world = world
        this.yaw = yaw
        this.pitch = pitch
    }

    constructor(world: World, x: Int, y: Int, z: Int, dimension: Dimension) : super(x, y, z, dimension){
        this.world = world
        this.yaw = 0F
        this.pitch = 0F
    }

    constructor(world: World, x: Int, y: Int, z: Int) : super(x, y, z, Dimension.OVERWORLD){
        this.world = world
        this.yaw = 0F
        this.pitch = 0F
    }

    constructor(world: World, vector: Vector, yaw: Float, pitch: Float, dimension: Dimension) : super(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ(), dimension){
        this.world = world
        this.yaw = yaw
        this.pitch = pitch
    }

    constructor(world: World, vector: Vector, dimension: Dimension) : super(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ(), dimension){
        this.world = world
        this.yaw = 0F
        this.pitch = 0F
    }

    constructor(world: World, vector: Vector) : super(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ(), Dimension.OVERWORLD){
        this.world = world
        this.yaw = 0F
        this.pitch = 0F
    }

    override fun add(x: Float, y: Float, z: Float): Location {
        return Location(world, this.getX() + x, this.getY() + y, this.getZ() + z, yaw, pitch, this.getDimension())
    }

    override fun subtract(x: Float, y: Float, z: Float): Location {
        return Location(world, this.getX() - x, this.getY() - y, this.getZ() - z, yaw, pitch, this.getDimension())
    }

    override fun multiply(x: Float, y: Float, z: Float): Location {
        return Location(world, this.getX() * x, this.getY() * y, this.getZ() * z, yaw, pitch, this.getDimension())
    }

    override fun divide(x: Float, y: Float, z: Float): Location {
        return Location(world, this.getX() / x, this.getY() / y, this.getZ() / z, yaw, pitch, this.getDimension())
    }

    fun getWorld(): World {
        return world
    }

    fun getYaw(): Float {
        return this.yaw
    }

    fun setYaw(yaw: Float) {
        this.yaw = yaw
    }

    fun getPitch(): Float {
        return this.pitch
    }

    fun setPitch(pitch: Float) {
        this.pitch = pitch
    }

    fun getBlock(): Block {
        return this.world.getBlock(this)
    }

    fun getBlock(layer: Int): Block {
        return this.world.getBlock(this, layer)
    }

    fun getBiome(): Biome {
        return world.getBiome(this, this.getDimension())
    }

     fun getChunk(): Chunk? {
        return world.getChunk(getBlockX() shr 4, getBlockZ() shr 4, this.getDimension())
    }

    fun getLoadedChunk(): Chunk? {
        return world.getLoadedChunk(getBlockX() shr 4, getBlockZ() shr 4, this.getDimension())
    }

    fun getDirection(): Vector {
        val pitch = (pitch + 90) * Math.PI / 180
        val yaw = (yaw + 90) * Math.PI / 180
        val x = sin(pitch) * cos(yaw)
        val z = sin(pitch) * sin(yaw)
        val y = cos(pitch)
        return Vector(x.toFloat(), y.toFloat(), z.toFloat()).normalize()
    }

    override fun clone(): Location {
        val clone: Vector = super<Vector>.clone()
        return Location(world, clone.getX(), clone.getY(), clone.getZ())
    }

    override fun toString(): String {
        return "Location{" +
                "world=" + world.getWorldData().levelName +
                ", x=" + this.getX() +
                ", y=" + this.getY() +
                ", z=" + this.getZ() +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                ", dimension=" + this.getDimension().name +
                '}'
    }
}

