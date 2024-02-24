package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityType

class BlockEntityRegistry {

    companion object {

        private val blockEntityClassFromType: MutableMap<BlockEntityType, Class<out JukeboxBlockEntity>> = mutableMapOf()

        init {
            register(BlockEntityType.BANNER, JukeboxBlockEntityBanner::class.java)
            register(BlockEntityType.BARREL, JukeboxBlockEntityBarrel::class.java)
            register(BlockEntityType.BEACON, JukeboxBlockEntityBeacon::class.java)
            register(BlockEntityType.BED, JukeboxBlockEntityBed::class.java)
            register(BlockEntityType.BEEHIVE, JukeboxBlockEntityBeehive::class.java)
            register(BlockEntityType.BELL, JukeboxBlockEntityBell::class.java)
            register(BlockEntityType.BLASTFURNACE, JukeboxBlockEntityBlastFurnace::class.java)
            register(BlockEntityType.BREWINGSTAND, JukeboxBlockEntityBrewingStand::class.java)
            register(BlockEntityType.BRUSHABLEBLOCK, JukeboxBlockEntityBrushableBlock::class.java)
            register(BlockEntityType.CALIBRATEDSCULKSENSOR, JukeboxBlockEntityCalibratedSculkSensor::class.java)
            register(BlockEntityType.CAMPFIRE, JukeboxBlockEntityCampfire::class.java)
            register(BlockEntityType.CAULDRON, JukeboxBlockEntityCauldron::class.java)
            register(BlockEntityType.CHEMISTRYTABLE, JukeboxBlockEntityChemistryTable::class.java)
            register(BlockEntityType.CHEST, JukeboxBlockEntityChest::class.java)
            register(BlockEntityType.CHISELEDBOOKSHELF, JukeboxBlockEntityChiseledBookshelf::class.java)
            register(BlockEntityType.COMMANDBLOCK, JukeboxBlockEntityCommandBlock::class.java)
            register(BlockEntityType.COMPARATOR, JukeboxBlockEntityComparator::class.java)
            register(BlockEntityType.CONDUIT, JukeboxBlockEntityConduit::class.java)
            register(BlockEntityType.COUNT, JukeboxBlockEntityCount::class.java)
            register(BlockEntityType.DAYLIGHTDETECTOR, JukeboxBlockEntityDaylightDetector::class.java)
            register(BlockEntityType.DECORATEDPOT, JukeboxBlockEntityDecoratedPot::class.java)
            register(BlockEntityType.DISPENSER, JukeboxBlockEntityDispenser::class.java)
            register(BlockEntityType.DROPPER, JukeboxBlockEntityDropper::class.java)
            register(BlockEntityType.ENCHANTINGTABLE, JukeboxBlockEntityEnchantingTable::class.java)
            register(BlockEntityType.ENDGATEWAY, JukeboxBlockEntityEndGateway::class.java)
            register(BlockEntityType.ENDPORTAL, JukeboxBlockEntityEndPortal::class.java)
            register(BlockEntityType.ENDERCHEST, JukeboxBlockEntityEnderChest::class.java)
            register(BlockEntityType.FLOWERPOT, JukeboxBlockEntityFlowerPot::class.java)
            register(BlockEntityType.FURNACE, JukeboxBlockEntityFurnace::class.java)
            register(BlockEntityType.GLOWITEMFRAME, JukeboxBlockEntityGlowItemFrame::class.java)
            register(BlockEntityType.HANGINGSIGN, JukeboxBlockEntityHangingSign::class.java)
            register(BlockEntityType.HOPPER, JukeboxBlockEntityHopper::class.java)
            register(BlockEntityType.ITEMFRAME, JukeboxBlockEntityItemFrame::class.java)
            register(BlockEntityType.JIGSAWBLOCK, JukeboxBlockEntityJigsawBlock::class.java)
            register(BlockEntityType.JUKEBOX, JukeboxBlockEntityJukebox::class.java)
            register(BlockEntityType.LECTERN, JukeboxBlockEntityLectern::class.java)
            register(BlockEntityType.LODESTONE, JukeboxBlockEntityLodestone::class.java)
            register(BlockEntityType.MOBSPAWNER, JukeboxBlockEntityMobSpawner::class.java)
            register(BlockEntityType.MOVINGBLOCK, JukeboxBlockEntityMovingBlock::class.java)
            register(BlockEntityType.MUSIC, JukeboxBlockEntityMusic::class.java)
            register(BlockEntityType.NETHERREACTOR, JukeboxBlockEntityNetherReactor::class.java)
            register(BlockEntityType.PISTONARM, JukeboxBlockEntityPistonArm::class.java)
            register(BlockEntityType.SCULKCATALYST, JukeboxBlockEntitySculkCatalyst::class.java)
            register(BlockEntityType.SCULKSENSOR, JukeboxBlockEntitySculkSensor::class.java)
            register(BlockEntityType.SCULKSHRIEKER, JukeboxBlockEntitySculkShrieker::class.java)
            register(BlockEntityType.SHULKERBOX, JukeboxBlockEntityShulkerBox::class.java)
            register(BlockEntityType.SIGN, JukeboxBlockEntitySign::class.java)
            register(BlockEntityType.SKULL, JukeboxBlockEntitySkull::class.java)
            register(BlockEntityType.SMOKER, JukeboxBlockEntitySmoker::class.java)
            register(BlockEntityType.SPOREBLOSSOM, JukeboxBlockEntitySporeBlossom::class.java)
            register(BlockEntityType.STRUCTUREBLOCK, JukeboxBlockEntityStructureBlock::class.java)
        }

        private fun register(blockEntityType: BlockEntityType, blockEntityClazz: Class<out JukeboxBlockEntity>) {
            this.blockEntityClassFromType[blockEntityType] = blockEntityClazz
        }

        fun blockEntityClassExists(blockEntityType: BlockEntityType): Boolean {
            return this.blockEntityClassFromType.containsKey(blockEntityType)
        }

        fun getBlockEntityClass(blockEntityType: BlockEntityType): Class<out JukeboxBlockEntity> {
            return this.blockEntityClassFromType[blockEntityType]!!
        }
    }
}