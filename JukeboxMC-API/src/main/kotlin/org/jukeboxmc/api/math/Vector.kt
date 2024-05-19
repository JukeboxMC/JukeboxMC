package org.jukeboxmc.api.math

import lombok.SneakyThrows
import org.apache.commons.math3.util.FastMath
import org.jukeboxmc.api.world.Dimension
import java.util.*
import kotlin.math.roundToInt
import kotlin.math.sqrt


open class Vector : Cloneable {

    private var x: Float = 0F
    private var y: Float = 0F
    private var z: Float = 0F
    private var dimension: Dimension = Dimension.OVERWORLD

    constructor()

    constructor(x: Float, y: Float, z: Float, dimension: Dimension) {
        this.x = x
        this.y = y
        this.z = z
        this.dimension = dimension
    }

    constructor(x: Int, y: Int, z: Int, dimension: Dimension) {
        this.x = x.toFloat()
        this.y = y.toFloat()
        this.z = z.toFloat()
        this.dimension = dimension
    }

    constructor(x: Float, y: Float, z: Float) {
        this.x = x
        this.y = y
        this.z = z
    }

    constructor(x: Int, y: Int, z: Int) {
        this.x = x.toFloat()
        this.y = y.toFloat()
        this.z = z.toFloat()
    }

    fun up(): Vector {
        return Vector(x, y + 1, z)
    }

    fun down(): Vector {
        return Vector(x, y - 1, z)
    }

    fun north(): Vector {
        return Vector(x, y, z - 1)
    }

    fun east(): Vector {
        return Vector(x + 1, y, z)
    }

    fun south(): Vector {
        return Vector(x, y, z + 1)
    }

    fun west(): Vector {
        return Vector(x - 1, y, z)
    }

    fun setVector(x: Int, y: Int, z: Int) {
        this.x = x.toFloat()
        this.y = y.toFloat()
        this.z = z.toFloat()
    }

    fun getX(): Float {
        return this.x
    }

    fun setX(x: Float) {
        this.x = x
    }

    fun getY(): Float {
        return this.y
    }

    fun setY(y: Float) {
        this.y = y
    }

    fun getZ(): Float {
        return this.z
    }

    fun setZ(z: Float) {
        this.z = z
    }

    fun getBlockX(): Int {
        return FastMath.floor(x.toDouble()).toInt()
    }

    fun getBlockY(): Int {
        return FastMath.floor(y.toDouble()).toInt()
    }

    fun getBlockZ(): Int {
        return FastMath.floor(z.toDouble()).toInt()
    }

    fun getDimension(): Dimension {
        return this.dimension
    }

    fun getChunkX(): Int {
        return getBlockX() shr 4
    }

    fun getChunkZ(): Int {
        return getBlockZ() shr 4
    }

    fun getVectorWhenXIsOnLine(other: Vector, x: Float): Vector? {
        val xDiff = other.x - this.x
        val yDiff = other.y - y
        val zDiff = other.z - z
        val f = (x - this.x) / xDiff
        return if (f in 0f..1f) Vector(this.x + xDiff * f, y + yDiff * f, z + zDiff * f, dimension) else null
    }

    fun getVectorWhenYIsOnLine(other: Vector, y: Float): Vector? {
        val xDiff = other.x - x
        val yDiff = other.y - this.y
        val zDiff = other.z - z
        val f = (y - this.y) / yDiff
        return if (f in 0f..1f) Vector(x + xDiff * f, this.y + yDiff * f, z + zDiff * f, dimension) else null
    }

    fun getVectorWhenZIsOnLine(other: Vector, z: Float): Vector? {
        val xDiff = other.x - x
        val yDiff = other.y - y
        val zDiff = other.z - this.z
        val f = (z - this.z) / zDiff
        return if (f in 0f..1f) Vector(x + xDiff * f, y + yDiff * f, this.z + zDiff * f, dimension) else null
    }

