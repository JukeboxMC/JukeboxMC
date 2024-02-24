package org.jukeboxmc.server.world

import org.jukeboxmc.api.world.Biome

class BiomeRegistry {

    companion object {
        private val idFromBiomeEnum: MutableMap<Biome, Int> = mutableMapOf()
        private val biomeFromIdEnum: MutableMap<Int, Biome> = mutableMapOf()

        init {
            register(Biome.OCEAN, 0)
            register(Biome.PLAINS, 1)
            register(Biome.DESERT, 2)
            register(Biome.EXTREME_HILLS, 3)
            register(Biome.FOREST, 4)
            register(Biome.TAIGA, 5)
            register(Biome.SWAMPLAND, 6)
            register(Biome.RIVER, 7)
            register(Biome.HELL, 8)
            register(Biome.THE_END, 9)
            register(Biome.LEGACY_FROZEN_OCEAN, 10)
            register(Biome.FROZEN_RIVER, 11)
            register(Biome.ICE_PLAINS, 12)
            register(Biome.ICE_MOUNTAINS, 13)
            register(Biome.MUSHROOM_ISLAND, 14)
            register(Biome.MUSHROOM_ISLAND_SHORE, 15)
            register(Biome.BEACH, 16)
            register(Biome.DESERT_HILLS, 17)
            register(Biome.FOREST_HILLS, 18)
            register(Biome.TAIGA_HILLS, 19)
            register(Biome.EXTREME_HILLS_EDGE, 20)
            register(Biome.JUNGLE, 21)
            register(Biome.JUNGLE_HILLS, 22)
            register(Biome.JUNGLE_EDGE, 23)
            register(Biome.DEEP_OCEAN, 24)
            register(Biome.STONE_BEACH, 25)
            register(Biome.COLD_BEACH, 26)
            register(Biome.BIRCH_FOREST, 27)
            register(Biome.BIRCH_FOREST_HILLS, 28)
            register(Biome.ROOFED_FOREST, 29)
            register(Biome.COLD_TAIGA, 30)
            register(Biome.COLD_TAIGA_HILLS, 31)
            register(Biome.MEGA_TAIGA, 32)
            register(Biome.MEGA_TAIGA_HILLS, 33)
            register(Biome.EXTREME_HILLS_PLUS_TREES, 34)
            register(Biome.SAVANNA, 35)
            register(Biome.SAVANNA_PLATEAU, 36)
            register(Biome.MESA, 37)
            register(Biome.MESA_PLATEAU_STONE, 38)
            register(Biome.MESA_PLATEAU, 39)
            register(Biome.WARM_OCEAN, 40)
            register(Biome.DEEP_WARM_OCEAN, 41)
            register(Biome.LUKEWARM_OCEAN, 42)
            register(Biome.DEEP_LUKEWARM_OCEAN, 43)
            register(Biome.COLD_OCEAN, 44)
            register(Biome.DEEP_COLD_OCEAN, 45)
            register(Biome.FROZEN_OCEAN, 46)
            register(Biome.DEEP_FROZEN_OCEAN, 47)
            register(Biome.BAMBOO_JUNGLE, 48)
            register(Biome.BAMBOO_JUNGLE_HILLS, 49)
            register(Biome.SUNFLOWER_PLAINS, 129)
            register(Biome.DESERT_MUTATED, 130)
            register(Biome.EXTREME_HILLS_MUTATED, 131)
            register(Biome.FLOWER_FOREST, 132)
            register(Biome.TAIGA_MUTATED, 133)
            register(Biome.SWAMPLAND_MUTATED, 134)
            register(Biome.ICE_PLAINS_SPIKES, 140)
            register(Biome.JUNGLE_MUTATED, 149)
            register(Biome.JUNGLE_EDGE_MUTATED, 151)
            register(Biome.BIRCH_FOREST_MUTATED, 155)
            register(Biome.BIRCH_FOREST_HILLS_MUTATED, 156)
            register(Biome.ROOFED_FOREST_MUTATED, 157)
            register(Biome.COLD_TAIGA_MUTATED, 158)
            register(Biome.REDWOOD_TAIGA_MUTATED, 160)
            register(Biome.REDWOOD_TAIGA_HILLS_MUTATED, 161)
            register(Biome.EXTREME_HILLS_PLUS_TREES_MUTATED, 162)
            register(Biome.SAVANNA_MUTATED, 163)
            register(Biome.SAVANNA_PLATEAU_MUTATED, 164)
            register(Biome.MESA_BRYCE, 165)
            register(Biome.MESA_PLATEAU_STONE_MUTATED, 166)
            register(Biome.MESA_PLATEAU_MUTATED, 167)
            register(Biome.SOULSAND_VALLEY, 178)
            register(Biome.CRIMSON_FOREST, 179)
            register(Biome.WARPED_FOREST, 180)
            register(Biome.BASALT_DELTAS, 181)
            register(Biome.JAGGED_PEAKS, 182)
            register(Biome.FROZEN_PEAKS, 183)
            register(Biome.SNOWY_SLOPES, 184)
            register(Biome.GROVE, 185)
            register(Biome.MEADOW, 186)
            register(Biome.LUSH_CAVES, 187)
            register(Biome.DRIPSTONE_CAVES, 188)
            register(Biome.STONY_PEAKS, 189)
            register(Biome.DEEP_DARK, 190)
            register(Biome.MANGROVE_SWAMP, 191)
            register(Biome.CHERRY_GROVE, 192)
        }

        fun register(biome: Biome, id: Int) {
            this.idFromBiomeEnum[biome] = id
            this.biomeFromIdEnum[id] = biome
        }

        fun getBiomeId(biome: Biome): Int {
            return this.idFromBiomeEnum[biome]!!
        }

        fun getBiome(id: Int): Biome {
            return this.biomeFromIdEnum[id]!!
        }
    }
}