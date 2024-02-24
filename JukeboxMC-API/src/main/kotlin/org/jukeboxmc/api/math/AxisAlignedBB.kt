package org.jukeboxmc.api.math

class AxisAlignedBB(
    private var minX: Float,
    private var minY: Float,
    private var minZ: Float,
    private var maxX: Float,
    private var maxY: Float,
    private var maxZ: Float
) : Cloneable {

    fun setBounds(minX: Float, minY: Float, minZ: Float, maxX: Float, maxY: Float, maxZ: Float): AxisAlignedBB {
        this.minX = minX
        this.minY = minY
        this.minZ = minZ
        this.maxX = maxX
        this.maxY = maxY
        this.maxZ = maxZ
        return this
    }

    fun setBounds(other: AxisAlignedBB): AxisAlignedBB {
        this.minX = other.minX
        this.minY = other.minY
        this.minZ = other.minZ
        this.maxX = other.maxX
        this.maxY = other.maxY
        this.maxZ = other.maxZ
        return this
    }

    fun addCoordinates(x: Float, y: Float, z: Float): AxisAlignedBB {
        var minX = this.minX
        var minY = this.minY
        var minZ = this.minZ
        var maxX = this.maxX
        var maxY = this.maxY
        var maxZ = this.maxZ

        if (x < 0) {
            minX += x
        } else if (x > 0) {
            maxX += x
        }

        if (y < 0) {
            minY += y
        } else if (y > 0) {
            maxY += y
        }

        if (z < 0) {
            minZ += z
        } else if (z > 0) {
            maxZ += z
        }

        return AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ)
    }

    fun grow(x: Float, y: Float, z: Float): AxisAlignedBB {
        return AxisAlignedBB(
            this.minX - x, this.minY - y, this.minZ - z,
            this.maxX + x, this.maxY + y, this.maxZ + z
        )
    }

    fun expand(x: Float, y: Float, z: Float): AxisAlignedBB {
        this.minX -= x
        this.minY -= y
        this.minZ -= z
        this.maxX += x
        this.maxY += y
        this.maxZ += z
        return this
    }

    fun offset(x: Float, y: Float, z: Float): AxisAlignedBB {
        this.minX += x
        this.minY += y
        this.minZ += z
        this.maxX += x
        this.maxY += y
        this.maxZ += z
        return this
    }

    fun shrink(x: Float, y: Float, z: Float): AxisAlignedBB {
        return AxisAlignedBB(
            this.minX + x, this.minY + y, this.minZ + z,
            this.maxX - x, this.maxY - y, this.maxZ - z
        )
    }

    fun contract(x: Float, y: Float, z: Float): AxisAlignedBB {
        this.minX += x
        this.minY += y
        this.minZ += z
        this.maxX -= x
        this.maxY -= y
        this.maxZ -= z
        return this
    }

    fun getOffsetBoundingBox(x: Float, y: Float, z: Float): AxisAlignedBB {
        return AxisAlignedBB(
            this.minX + x, this.minY + y, this.minZ + z,
            this.maxX + x, this.maxY + y, this.maxZ + z
        )
    }

    fun calculateXOffset(bb: AxisAlignedBB, x: Float): Float {
        var xX = x
        if (bb.maxY <= this.minY || bb.minY >= this.maxY) {
            return xX
        }

        if (bb.maxZ <= this.minZ || bb.minZ >= this.maxZ) {
            return xX
        }

        if (xX > 0 && bb.maxX <= this.minX) {
            val x1 = this.minX - bb.maxX
            if (x1 < xX) {
                xX = x1
            }
        }

        if (xX < 0 && bb.minX >= this.maxX) {
            val x2 = this.maxX - bb.minX
            if (x2 > xX) {
                xX = x2
            }
        }

        return xX
    }

    fun calculateYOffset(bb: AxisAlignedBB, y: Float): Float {
        var yY = y
        if (bb.maxX <= this.minX || bb.minX >= this.maxX) {
            return yY
        }

        if (bb.maxZ <= this.minZ || bb.minZ >= this.maxZ) {
            return yY
        }

        if (yY > 0 && bb.maxY <= this.minY) {
            val y1 = this.minY - bb.maxY
            if (y1 < yY) {
                yY = y1
            }
        }

        if (yY < 0 && bb.minY >= this.maxY) {
            val y2 = this.maxY - bb.minY
            if (y2 > yY) {
                yY = y2
            }
        }

        return yY
    }

    fun calculateZOffset(bb: AxisAlignedBB, z: Float): Float {
        var zZ = z
        if (bb.maxX <= this.minX || bb.minX >= this.maxX) {
            return zZ
        }

        if (bb.maxY <= this.minY || bb.minY >= this.maxY) {
            return zZ
        }

        if (zZ > 0 && bb.maxZ <= this.minZ) {
            val z1 = this.minZ - bb.maxZ
            if (z1 < zZ) {
                zZ = z1
            }
        }

        if (zZ < 0 && bb.minZ >= this.maxZ) {
            val z2 = this.maxZ - bb.minZ
            if (z2 > zZ) {
                zZ = z2
            }
        }

        return zZ
    }

    fun intersectsWith(bb: AxisAlignedBB): Boolean {
        return bb.maxX - this.minX > 0.01f && this.maxX - bb.minX > 0.01f &&
                bb.maxY - this.minY > 0.01f && this.maxY - bb.minY > 0.01f &&
                bb.maxZ - this.minZ > 0.01f && this.maxZ - bb.minZ > 0.01f
    }

    fun isVectorInside(vector: Vector): Boolean {
        return !(vector.getX() <= this.minX || vector.getX() >= this.maxX) &&
                !(vector.getY() <= this.minY || vector.getY() >= this.maxY) &&
                (vector.getZ() > this.minZ || vector.getZ() < this.maxZ)
    }

    fun getAverageEdgeLength(): Float {
        return (this.maxX - this.minX + this.maxY - this.minY + this.maxZ - this.minZ) / 3
    }

    fun isVectorInYZ(vector: Vector): Boolean {
        return vector.getY() >= this.minY && vector.getY() <= this.maxY && vector.getZ() >= this.minZ && vector.getZ() <= this.maxZ
    }

    fun isVectorInXZ(vector: Vector): Boolean {
        return vector.getX() >= this.minX && vector.getX() <= this.maxX && vector.getZ() >= this.minZ && vector.getZ() <= this.maxZ
    }

    fun isVectorInXY(vector: Vector): Boolean {
        return vector.getX() >= this.minX && vector.getX() <= this.maxX && vector.getY() >= this.minY && vector.getY() <= this.maxY
    }

    fun calculateIntercept(pos1: Vector, pos2: Vector): Vector? {
        val v1 = pos1.getVectorWhenXIsOnLine(pos2, this.minX)
        val v2 = pos1.getVectorWhenXIsOnLine(pos2, this.maxX)
        val v3 = pos1.getVectorWhenYIsOnLine(pos2, this.minY)
        val v4 = pos1.getVectorWhenYIsOnLine(pos2, this.maxY)
        val v5 = pos1.getVectorWhenZIsOnLine(pos2, this.minZ)
        val v6 = pos1.getVectorWhenZIsOnLine(pos2, this.maxZ)

        var resultVector: Vector? = null
        if (v1 != null && this.isVectorInYZ(v1)) {
            resultVector = v1
        }

        if (v2 != null && this.isVectorInYZ(v2) &&
            (resultVector == null || pos1.distanceSquared(v2) < pos1.distanceSquared(resultVector))
        ) {
            resultVector = v2
        }

        if (v3 != null && this.isVectorInXZ(v3) &&
            (resultVector == null || pos1.distanceSquared(v3) < pos1.distanceSquared(resultVector))
        ) {
            resultVector = v3
        }

        if (v4 != null && this.isVectorInXZ(v4) &&
            (resultVector == null || pos1.distanceSquared(v4) < pos1.distanceSquared(resultVector))
        ) {
            resultVector = v4
        }

        if (v5 != null && this.isVectorInXY(v5) &&
            (resultVector == null || pos1.distanceSquared(v5) < pos1.distanceSquared(resultVector))
        ) {
            resultVector = v5
        }

        if (v6 != null && this.isVectorInXY(v6) &&
            (resultVector == null || pos1.distanceSquared(v6) < pos1.distanceSquared(resultVector))
        ) {
            resultVector = v6
        }

        return resultVector
    }

    public override fun clone(): AxisAlignedBB {
        return try {
            super.clone() as AxisAlignedBB
        } catch (e: CloneNotSupportedException) {
            AxisAlignedBB(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ)
        }.setBounds(this)
    }

    fun getMinX(): Float {
        return this.minX
    }

    fun getMinY(): Float {
        return this.minY
    }

    fun getMinZ(): Float {
        return this.minZ
    }

    fun getMaxX(): Float {
        return this.maxX
    }

    fun getMaxY(): Float {
        return this.maxY
    }

    fun getMaxZ(): Float {
        return this.maxZ
    }

    override fun toString(): String {
        return "AxisAlignedBB(minX=$minX, minY=$minY, minZ=$minZ, maxX=$maxX, maxY=$maxY, maxZ=$maxZ)"
    }


}