    open fun add(x: Float, y: Float, z: Float): Vector {
        return Vector(this.x + x, this.y + y, this.z + z, dimension)
    }

    open fun subtract(x: Float, y: Float, z: Float): Vector {
        return Vector(this.x - x, this.y - y, this.z - z, dimension)
    }

    open fun multiply(x: Float, y: Float, z: Float): Vector {
        return Vector(this.x * x, this.y * y, this.z * z, dimension)
    }

    open fun divide(x: Float, y: Float, z: Float): Vector {
        return Vector(this.x / x, this.y / y, this.z / z, dimension)
    }

    fun normalize(): Vector {
        val squaredLength = squaredLength()
        return if (squaredLength < 1.0E-4) Vector(0f, 0f, 0f) else divide(squaredLength, squaredLength, squaredLength)
    }

    fun distance(vector: Vector): Float {
        return sqrt(distanceSquared(vector).toDouble()).toFloat()
    }

    fun distanceSquared(vector: Vector): Float {
        return (FastMath.pow((x - vector.x).toDouble(), 2) + FastMath.pow(
            (y - vector.y).toDouble(),
            2
        ) + FastMath.pow((z - vector.z).toDouble(), 2)).toFloat()
    }

    fun squaredLength(): Float {
        return FastMath.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val vector = other as Vector
        return vector.x.compareTo(x) == 0 && vector.y.compareTo(y) == 0 && vector.z.compareTo(z) == 0 && dimension === vector.dimension
    }

    override fun hashCode(): Int {
        return Objects.hash(x, y, z, dimension)
    }

    @SneakyThrows
    public override fun clone(): Vector {
        val clone = super.clone() as Vector
        clone.x = x
        clone.y = y
        clone.z = z
        return clone
    }

    fun getIntermediateWithXValue(var1: Vector, x: Float): Vector? {
        val var4: Float = var1.getX() - this.getX()
        val var6: Float = var1.getY() - this.getY()
        val var8: Float = var1.getZ() - this.getZ()
        return if (var4 * var4 < 1.0000000116860974E-7) {
            null
        } else {
            val var10: Float = (x - this.getX()) / var4
            if (!(var10 < 0.0) && !(var10 > 1.0)) Vector(
                this.getX() + var4 * var10,
                this.getY() + var6 * var10,
                this.getZ() + var8 * var10
            ) else null
        }
    }

    fun getIntermediateWithYValue(var1: Vector, y: Float): Vector? {
        val var4: Float = var1.getX() - this.getX()
        val var6: Float = var1.getY() - this.getY()
        val var8: Float = var1.getZ() - this.getZ()
        return if (var6 * var6 < 1.0000000116860974E-7) {
            null
        } else {
            val var10: Float = (y - this.getY()) / var6
            if (!(var10 < 0.0) && !(var10 > 1.0)) Vector(
                this.getX() + var4 * var10,
                this.getY() + var6 * var10,
                this.getZ() + var8 * var10
            ) else null
        }
    }

    fun getIntermediateWithZValue(var1: Vector, z: Float): Vector? {
        val var4: Float = var1.getX() - this.getX()
        val var6: Float = var1.getY() - this.getY()
        val var8: Float = var1.getZ() - this.getZ()
        return if (var8 * var8 < 1.0000000116860974E-7) {
            null
        } else {
            val var10: Float = (z - this.getZ()) / var8
            if (!(var10 < 0.0) && !(var10 > 1.0)) Vector(
                this.getX() + var4 * var10,
                this.getY() + var6 * var10,
                this.getZ() + var8 * var10
            ) else null
        }
    }

    override fun toString(): String {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", dimension=" + dimension +
                '}'
    }

    fun round(): Vector {
        return Vector(x.roundToInt(), y.roundToInt(), z.roundToInt())
    }

    companion object {
        fun zero(): Vector {
            return Vector(0, 0, 0)
        }
    }
}

