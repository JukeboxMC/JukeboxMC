package org.jukeboxmc.api.world

/**
 * @author Kaooot
 * @version 1.0
 */
enum class Dimension(
    private val minY: Int,
    private val maxY: Int
) {

    OVERWORLD(-64, 319),
    NETHER(0, 128),
    THE_END(0, 256);

    fun getMinY(): Int {
        return this.minY
    }

    fun getMaxY(): Int {
        return this.maxY
    }
}
