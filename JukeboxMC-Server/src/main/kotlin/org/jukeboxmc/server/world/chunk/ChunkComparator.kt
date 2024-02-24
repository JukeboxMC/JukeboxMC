package org.jukeboxmc.server.world.chunk

import it.unimi.dsi.fastutil.longs.LongComparator
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.util.Utils


class ChunkComparator(private val player: JukeboxPlayer) : LongComparator {
    override fun compare(o1: Long, o2: Long): Int {
        val x1: Int = Utils.fromHashX(o1)
        val z1: Int = Utils.fromHashZ(o1)
        val x2: Int = Utils.fromHashX(o2)
        val z2: Int = Utils.fromHashZ(o2)
        val spawnX: Int = player.getChunkX()
        val spawnZ: Int = player.getChunkZ()
        return distance(spawnX, spawnZ, x1, z1).compareTo(distance(spawnX, spawnZ, x2, z2))
    }

    companion object {
        fun distance(centerX: Int, centerZ: Int, x: Int, z: Int): Int {
            val dx = centerX - x
            val dz = centerZ - z
            return dx * dx + dz * dz
        }
    }
}

