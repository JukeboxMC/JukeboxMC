package org.jukeboxmc.server.block

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.server.block.behavior.*

class BlockRegistry {

    companion object {

        private val identifierFromBlockType: MutableMap<BlockType, Identifier> = mutableMapOf()
        private val blockTypeFromIdentifier: MutableMap<Identifier, BlockType> = mutableMapOf()
        private val blockClassFromBlockType: MutableMap<BlockType, Class<out JukeboxBlock?>> = mutableMapOf()

        val blockByBlockType: MutableMap<BlockType, JukeboxBlock> = mutableMapOf()

        init {
            register(BlockType.ACACIA_BUTTON, Identifier.fromString("minecraft:acacia_button"), BlockButton::class.java)
            register(BlockType.ACACIA_DOOR, Identifier.fromString("minecraft:acacia_door"), BlockDoor::class.java)
            register(BlockType.ACACIA_FENCE, Identifier.fromString("minecraft:acacia_fence"))
            register(
                BlockType.ACACIA_FENCE_GATE,
                Identifier.fromString("minecraft:acacia_fence_gate"),
                BlockFenceGate::class.java
            )
            register(
                BlockType.ACACIA_HANGING_SIGN,
                Identifier.fromString("minecraft:acacia_hanging_sign"),
                BlockHangingSign::class.java
            )
            register(BlockType.ACACIA_LOG, Identifier.fromString("minecraft:acacia_log"), BlockLog::class.java)
            register(BlockType.ACACIA_PLANKS, Identifier.fromString("minecraft:acacia_planks"))
            register(
                BlockType.ACACIA_PRESSURE_PLATE,
                Identifier.fromString("minecraft:acacia_pressure_plate"),
                BlockPressurePlate::class.java
            )
            register(BlockType.ACACIA_STAIRS, Identifier.fromString("minecraft:acacia_stairs"), BlockStairs::class.java)
            register(
                BlockType.ACACIA_STANDING_SIGN,
                Identifier.fromString("minecraft:acacia_standing_sign"),
                BlockStandingSign::class.java
            )
            register(
                BlockType.ACACIA_TRAPDOOR,
                Identifier.fromString("minecraft:acacia_trapdoor"),
                BlockTrapdoor::class.java
            )
            register(
                BlockType.ACACIA_WALL_SIGN,
                Identifier.fromString("minecraft:acacia_wall_sign"),
                BlockWallSign::class.java
            )
            register(
                BlockType.ACTIVATOR_RAIL,
                Identifier.fromString("minecraft:activator_rail"),
                BlockActivatorRail::class.java
            )
            register(BlockType.AIR, Identifier.fromString("minecraft:air"), BlockAir::class.java)
            register(BlockType.ALLOW, Identifier.fromString("minecraft:allow"))
            register(BlockType.AMETHYST_BLOCK, Identifier.fromString("minecraft:amethyst_block"))
            register(
                BlockType.AMETHYST_CLUSTER,
                Identifier.fromString("minecraft:amethyst_cluster"),
                BlockAmethystCluster::class.java
            )
            register(BlockType.ANCIENT_DEBRIS, Identifier.fromString("minecraft:ancient_debris"))
            register(BlockType.ANDESITE, Identifier.fromString("minecraft:andesite"))
            register(
                BlockType.ANDESITE_STAIRS,
                Identifier.fromString("minecraft:andesite_stairs"),
                BlockStairs::class.java
            )
            register(BlockType.ANVIL, Identifier.fromString("minecraft:anvil"), BlockAnvil::class.java)
            register(BlockType.AZALEA, Identifier.fromString("minecraft:azalea"), BlockAzalea::class.java)
            register(
                BlockType.AZALEA_LEAVES,
                Identifier.fromString("minecraft:azalea_leaves"),
                BlockAzaleaLeaves::class.java
            )
            register(
                BlockType.AZALEA_LEAVES_FLOWERED,
                Identifier.fromString("minecraft:azalea_leaves_flowered"),
                BlockAzaleaLeavesFlowered::class.java
            )
            register(BlockType.BAMBOO, Identifier.fromString("minecraft:bamboo"), BlockBamboo::class.java)
            register(
                BlockType.BAMBOO_BLOCK,
                Identifier.fromString("minecraft:bamboo_block"),
                BlockBambooBlock::class.java
            )
            register(BlockType.BAMBOO_BUTTON, Identifier.fromString("minecraft:bamboo_button"), BlockButton::class.java)
            register(BlockType.BAMBOO_DOOR, Identifier.fromString("minecraft:bamboo_door"), BlockDoor::class.java)
            register(
                BlockType.BAMBOO_DOUBLE_SLAB,
                Identifier.fromString("minecraft:bamboo_double_slab"),
                BlockDoubleSlab::class.java
            )
            register(BlockType.BAMBOO_FENCE, Identifier.fromString("minecraft:bamboo_fence"))
            register(
                BlockType.BAMBOO_FENCE_GATE,
                Identifier.fromString("minecraft:bamboo_fence_gate"),
                BlockFenceGate::class.java
            )
            register(
                BlockType.BAMBOO_HANGING_SIGN,
                Identifier.fromString("minecraft:bamboo_hanging_sign"),
                BlockHangingSign::class.java
            )
            register(BlockType.BAMBOO_MOSAIC, Identifier.fromString("minecraft:bamboo_mosaic"))
            register(
                BlockType.BAMBOO_MOSAIC_DOUBLE_SLAB,
                Identifier.fromString("minecraft:bamboo_mosaic_double_slab"),
                BlockDoubleSlab::class.java
            )
            register(
                BlockType.BAMBOO_MOSAIC_SLAB,
                Identifier.fromString("minecraft:bamboo_mosaic_slab"),
                BlockSlab::class.java
            )
            register(
                BlockType.BAMBOO_MOSAIC_STAIRS,
                Identifier.fromString("minecraft:bamboo_mosaic_stairs"),
                BlockBambooMosaicStairs::class.java
            )
            register(BlockType.BAMBOO_PLANKS, Identifier.fromString("minecraft:bamboo_planks"))
            register(
                BlockType.BAMBOO_PRESSURE_PLATE,
                Identifier.fromString("minecraft:bamboo_pressure_plate"),
                BlockPressurePlate::class.java
            )
            register(
                BlockType.BAMBOO_SAPLING,
                Identifier.fromString("minecraft:bamboo_sapling"),
                BlockBambooSapling::class.java
            )
            register(BlockType.BAMBOO_SLAB, Identifier.fromString("minecraft:bamboo_slab"), BlockSlab::class.java)
            register(BlockType.BAMBOO_STAIRS, Identifier.fromString("minecraft:bamboo_stairs"), BlockStairs::class.java)
            register(
                BlockType.BAMBOO_STANDING_SIGN,
                Identifier.fromString("minecraft:bamboo_standing_sign"),
                BlockStandingSign::class.java
            )
            register(
                BlockType.BAMBOO_TRAPDOOR,
                Identifier.fromString("minecraft:bamboo_trapdoor"),
                BlockTrapdoor::class.java
            )
            register(
                BlockType.BAMBOO_WALL_SIGN,
                Identifier.fromString("minecraft:bamboo_wall_sign"),
                BlockWallSign::class.java
            )
            register(BlockType.BARREL, Identifier.fromString("minecraft:barrel"), BlockBarrel::class.java)
            register(BlockType.BARRIER, Identifier.fromString("minecraft:barrier"))
            register(BlockType.BASALT, Identifier.fromString("minecraft:basalt"), BlockBasalt::class.java)
            register(BlockType.BEACON, Identifier.fromString("minecraft:beacon"), BlockBeacon::class.java)
            register(BlockType.BED, Identifier.fromString("minecraft:bed"), BlockBed::class.java)
            register(BlockType.BEDROCK, Identifier.fromString("minecraft:bedrock"), BlockBedrock::class.java)
            register(BlockType.BEE_NEST, Identifier.fromString("minecraft:bee_nest"), BlockBeeNest::class.java)
            register(BlockType.BEEHIVE, Identifier.fromString("minecraft:beehive"), BlockBeehive::class.java)
            register(BlockType.BEETROOT, Identifier.fromString("minecraft:beetroot"), BlockBeetroot::class.java)
            register(BlockType.BELL, Identifier.fromString("minecraft:bell"), BlockBell::class.java)
            register(
                BlockType.BIG_DRIPLEAF,
                Identifier.fromString("minecraft:big_dripleaf"),
                BlockBigDripleaf::class.java
            )
            register(BlockType.BIRCH_BUTTON, Identifier.fromString("minecraft:birch_button"), BlockButton::class.java)
            register(BlockType.BIRCH_DOOR, Identifier.fromString("minecraft:birch_door"), BlockDoor::class.java)
            register(BlockType.BIRCH_FENCE, Identifier.fromString("minecraft:birch_fence"))
            register(
                BlockType.BIRCH_FENCE_GATE,
                Identifier.fromString("minecraft:birch_fence_gate"),
                BlockFenceGate::class.java
            )
            register(
                BlockType.BIRCH_HANGING_SIGN,
                Identifier.fromString("minecraft:birch_hanging_sign"),
                BlockHangingSign::class.java
            )
            register(BlockType.BIRCH_LOG, Identifier.fromString("minecraft:birch_log"), BlockLog::class.java)
            register(BlockType.BIRCH_PLANKS, Identifier.fromString("minecraft:birch_planks"))
            register(
                BlockType.BIRCH_PRESSURE_PLATE,
                Identifier.fromString("minecraft:birch_pressure_plate"),
                BlockPressurePlate::class.java
            )
            register(BlockType.BIRCH_STAIRS, Identifier.fromString("minecraft:birch_stairs"), BlockStairs::class.java)
            register(
                BlockType.BIRCH_STANDING_SIGN,
                Identifier.fromString("minecraft:birch_standing_sign"),
                BlockStandingSign::class.java
            )
            register(
                BlockType.BIRCH_TRAPDOOR,
                Identifier.fromString("minecraft:birch_trapdoor"),
                BlockTrapdoor::class.java
            )
            register(
                BlockType.BIRCH_WALL_SIGN,
                Identifier.fromString("minecraft:birch_wall_sign"),
                BlockWallSign::class.java
            )
            register(BlockType.BLACK_CANDLE, Identifier.fromString("minecraft:black_candle"), BlockCandle::class.java)
            register(
                BlockType.BLACK_CANDLE_CAKE,
                Identifier.fromString("minecraft:black_candle_cake"),
                BlockCandleCake::class.java
            )
            register(BlockType.BLACK_CARPET, Identifier.fromString("minecraft:black_carpet"))
            register(BlockType.BLACK_CONCRETE, Identifier.fromString("minecraft:black_concrete"))
            register(BlockType.BLACK_CONCRETE_POWDER, Identifier.fromString("minecraft:black_concrete_powder"))
            register(
                BlockType.BLACK_GLAZED_TERRACOTTA,
                Identifier.fromString("minecraft:black_glazed_terracotta"),
                BlockGlazedTerracotta::class.java
            )
            register(
                BlockType.BLACK_SHULKER_BOX,
                Identifier.fromString("minecraft:black_shulker_box"),
                BlockShulkerBox::class.java
            )
            register(BlockType.BLACK_STAINED_GLASS, Identifier.fromString("minecraft:black_stained_glass"))
            register(BlockType.BLACK_STAINED_GLASS_PANE, Identifier.fromString("minecraft:black_stained_glass_pane"))
            register(BlockType.BLACK_TERRACOTTA, Identifier.fromString("minecraft:black_terracotta"))
            register(BlockType.BLACK_WOOL, Identifier.fromString("minecraft:black_wool"))
            register(BlockType.BLACKSTONE, Identifier.fromString("minecraft:blackstone"))
            register(
                BlockType.BLACKSTONE_DOUBLE_SLAB,
                Identifier.fromString("minecraft:blackstone_double_slab"),
                BlockDoubleSlab::class.java
            )
            register(
                BlockType.BLACKSTONE_SLAB,
                Identifier.fromString("minecraft:blackstone_slab"),
                BlockSlab::class.java
            )
            register(
                BlockType.BLACKSTONE_STAIRS,
                Identifier.fromString("minecraft:blackstone_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.BLACKSTONE_WALL,
                Identifier.fromString("minecraft:blackstone_wall"),
                BlockWall::class.java
            )
            register(
                BlockType.BLAST_FURNACE,
                Identifier.fromString("minecraft:blast_furnace"),
                BlockBlastFurnace::class.java
            )
            register(BlockType.BLUE_CANDLE, Identifier.fromString("minecraft:blue_candle"), BlockCandle::class.java)
            register(
                BlockType.BLUE_CANDLE_CAKE,
                Identifier.fromString("minecraft:blue_candle_cake"),
                BlockCandleCake::class.java
            )
            register(BlockType.BLUE_CARPET, Identifier.fromString("minecraft:blue_carpet"))
            register(BlockType.BLUE_CONCRETE, Identifier.fromString("minecraft:blue_concrete"))
            register(BlockType.BLUE_CONCRETE_POWDER, Identifier.fromString("minecraft:blue_concrete_powder"))
            register(
                BlockType.BLUE_GLAZED_TERRACOTTA,
                Identifier.fromString("minecraft:blue_glazed_terracotta"),
                BlockGlazedTerracotta::class.java
            )
            register(BlockType.BLUE_ICE, Identifier.fromString("minecraft:blue_ice"))
            register(
                BlockType.BLUE_SHULKER_BOX,
                Identifier.fromString("minecraft:blue_shulker_box"),
                BlockShulkerBox::class.java
            )
            register(BlockType.BLUE_STAINED_GLASS, Identifier.fromString("minecraft:blue_stained_glass"))
            register(BlockType.BLUE_STAINED_GLASS_PANE, Identifier.fromString("minecraft:blue_stained_glass_pane"))
            register(BlockType.BLUE_TERRACOTTA, Identifier.fromString("minecraft:blue_terracotta"))
            register(BlockType.BLUE_WOOL, Identifier.fromString("minecraft:blue_wool"))
            register(BlockType.BONE_BLOCK, Identifier.fromString("minecraft:bone_block"), BlockBoneBlock::class.java)
            register(BlockType.BOOKSHELF, Identifier.fromString("minecraft:bookshelf"))
            register(BlockType.BORDER_BLOCK, Identifier.fromString("minecraft:border_block"), BorderBlock::class.java)
            register(BlockType.BRAIN_CORAL, Identifier.fromString("minecraft:brain_coral"))
            register(
                BlockType.BREWING_STAND,
                Identifier.fromString("minecraft:brewing_stand"),
                BlockBrewingStand::class.java
            )
            register(BlockType.BRICK_BLOCK, Identifier.fromString("minecraft:brick_block"))
            register(BlockType.BRICK_STAIRS, Identifier.fromString("minecraft:brick_stairs"), BlockStairs::class.java)
            register(BlockType.BROWN_CANDLE, Identifier.fromString("minecraft:brown_candle"), BlockCandle::class.java)
            register(
                BlockType.BROWN_CANDLE_CAKE,
                Identifier.fromString("minecraft:brown_candle_cake"),
                BlockCandleCake::class.java
            )
            register(BlockType.BROWN_CARPET, Identifier.fromString("minecraft:brown_carpet"))
            register(BlockType.BROWN_CONCRETE, Identifier.fromString("minecraft:brown_concrete"))
            register(BlockType.BROWN_CONCRETE_POWDER, Identifier.fromString("minecraft:brown_concrete_powder"))
            register(
                BlockType.BROWN_GLAZED_TERRACOTTA,
                Identifier.fromString("minecraft:brown_glazed_terracotta"),
                BlockGlazedTerracotta::class.java
            )
            register(BlockType.BROWN_MUSHROOM, Identifier.fromString("minecraft:brown_mushroom"), BlockBrownMushroom::class.java)
            register(
                BlockType.BROWN_MUSHROOM_BLOCK,
                Identifier.fromString("minecraft:brown_mushroom_block"),
                BlockBrownMushroomBlock::class.java
            )
            register(BlockType.BROWN_SHULKER_BOX, Identifier.fromString("minecraft:brown_shulker_box"))
            register(BlockType.BROWN_STAINED_GLASS, Identifier.fromString("minecraft:brown_stained_glass"))
            register(BlockType.BROWN_STAINED_GLASS_PANE, Identifier.fromString("minecraft:brown_stained_glass_pane"))
            register(BlockType.BROWN_TERRACOTTA, Identifier.fromString("minecraft:brown_terracotta"))
            register(BlockType.BROWN_WOOL, Identifier.fromString("minecraft:brown_wool"))
            register(
                BlockType.BUBBLE_COLUMN,
                Identifier.fromString("minecraft:bubble_column"),
                BlockBubbleColumn::class.java
            )
            register(BlockType.BUBBLE_CORAL, Identifier.fromString("minecraft:bubble_coral"))
            register(BlockType.BUDDING_AMETHYST, Identifier.fromString("minecraft:budding_amethyst"))
            register(BlockType.CACTUS, Identifier.fromString("minecraft:cactus"), BlockCactus::class.java)
            register(BlockType.CAKE, Identifier.fromString("minecraft:cake"), BlockCake::class.java)
            register(BlockType.CALCITE, Identifier.fromString("minecraft:calcite"))
            register(
                BlockType.CALIBRATED_SCULK_SENSOR,
                Identifier.fromString("minecraft:calibrated_sculk_sensor"),
                BlockCalibratedSculkSensor::class.java
            )
            register(BlockType.CAMERA, Identifier.fromString("minecraft:camera"))
            register(BlockType.CAMPFIRE, Identifier.fromString("minecraft:campfire"), BlockCampfire::class.java)
            register(BlockType.CANDLE, Identifier.fromString("minecraft:candle"), BlockCandle::class.java)
            register(BlockType.CANDLE_CAKE, Identifier.fromString("minecraft:candle_cake"), BlockCandleCake::class.java)
            register(BlockType.CARROTS, Identifier.fromString("minecraft:carrots"), BlockCarrots::class.java)
            register(BlockType.CARTOGRAPHY_TABLE, Identifier.fromString("minecraft:cartography_table"), BlockCartographyTable::class.java)
            register(
                BlockType.CARVED_PUMPKIN,
                Identifier.fromString("minecraft:carved_pumpkin"),
                BlockCarvedPumpkin::class.java
            )
            register(BlockType.CAULDRON, Identifier.fromString("minecraft:cauldron"), BlockCauldron::class.java)
            register(BlockType.CAVE_VINES, Identifier.fromString("minecraft:cave_vines"), BlockCaveVines::class.java)
            register(
                BlockType.CAVE_VINES_BODY_WITH_BERRIES,
                Identifier.fromString("minecraft:cave_vines_body_with_berries"),
                BlockCaveVinesBodyWithBerries::class.java
            )
            register(
                BlockType.CAVE_VINES_HEAD_WITH_BERRIES,
                Identifier.fromString("minecraft:cave_vines_head_with_berries"),
                BlockCaveVinesHeadWithBerries::class.java
            )
            register(BlockType.CHAIN, Identifier.fromString("minecraft:chain"), BlockChain::class.java)
            register(
                BlockType.CHAIN_COMMAND_BLOCK,
                Identifier.fromString("minecraft:chain_command_block"),
                BlockChainCommandBlock::class.java
            )
            register(BlockType.CHEMICAL_HEAT, Identifier.fromString("minecraft:chemical_heat"))
            register(
                BlockType.CHEMISTRY_TABLE,
                Identifier.fromString("minecraft:chemistry_table"),
                BlockChemistryTable::class.java
            )
            register(BlockType.CHERRY_BUTTON, Identifier.fromString("minecraft:cherry_button"), BlockButton::class.java)
            register(BlockType.CHERRY_DOOR, Identifier.fromString("minecraft:cherry_door"), BlockDoor::class.java)
            register(
                BlockType.CHERRY_DOUBLE_SLAB,
                Identifier.fromString("minecraft:cherry_double_slab"),
                BlockDoubleSlab::class.java
            )
            register(BlockType.CHERRY_FENCE, Identifier.fromString("minecraft:cherry_fence"))
            register(
                BlockType.CHERRY_FENCE_GATE,
                Identifier.fromString("minecraft:cherry_fence_gate"),
                BlockFenceGate::class.java
            )
            register(
                BlockType.CHERRY_HANGING_SIGN,
                Identifier.fromString("minecraft:cherry_hanging_sign"),
                BlockHangingSign::class.java
            )
            register(
                BlockType.CHERRY_LEAVES,
                Identifier.fromString("minecraft:cherry_leaves"),
                BlockCherryLeaves::class.java
            )
            register(BlockType.CHERRY_LOG, Identifier.fromString("minecraft:cherry_log"), BlockLog::class.java)
            register(BlockType.CHERRY_PLANKS, Identifier.fromString("minecraft:cherry_planks"))
            register(
                BlockType.CHERRY_PRESSURE_PLATE,
                Identifier.fromString("minecraft:cherry_pressure_plate"),
                BlockPressurePlate::class.java
            )
            register(
                BlockType.CHERRY_SAPLING,
                Identifier.fromString("minecraft:cherry_sapling"),
                BlockCherrySapling::class.java
            )
            register(BlockType.CHERRY_SLAB, Identifier.fromString("minecraft:cherry_slab"), BlockSlab::class.java)
            register(BlockType.CHERRY_STAIRS, Identifier.fromString("minecraft:cherry_stairs"), BlockStairs::class.java)
            register(
                BlockType.CHERRY_STANDING_SIGN,
                Identifier.fromString("minecraft:cherry_standing_sign"),
                BlockStandingSign::class.java
            )
            register(
                BlockType.CHERRY_TRAPDOOR,
                Identifier.fromString("minecraft:cherry_trapdoor"),
                BlockTrapdoor::class.java
            )
            register(
                BlockType.CHERRY_WALL_SIGN,
                Identifier.fromString("minecraft:cherry_wall_sign"),
                BlockWallSign::class.java
            )
            register(BlockType.CHERRY_WOOD, Identifier.fromString("minecraft:cherry_wood"), BlockCherryWood::class.java)
            register(BlockType.CHEST, Identifier.fromString("minecraft:chest"), BlockChest::class.java)
            register(
                BlockType.CHISELED_BOOKSHELF,
                Identifier.fromString("minecraft:chiseled_bookshelf"),
                BlockChiseledBookshelf::class.java
            )
            register(BlockType.CHISELED_COPPER, Identifier.fromString("minecraft:chiseled_copper"))
            register(BlockType.CHISELED_DEEPSLATE, Identifier.fromString("minecraft:chiseled_deepslate"))
            register(BlockType.CHISELED_NETHER_BRICKS, Identifier.fromString("minecraft:chiseled_nether_bricks"))
            register(
                BlockType.CHISELED_POLISHED_BLACKSTONE,
                Identifier.fromString("minecraft:chiseled_polished_blackstone")
            )
            register(BlockType.CHISELED_TUFF, Identifier.fromString("minecraft:chiseled_tuff"))
            register(BlockType.CHISELED_TUFF_BRICKS, Identifier.fromString("minecraft:chiseled_tuff_bricks"))
            register(
                BlockType.CHORUS_FLOWER,
                Identifier.fromString("minecraft:chorus_flower"),
                BlockChorusFlower::class.java
            )
            register(BlockType.CHORUS_PLANT, Identifier.fromString("minecraft:chorus_plant"))
            register(BlockType.CLAY, Identifier.fromString("minecraft:clay"))
            register(
                BlockType.CLIENT_REQUEST_PLACEHOLDER_BLOCK,
                Identifier.fromString("minecraft:client_request_placeholder_block")
            )
            register(BlockType.COAL_BLOCK, Identifier.fromString("minecraft:coal_block"))
            register(BlockType.COAL_ORE, Identifier.fromString("minecraft:coal_ore"))
            register(BlockType.COBBLED_DEEPSLATE, Identifier.fromString("minecraft:cobbled_deepslate"))
            register(
                BlockType.COBBLED_DEEPSLATE_DOUBLE_SLAB,
                Identifier.fromString("minecraft:cobbled_deepslate_double_slab"),
                BlockDoubleSlab::class.java
            )
            register(
                BlockType.COBBLED_DEEPSLATE_SLAB,
                Identifier.fromString("minecraft:cobbled_deepslate_slab"),
                BlockSlab::class.java
            )
            register(
                BlockType.COBBLED_DEEPSLATE_STAIRS,
                Identifier.fromString("minecraft:cobbled_deepslate_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.COBBLED_DEEPSLATE_WALL,
                Identifier.fromString("minecraft:cobbled_deepslate_wall"),
                BlockWall::class.java
            )
            register(BlockType.COBBLESTONE, Identifier.fromString("minecraft:cobblestone"))
            register(
                BlockType.COBBLESTONE_WALL,
                Identifier.fromString("minecraft:cobblestone_wall"),
                BlockCobblestoneWall::class.java
            )
            register(BlockType.COCOA, Identifier.fromString("minecraft:cocoa"), BlockCocoa::class.java)
            register(
                BlockType.COLORED_TORCH_BP,
                Identifier.fromString("minecraft:colored_torch_bp"),
                BlockColoredTorchBp::class.java
            )
            register(
                BlockType.COLORED_TORCH_RG,
                Identifier.fromString("minecraft:colored_torch_rg"),
                BlockColoredTorchRg::class.java
            )
            register(
                BlockType.COMMAND_BLOCK,
                Identifier.fromString("minecraft:command_block"),
                BlockCommandBlock::class.java
            )
            register(BlockType.COMPOSTER, Identifier.fromString("minecraft:composter"), BlockComposter::class.java)
            register(BlockType.CONDUIT, Identifier.fromString("minecraft:conduit"), BlockConduit::class.java)
            register(BlockType.COPPER_BLOCK, Identifier.fromString("minecraft:copper_block"))
            register(BlockType.COPPER_BULB, Identifier.fromString("minecraft:copper_bulb"), BlockCopperBulb::class.java)
            register(BlockType.COPPER_DOOR, Identifier.fromString("minecraft:copper_door"), BlockDoor::class.java)
            register(BlockType.COPPER_GRATE, Identifier.fromString("minecraft:copper_grate"))
            register(BlockType.COPPER_ORE, Identifier.fromString("minecraft:copper_ore"))
            register(
                BlockType.COPPER_TRAPDOOR,
                Identifier.fromString("minecraft:copper_trapdoor"),
                BlockTrapdoor::class.java
            )
            register(BlockType.CORAL_BLOCK, Identifier.fromString("minecraft:coral_block"), BlockCoralBlock::class.java)
            register(BlockType.CORAL_FAN, Identifier.fromString("minecraft:coral_fan"), BlockCoralFan::class.java)
            register(
                BlockType.CORAL_FAN_DEAD,
                Identifier.fromString("minecraft:coral_fan_dead"),
                BlockCoralFanDead::class.java
            )
            register(
                BlockType.CORAL_FAN_HANG,
                Identifier.fromString("minecraft:coral_fan_hang"),
                BlockCoralFanHang::class.java
            )
            register(
                BlockType.CORAL_FAN_HANG2,
                Identifier.fromString("minecraft:coral_fan_hang2"),
                BlockCoralFanHang2::class.java
            )
            register(
                BlockType.CORAL_FAN_HANG3,
                Identifier.fromString("minecraft:coral_fan_hang3"),
                BlockCoralFanHang3::class.java
            )
            register(BlockType.CRACKED_DEEPSLATE_BRICKS, Identifier.fromString("minecraft:cracked_deepslate_bricks"))
            register(BlockType.CRACKED_DEEPSLATE_TILES, Identifier.fromString("minecraft:cracked_deepslate_tiles"))
            register(BlockType.CRACKED_NETHER_BRICKS, Identifier.fromString("minecraft:cracked_nether_bricks"))
            register(
                BlockType.CRACKED_POLISHED_BLACKSTONE_BRICKS,
                Identifier.fromString("minecraft:cracked_polished_blackstone_bricks")
            )
            register(BlockType.CRAFTER, Identifier.fromString("minecraft:crafter"), BlockCrafter::class.java)
            register(BlockType.CRAFTING_TABLE, Identifier.fromString("minecraft:crafting_table"), BlockCraftingTable::class.java)
            register(
                BlockType.CRIMSON_BUTTON,
                Identifier.fromString("minecraft:crimson_button"),
                BlockButton::class.java
            )
            register(BlockType.CRIMSON_DOOR, Identifier.fromString("minecraft:crimson_door"), BlockDoor::class.java)
            register(
                BlockType.CRIMSON_DOUBLE_SLAB,
                Identifier.fromString("minecraft:crimson_double_slab"),
                BlockDoubleSlab::class.java
            )
            register(BlockType.CRIMSON_FENCE, Identifier.fromString("minecraft:crimson_fence"))
            register(
                BlockType.CRIMSON_FENCE_GATE,
                Identifier.fromString("minecraft:crimson_fence_gate"),
                BlockFenceGate::class.java
            )
            register(BlockType.CRIMSON_FUNGUS, Identifier.fromString("minecraft:crimson_fungus"))
            register(
                BlockType.CRIMSON_HANGING_SIGN,
                Identifier.fromString("minecraft:crimson_hanging_sign"),
                BlockHangingSign::class.java
            )
            register(
                BlockType.CRIMSON_HYPHAE,
                Identifier.fromString("minecraft:crimson_hyphae"),
                BlockCrimsonHyphae::class.java
            )
            register(BlockType.CRIMSON_NYLIUM, Identifier.fromString("minecraft:crimson_nylium"))
            register(BlockType.CRIMSON_PLANKS, Identifier.fromString("minecraft:crimson_planks"))
            register(
                BlockType.CRIMSON_PRESSURE_PLATE,
                Identifier.fromString("minecraft:crimson_pressure_plate"),
                BlockPressurePlate::class.java
            )
            register(BlockType.CRIMSON_ROOTS, Identifier.fromString("minecraft:crimson_roots"))
            register(BlockType.CRIMSON_SLAB, Identifier.fromString("minecraft:crimson_slab"), BlockSlab::class.java)
            register(
                BlockType.CRIMSON_STAIRS,
                Identifier.fromString("minecraft:crimson_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.CRIMSON_STANDING_SIGN,
                Identifier.fromString("minecraft:crimson_standing_sign"),
                BlockStandingSign::class.java
            )
            register(
                BlockType.CRIMSON_STEM,
                Identifier.fromString("minecraft:crimson_stem"),
                BlockCrimsonStem::class.java
            )
            register(
                BlockType.CRIMSON_TRAPDOOR,
                Identifier.fromString("minecraft:crimson_trapdoor"),
                BlockTrapdoor::class.java
            )
            register(
                BlockType.CRIMSON_WALL_SIGN,
                Identifier.fromString("minecraft:crimson_wall_sign"),
                BlockWallSign::class.java
            )
            register(BlockType.CRYING_OBSIDIAN, Identifier.fromString("minecraft:crying_obsidian"))
            register(BlockType.CUT_COPPER, Identifier.fromString("minecraft:cut_copper"))
            register(
                BlockType.CUT_COPPER_SLAB,
                Identifier.fromString("minecraft:cut_copper_slab"),
                BlockSlab::class.java
            )
            register(
                BlockType.CUT_COPPER_STAIRS,
                Identifier.fromString("minecraft:cut_copper_stairs"),
                BlockStairs::class.java
            )
            register(BlockType.CYAN_CANDLE, Identifier.fromString("minecraft:cyan_candle"), BlockCandle::class.java)
            register(
                BlockType.CYAN_CANDLE_CAKE,
                Identifier.fromString("minecraft:cyan_candle_cake"),
                BlockCandleCake::class.java
            )
            register(BlockType.CYAN_CARPET, Identifier.fromString("minecraft:cyan_carpet"))
            register(BlockType.CYAN_CONCRETE, Identifier.fromString("minecraft:cyan_concrete"))
            register(BlockType.CYAN_CONCRETE_POWDER, Identifier.fromString("minecraft:cyan_concrete_powder"))
            register(
                BlockType.CYAN_GLAZED_TERRACOTTA,
                Identifier.fromString("minecraft:cyan_glazed_terracotta"),
                BlockGlazedTerracotta::class.java
            )
            register(
                BlockType.CYAN_SHULKER_BOX,
                Identifier.fromString("minecraft:cyan_shulker_box"),
                BlockShulkerBox::class.java
            )
            register(BlockType.CYAN_STAINED_GLASS, Identifier.fromString("minecraft:cyan_stained_glass"))
            register(BlockType.CYAN_STAINED_GLASS_PANE, Identifier.fromString("minecraft:cyan_stained_glass_pane"))
            register(BlockType.CYAN_TERRACOTTA, Identifier.fromString("minecraft:cyan_terracotta"))
            register(BlockType.CYAN_WOOL, Identifier.fromString("minecraft:cyan_wool"))
            register(
                BlockType.DARK_OAK_BUTTON,
                Identifier.fromString("minecraft:dark_oak_button"),
                BlockButton::class.java
            )
            register(BlockType.DARK_OAK_DOOR, Identifier.fromString("minecraft:dark_oak_door"), BlockDoor::class.java)
            register(BlockType.DARK_OAK_FENCE, Identifier.fromString("minecraft:dark_oak_fence"))
            register(
                BlockType.DARK_OAK_FENCE_GATE,
                Identifier.fromString("minecraft:dark_oak_fence_gate"),
                BlockFenceGate::class.java
            )
            register(
                BlockType.DARK_OAK_HANGING_SIGN,
                Identifier.fromString("minecraft:dark_oak_hanging_sign"),
                BlockHangingSign::class.java
            )
            register(BlockType.DARK_OAK_LOG, Identifier.fromString("minecraft:dark_oak_log"), BlockLog::class.java)
            register(BlockType.DARK_OAK_PLANKS, Identifier.fromString("minecraft:dark_oak_planks"))
            register(
                BlockType.DARK_OAK_PRESSURE_PLATE,
                Identifier.fromString("minecraft:dark_oak_pressure_plate"),
                BlockPressurePlate::class.java
            )
            register(
                BlockType.DARK_OAK_STAIRS,
                Identifier.fromString("minecraft:dark_oak_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.DARK_OAK_TRAPDOOR,
                Identifier.fromString("minecraft:dark_oak_trapdoor"),
                BlockTrapdoor::class.java
            )
            register(
                BlockType.DARK_PRISMARINE_STAIRS,
                Identifier.fromString("minecraft:dark_prismarine_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.DARKOAK_STANDING_SIGN,
                Identifier.fromString("minecraft:darkoak_standing_sign"),
                BlockStandingSign::class.java
            )
            register(
                BlockType.DARKOAK_WALL_SIGN,
                Identifier.fromString("minecraft:darkoak_wall_sign"),
                BlockWallSign::class.java
            )
            register(
                BlockType.DAYLIGHT_DETECTOR,
                Identifier.fromString("minecraft:daylight_detector"),
                BlockDaylightDetector::class.java
            )
            register(
                BlockType.DAYLIGHT_DETECTOR_INVERTED,
                Identifier.fromString("minecraft:daylight_detector_inverted"),
                BlockDaylightDetectorInverted::class.java
            )
            register(BlockType.DEAD_BRAIN_CORAL, Identifier.fromString("minecraft:dead_brain_coral"))
            register(BlockType.DEAD_BUBBLE_CORAL, Identifier.fromString("minecraft:dead_bubble_coral"))
            register(BlockType.DEAD_FIRE_CORAL, Identifier.fromString("minecraft:dead_fire_coral"))
            register(BlockType.DEAD_HORN_CORAL, Identifier.fromString("minecraft:dead_horn_coral"))
            register(BlockType.DEAD_TUBE_CORAL, Identifier.fromString("minecraft:dead_tube_coral"))
            register(BlockType.DEADBUSH, Identifier.fromString("minecraft:deadbush"), BlockDeadBush::class.java)
            register(
                BlockType.DECORATED_POT,
                Identifier.fromString("minecraft:decorated_pot"),
                BlockDecoratedPot::class.java
            )
            register(BlockType.DEEPSLATE, Identifier.fromString("minecraft:deepslate"), BlockDeepslate::class.java)
            register(
                BlockType.DEEPSLATE_BRICK_DOUBLE_SLAB,
                Identifier.fromString("minecraft:deepslate_brick_double_slab"),
                BlockDoubleSlab::class.java
            )
            register(
                BlockType.DEEPSLATE_BRICK_SLAB,
                Identifier.fromString("minecraft:deepslate_brick_slab"),
                BlockSlab::class.java
            )
            register(
                BlockType.DEEPSLATE_BRICK_STAIRS,
                Identifier.fromString("minecraft:deepslate_brick_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.DEEPSLATE_BRICK_WALL,
                Identifier.fromString("minecraft:deepslate_brick_wall"),
                BlockWall::class.java
            )
            register(BlockType.DEEPSLATE_BRICKS, Identifier.fromString("minecraft:deepslate_bricks"))
            register(BlockType.DEEPSLATE_COAL_ORE, Identifier.fromString("minecraft:deepslate_coal_ore"))
            register(BlockType.DEEPSLATE_COPPER_ORE, Identifier.fromString("minecraft:deepslate_copper_ore"))
            register(BlockType.DEEPSLATE_DIAMOND_ORE, Identifier.fromString("minecraft:deepslate_diamond_ore"))
            register(BlockType.DEEPSLATE_EMERALD_ORE, Identifier.fromString("minecraft:deepslate_emerald_ore"))
            register(BlockType.DEEPSLATE_GOLD_ORE, Identifier.fromString("minecraft:deepslate_gold_ore"))
            register(BlockType.DEEPSLATE_IRON_ORE, Identifier.fromString("minecraft:deepslate_iron_ore"))
            register(BlockType.DEEPSLATE_LAPIS_ORE, Identifier.fromString("minecraft:deepslate_lapis_ore"))
            register(BlockType.DEEPSLATE_REDSTONE_ORE, Identifier.fromString("minecraft:deepslate_redstone_ore"))
            register(
                BlockType.DEEPSLATE_TILE_DOUBLE_SLAB,
                Identifier.fromString("minecraft:deepslate_tile_double_slab"),
                BlockDoubleSlab::class.java
            )
            register(
                BlockType.DEEPSLATE_TILE_SLAB,
                Identifier.fromString("minecraft:deepslate_tile_slab"),
                BlockSlab::class.java
            )
            register(
                BlockType.DEEPSLATE_TILE_STAIRS,
                Identifier.fromString("minecraft:deepslate_tile_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.DEEPSLATE_TILE_WALL,
                Identifier.fromString("minecraft:deepslate_tile_wall"),
                BlockWall::class.java
            )
            register(BlockType.DEEPSLATE_TILES, Identifier.fromString("minecraft:deepslate_tiles"))
            register(BlockType.DENY, Identifier.fromString("minecraft:deny"))
            register(
                BlockType.DETECTOR_RAIL,
                Identifier.fromString("minecraft:detector_rail"),
                BlockDetectorRail::class.java
            )
            register(BlockType.DIAMOND_BLOCK, Identifier.fromString("minecraft:diamond_block"))
            register(BlockType.DIAMOND_ORE, Identifier.fromString("minecraft:diamond_ore"))
            register(BlockType.DIORITE, Identifier.fromString("minecraft:diorite"))
            register(
                BlockType.DIORITE_STAIRS,
                Identifier.fromString("minecraft:diorite_stairs"),
                BlockStairs::class.java
            )
            register(BlockType.DIRT, Identifier.fromString("minecraft:dirt"), BlockDirt::class.java)
            register(BlockType.DIRT_WITH_ROOTS, Identifier.fromString("minecraft:dirt_with_roots"))
            register(BlockType.DISPENSER, Identifier.fromString("minecraft:dispenser"), BlockDispenser::class.java)
            register(
                BlockType.DOUBLE_CUT_COPPER_SLAB,
                Identifier.fromString("minecraft:double_cut_copper_slab"),
                BlockDoubleSlab::class.java
            )
            register(
                BlockType.DOUBLE_PLANT,
                Identifier.fromString("minecraft:double_plant"),
                BlockDoublePlant::class.java
            )
            register(
                BlockType.DOUBLE_STONE_BLOCK_SLAB,
                Identifier.fromString("minecraft:double_stone_block_slab"),
                BlockDoubleStoneBlockSlab::class.java
            )
            register(
                BlockType.DOUBLE_STONE_BLOCK_SLAB2,
                Identifier.fromString("minecraft:double_stone_block_slab2"),
                BlockDoubleStoneBlockSlab2::class.java
            )
            register(
                BlockType.DOUBLE_STONE_BLOCK_SLAB3,
                Identifier.fromString("minecraft:double_stone_block_slab3"),
                BlockDoubleStoneBlockSlab3::class.java
            )
            register(
                BlockType.DOUBLE_STONE_BLOCK_SLAB4,
                Identifier.fromString("minecraft:double_stone_block_slab4"),
                BlockDoubleStoneBlockSlab4::class.java
            )
            register(
                BlockType.OAK_DOUBLE_SLAB,
                Identifier.fromString("minecraft:oak_double_slab"),
                BlockDoubleWoodenSlab::class.java
            )
            register(
                BlockType.SPRUCE_DOUBLE_SLAB,
                Identifier.fromString("minecraft:spruce_double_slab"),
                BlockDoubleWoodenSlab::class.java
            )
            register(
                BlockType.BIRCH_DOUBLE_SLAB,
                Identifier.fromString("minecraft:birch_double_slab"),
                BlockDoubleWoodenSlab::class.java
            )
            register(
                BlockType.JUNGLE_DOUBLE_SLAB,
                Identifier.fromString("minecraft:jungle_double_slab"),
                BlockDoubleWoodenSlab::class.java
            )
            register(
                BlockType.ACACIA_DOUBLE_SLAB,
                Identifier.fromString("minecraft:acacia_double_slab"),
                BlockDoubleWoodenSlab::class.java
            )
            register(
                BlockType.DARK_OAK_DOUBLE_SLAB,
                Identifier.fromString("minecraft:dark_oak_double_slab"),
                BlockDoubleWoodenSlab::class.java
            )
            register(BlockType.DRAGON_EGG, Identifier.fromString("minecraft:dragon_egg"))
            register(BlockType.DRIED_KELP_BLOCK, Identifier.fromString("minecraft:dried_kelp_block"))
            register(BlockType.DRIPSTONE_BLOCK, Identifier.fromString("minecraft:dripstone_block"))
            register(BlockType.DROPPER, Identifier.fromString("minecraft:dropper"), BlockDropper::class.java)
            register(BlockType.ELEMENT_0, Identifier.fromString("minecraft:element_0"))
            register(BlockType.ELEMENT_1, Identifier.fromString("minecraft:element_1"))
            register(BlockType.ELEMENT_10, Identifier.fromString("minecraft:element_10"))
            register(BlockType.ELEMENT_100, Identifier.fromString("minecraft:element_100"))
            register(BlockType.ELEMENT_101, Identifier.fromString("minecraft:element_101"))
            register(BlockType.ELEMENT_102, Identifier.fromString("minecraft:element_102"))
            register(BlockType.ELEMENT_103, Identifier.fromString("minecraft:element_103"))
            register(BlockType.ELEMENT_104, Identifier.fromString("minecraft:element_104"))
            register(BlockType.ELEMENT_105, Identifier.fromString("minecraft:element_105"))
            register(BlockType.ELEMENT_106, Identifier.fromString("minecraft:element_106"))
            register(BlockType.ELEMENT_107, Identifier.fromString("minecraft:element_107"))
            register(BlockType.ELEMENT_108, Identifier.fromString("minecraft:element_108"))
            register(BlockType.ELEMENT_109, Identifier.fromString("minecraft:element_109"))
            register(BlockType.ELEMENT_11, Identifier.fromString("minecraft:element_11"))
            register(BlockType.ELEMENT_110, Identifier.fromString("minecraft:element_110"))
            register(BlockType.ELEMENT_111, Identifier.fromString("minecraft:element_111"))
            register(BlockType.ELEMENT_112, Identifier.fromString("minecraft:element_112"))
            register(BlockType.ELEMENT_113, Identifier.fromString("minecraft:element_113"))
            register(BlockType.ELEMENT_114, Identifier.fromString("minecraft:element_114"))
            register(BlockType.ELEMENT_115, Identifier.fromString("minecraft:element_115"))
            register(BlockType.ELEMENT_116, Identifier.fromString("minecraft:element_116"))
            register(BlockType.ELEMENT_117, Identifier.fromString("minecraft:element_117"))
            register(BlockType.ELEMENT_118, Identifier.fromString("minecraft:element_118"))
            register(BlockType.ELEMENT_12, Identifier.fromString("minecraft:element_12"))
            register(BlockType.ELEMENT_13, Identifier.fromString("minecraft:element_13"))
            register(BlockType.ELEMENT_14, Identifier.fromString("minecraft:element_14"))
            register(BlockType.ELEMENT_15, Identifier.fromString("minecraft:element_15"))
            register(BlockType.ELEMENT_16, Identifier.fromString("minecraft:element_16"))
            register(BlockType.ELEMENT_17, Identifier.fromString("minecraft:element_17"))
            register(BlockType.ELEMENT_18, Identifier.fromString("minecraft:element_18"))
            register(BlockType.ELEMENT_19, Identifier.fromString("minecraft:element_19"))
            register(BlockType.ELEMENT_2, Identifier.fromString("minecraft:element_2"))
            register(BlockType.ELEMENT_20, Identifier.fromString("minecraft:element_20"))
            register(BlockType.ELEMENT_21, Identifier.fromString("minecraft:element_21"))
            register(BlockType.ELEMENT_22, Identifier.fromString("minecraft:element_22"))
            register(BlockType.ELEMENT_23, Identifier.fromString("minecraft:element_23"))
            register(BlockType.ELEMENT_24, Identifier.fromString("minecraft:element_24"))
            register(BlockType.ELEMENT_25, Identifier.fromString("minecraft:element_25"))
            register(BlockType.ELEMENT_26, Identifier.fromString("minecraft:element_26"))
            register(BlockType.ELEMENT_27, Identifier.fromString("minecraft:element_27"))
            register(BlockType.ELEMENT_28, Identifier.fromString("minecraft:element_28"))
            register(BlockType.ELEMENT_29, Identifier.fromString("minecraft:element_29"))
            register(BlockType.ELEMENT_3, Identifier.fromString("minecraft:element_3"))
            register(BlockType.ELEMENT_30, Identifier.fromString("minecraft:element_30"))
            register(BlockType.ELEMENT_31, Identifier.fromString("minecraft:element_31"))
            register(BlockType.ELEMENT_32, Identifier.fromString("minecraft:element_32"))
            register(BlockType.ELEMENT_33, Identifier.fromString("minecraft:element_33"))
            register(BlockType.ELEMENT_34, Identifier.fromString("minecraft:element_34"))
            register(BlockType.ELEMENT_35, Identifier.fromString("minecraft:element_35"))
            register(BlockType.ELEMENT_36, Identifier.fromString("minecraft:element_36"))
            register(BlockType.ELEMENT_37, Identifier.fromString("minecraft:element_37"))
            register(BlockType.ELEMENT_38, Identifier.fromString("minecraft:element_38"))
            register(BlockType.ELEMENT_39, Identifier.fromString("minecraft:element_39"))
            register(BlockType.ELEMENT_4, Identifier.fromString("minecraft:element_4"))
            register(BlockType.ELEMENT_40, Identifier.fromString("minecraft:element_40"))
            register(BlockType.ELEMENT_41, Identifier.fromString("minecraft:element_41"))
            register(BlockType.ELEMENT_42, Identifier.fromString("minecraft:element_42"))
            register(BlockType.ELEMENT_43, Identifier.fromString("minecraft:element_43"))
            register(BlockType.ELEMENT_44, Identifier.fromString("minecraft:element_44"))
            register(BlockType.ELEMENT_45, Identifier.fromString("minecraft:element_45"))
            register(BlockType.ELEMENT_46, Identifier.fromString("minecraft:element_46"))
            register(BlockType.ELEMENT_47, Identifier.fromString("minecraft:element_47"))
            register(BlockType.ELEMENT_48, Identifier.fromString("minecraft:element_48"))
            register(BlockType.ELEMENT_49, Identifier.fromString("minecraft:element_49"))
            register(BlockType.ELEMENT_5, Identifier.fromString("minecraft:element_5"))
            register(BlockType.ELEMENT_50, Identifier.fromString("minecraft:element_50"))
            register(BlockType.ELEMENT_51, Identifier.fromString("minecraft:element_51"))
            register(BlockType.ELEMENT_52, Identifier.fromString("minecraft:element_52"))
            register(BlockType.ELEMENT_53, Identifier.fromString("minecraft:element_53"))
            register(BlockType.ELEMENT_54, Identifier.fromString("minecraft:element_54"))
            register(BlockType.ELEMENT_55, Identifier.fromString("minecraft:element_55"))
            register(BlockType.ELEMENT_56, Identifier.fromString("minecraft:element_56"))
            register(BlockType.ELEMENT_57, Identifier.fromString("minecraft:element_57"))
            register(BlockType.ELEMENT_58, Identifier.fromString("minecraft:element_58"))
            register(BlockType.ELEMENT_59, Identifier.fromString("minecraft:element_59"))
            register(BlockType.ELEMENT_6, Identifier.fromString("minecraft:element_6"))
            register(BlockType.ELEMENT_60, Identifier.fromString("minecraft:element_60"))
            register(BlockType.ELEMENT_61, Identifier.fromString("minecraft:element_61"))
            register(BlockType.ELEMENT_62, Identifier.fromString("minecraft:element_62"))
            register(BlockType.ELEMENT_63, Identifier.fromString("minecraft:element_63"))
            register(BlockType.ELEMENT_64, Identifier.fromString("minecraft:element_64"))
            register(BlockType.ELEMENT_65, Identifier.fromString("minecraft:element_65"))
            register(BlockType.ELEMENT_66, Identifier.fromString("minecraft:element_66"))
            register(BlockType.ELEMENT_67, Identifier.fromString("minecraft:element_67"))
            register(BlockType.ELEMENT_68, Identifier.fromString("minecraft:element_68"))
            register(BlockType.ELEMENT_69, Identifier.fromString("minecraft:element_69"))
            register(BlockType.ELEMENT_7, Identifier.fromString("minecraft:element_7"))
            register(BlockType.ELEMENT_70, Identifier.fromString("minecraft:element_70"))
            register(BlockType.ELEMENT_71, Identifier.fromString("minecraft:element_71"))
            register(BlockType.ELEMENT_72, Identifier.fromString("minecraft:element_72"))
            register(BlockType.ELEMENT_73, Identifier.fromString("minecraft:element_73"))
            register(BlockType.ELEMENT_74, Identifier.fromString("minecraft:element_74"))
            register(BlockType.ELEMENT_75, Identifier.fromString("minecraft:element_75"))
            register(BlockType.ELEMENT_76, Identifier.fromString("minecraft:element_76"))
            register(BlockType.ELEMENT_77, Identifier.fromString("minecraft:element_77"))
            register(BlockType.ELEMENT_78, Identifier.fromString("minecraft:element_78"))
            register(BlockType.ELEMENT_79, Identifier.fromString("minecraft:element_79"))
            register(BlockType.ELEMENT_8, Identifier.fromString("minecraft:element_8"))
            register(BlockType.ELEMENT_80, Identifier.fromString("minecraft:element_80"))
            register(BlockType.ELEMENT_81, Identifier.fromString("minecraft:element_81"))
            register(BlockType.ELEMENT_82, Identifier.fromString("minecraft:element_82"))
            register(BlockType.ELEMENT_83, Identifier.fromString("minecraft:element_83"))
            register(BlockType.ELEMENT_84, Identifier.fromString("minecraft:element_84"))
            register(BlockType.ELEMENT_85, Identifier.fromString("minecraft:element_85"))
            register(BlockType.ELEMENT_86, Identifier.fromString("minecraft:element_86"))
            register(BlockType.ELEMENT_87, Identifier.fromString("minecraft:element_87"))
            register(BlockType.ELEMENT_88, Identifier.fromString("minecraft:element_88"))
            register(BlockType.ELEMENT_89, Identifier.fromString("minecraft:element_89"))
            register(BlockType.ELEMENT_9, Identifier.fromString("minecraft:element_9"))
            register(BlockType.ELEMENT_90, Identifier.fromString("minecraft:element_90"))
            register(BlockType.ELEMENT_91, Identifier.fromString("minecraft:element_91"))
            register(BlockType.ELEMENT_92, Identifier.fromString("minecraft:element_92"))
            register(BlockType.ELEMENT_93, Identifier.fromString("minecraft:element_93"))
            register(BlockType.ELEMENT_94, Identifier.fromString("minecraft:element_94"))
            register(BlockType.ELEMENT_95, Identifier.fromString("minecraft:element_95"))
            register(BlockType.ELEMENT_96, Identifier.fromString("minecraft:element_96"))
            register(BlockType.ELEMENT_97, Identifier.fromString("minecraft:element_97"))
            register(BlockType.ELEMENT_98, Identifier.fromString("minecraft:element_98"))
            register(BlockType.ELEMENT_99, Identifier.fromString("minecraft:element_99"))
            register(BlockType.EMERALD_BLOCK, Identifier.fromString("minecraft:emerald_block"))
            register(BlockType.EMERALD_ORE, Identifier.fromString("minecraft:emerald_ore"))
            register(
                BlockType.ENCHANTING_TABLE,
                Identifier.fromString("minecraft:enchanting_table"),
                BlockEnchantingTable::class.java
            )
            register(
                BlockType.END_BRICK_STAIRS,
                Identifier.fromString("minecraft:end_brick_stairs"),
                BlockStairs::class.java
            )
            register(BlockType.END_BRICKS, Identifier.fromString("minecraft:end_bricks"))
            register(BlockType.END_GATEWAY, Identifier.fromString("minecraft:end_gateway"), BlockEndGateway::class.java)
            register(BlockType.END_PORTAL, Identifier.fromString("minecraft:end_portal"))
            register(
                BlockType.END_PORTAL_FRAME,
                Identifier.fromString("minecraft:end_portal_frame"),
                BlockEndPortalFrame::class.java
            )
            register(BlockType.END_ROD, Identifier.fromString("minecraft:end_rod"), BlockEndRod::class.java)
            register(BlockType.END_STONE, Identifier.fromString("minecraft:end_stone"))
            register(BlockType.ENDER_CHEST, Identifier.fromString("minecraft:ender_chest"), BlockEnderChest::class.java)
            register(BlockType.EXPOSED_CHISELED_COPPER, Identifier.fromString("minecraft:exposed_chiseled_copper"))
            register(BlockType.EXPOSED_COPPER, Identifier.fromString("minecraft:exposed_copper"))
            register(
                BlockType.EXPOSED_COPPER_BULB,
                Identifier.fromString("minecraft:exposed_copper_bulb"),
                BlockExposedCopperBulb::class.java
            )
            register(
                BlockType.EXPOSED_COPPER_DOOR,
                Identifier.fromString("minecraft:exposed_copper_door"),
                BlockDoor::class.java
            )
            register(BlockType.EXPOSED_COPPER_GRATE, Identifier.fromString("minecraft:exposed_copper_grate"))
            register(
                BlockType.EXPOSED_COPPER_TRAPDOOR,
                Identifier.fromString("minecraft:exposed_copper_trapdoor"),
                BlockTrapdoor::class.java
            )
            register(BlockType.EXPOSED_CUT_COPPER, Identifier.fromString("minecraft:exposed_cut_copper"))
            register(
                BlockType.EXPOSED_CUT_COPPER_SLAB,
                Identifier.fromString("minecraft:exposed_cut_copper_slab"),
                BlockSlab::class.java
            )
            register(
                BlockType.EXPOSED_CUT_COPPER_STAIRS,
                Identifier.fromString("minecraft:exposed_cut_copper_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.EXPOSED_DOUBLE_CUT_COPPER_SLAB,
                Identifier.fromString("minecraft:exposed_double_cut_copper_slab"),
                BlockSlab::class.java
            )
            register(BlockType.FARMLAND, Identifier.fromString("minecraft:farmland"), BlockFarmland::class.java)
            register(
                BlockType.FENCE_GATE,
                Identifier.fromString("minecraft:fence_gate"),
                BlockFenceGate::class.java
            )
            register(BlockType.FIRE, Identifier.fromString("minecraft:fire"), BlockFire::class.java)
            register(BlockType.FIRE_CORAL, Identifier.fromString("minecraft:fire_coral"))
            register(BlockType.FLETCHING_TABLE, Identifier.fromString("minecraft:fletching_table"))
            register(BlockType.FLOWER_POT, Identifier.fromString("minecraft:flower_pot"), BlockFlowerPot::class.java)
            register(BlockType.FLOWERING_AZALEA, Identifier.fromString("minecraft:flowering_azalea"))
            register(
                BlockType.FLOWING_LAVA,
                Identifier.fromString("minecraft:flowing_lava"),
                BlockFlowingLava::class.java
            )
            register(
                BlockType.FLOWING_WATER,
                Identifier.fromString("minecraft:flowing_water"),
                BlockFlowingWater::class.java
            )
            register(BlockType.FRAME, Identifier.fromString("minecraft:frame"), BlockFrame::class.java)
            register(BlockType.FROG_SPAWN, Identifier.fromString("minecraft:frog_spawn"))
            register(BlockType.FROSTED_ICE, Identifier.fromString("minecraft:frosted_ice"), BlockFrostedIce::class.java)
            register(BlockType.FURNACE, Identifier.fromString("minecraft:furnace"), BlockFurnace::class.java)
            register(BlockType.GILDED_BLACKSTONE, Identifier.fromString("minecraft:gilded_blackstone"))
            register(BlockType.GLASS, Identifier.fromString("minecraft:glass"))
            register(BlockType.GLASS_PANE, Identifier.fromString("minecraft:glass_pane"))
            register(BlockType.GLOW_FRAME, Identifier.fromString("minecraft:glow_frame"), BlockGlowFrame::class.java)
            register(BlockType.GLOW_LICHEN, Identifier.fromString("minecraft:glow_lichen"), BlockGlowLichen::class.java)
            register(BlockType.GLOWINGOBSIDIAN, Identifier.fromString("minecraft:glowingobsidian"))
            register(BlockType.GLOWSTONE, Identifier.fromString("minecraft:glowstone"))
            register(BlockType.GOLD_BLOCK, Identifier.fromString("minecraft:gold_block"))
            register(BlockType.GOLD_ORE, Identifier.fromString("minecraft:gold_ore"))
            register(BlockType.GOLDEN_RAIL, Identifier.fromString("minecraft:golden_rail"), BlockGoldenRail::class.java)
            register(BlockType.GRANITE, Identifier.fromString("minecraft:granite"))
            register(
                BlockType.GRANITE_STAIRS,
                Identifier.fromString("minecraft:granite_stairs"),
                BlockStairs::class.java
            )
            register(BlockType.GRASS_BLOCK, Identifier.fromString("minecraft:grass_block"))
            register(BlockType.GRASS_PATH, Identifier.fromString("minecraft:grass_path"))
            register(BlockType.GRAVEL, Identifier.fromString("minecraft:gravel"))
            register(BlockType.GRAY_CANDLE, Identifier.fromString("minecraft:gray_candle"), BlockCandle::class.java)
            register(
                BlockType.GRAY_CANDLE_CAKE,
                Identifier.fromString("minecraft:gray_candle_cake"),
                BlockCandleCake::class.java
            )
            register(BlockType.GRAY_CARPET, Identifier.fromString("minecraft:gray_carpet"))
            register(BlockType.GRAY_CONCRETE, Identifier.fromString("minecraft:gray_concrete"))
            register(BlockType.GRAY_CONCRETE_POWDER, Identifier.fromString("minecraft:gray_concrete_powder"))
            register(
                BlockType.GRAY_GLAZED_TERRACOTTA,
                Identifier.fromString("minecraft:gray_glazed_terracotta"),
                BlockGlazedTerracotta::class.java
            )
            register(
                BlockType.GRAY_SHULKER_BOX,
                Identifier.fromString("minecraft:gray_shulker_box"),
                BlockShulkerBox::class.java
            )
            register(BlockType.GRAY_STAINED_GLASS, Identifier.fromString("minecraft:gray_stained_glass"))
            register(BlockType.GRAY_STAINED_GLASS_PANE, Identifier.fromString("minecraft:gray_stained_glass_pane"))
            register(BlockType.GRAY_TERRACOTTA, Identifier.fromString("minecraft:gray_terracotta"))
            register(BlockType.GRAY_WOOL, Identifier.fromString("minecraft:gray_wool"))
            register(BlockType.GREEN_CANDLE, Identifier.fromString("minecraft:green_candle"), BlockCandle::class.java)
            register(
                BlockType.GREEN_CANDLE_CAKE,
                Identifier.fromString("minecraft:green_candle_cake"),
                BlockCandleCake::class.java
            )
            register(BlockType.GREEN_CARPET, Identifier.fromString("minecraft:green_carpet"))
            register(BlockType.GREEN_CONCRETE, Identifier.fromString("minecraft:green_concrete"))
            register(BlockType.GREEN_CONCRETE_POWDER, Identifier.fromString("minecraft:green_concrete_powder"))
            register(
                BlockType.GREEN_GLAZED_TERRACOTTA,
                Identifier.fromString("minecraft:green_glazed_terracotta"),
                BlockGlazedTerracotta::class.java
            )
            register(
                BlockType.GREEN_SHULKER_BOX,
                Identifier.fromString("minecraft:green_shulker_box"),
                BlockShulkerBox::class.java
            )
            register(BlockType.GREEN_STAINED_GLASS, Identifier.fromString("minecraft:green_stained_glass"))
            register(BlockType.GREEN_STAINED_GLASS_PANE, Identifier.fromString("minecraft:green_stained_glass_pane"))
            register(BlockType.GREEN_TERRACOTTA, Identifier.fromString("minecraft:green_terracotta"))
            register(BlockType.GREEN_WOOL, Identifier.fromString("minecraft:green_wool"))
            register(BlockType.GRINDSTONE, Identifier.fromString("minecraft:grindstone"), BlockGrindstone::class.java)
            register(BlockType.HANGING_ROOTS, Identifier.fromString("minecraft:hanging_roots"))
            register(BlockType.HARD_GLASS, Identifier.fromString("minecraft:hard_glass"))
            register(BlockType.HARD_GLASS_PANE, Identifier.fromString("minecraft:hard_glass_pane"))
            register(
                BlockType.HARD_BLACK_STAINED_GLASS,
                Identifier.fromString("minecraft:hard_black_stained_glass")
            )
            register(
                BlockType.HARD_BLUE_STAINED_GLASS,
                Identifier.fromString("minecraft:hard_blue_stained_glass")
            )
            register(
                BlockType.HARD_BROWN_STAINED_GLASS,
                Identifier.fromString("minecraft:hard_brown_stained_glass")
            )
            register(
                BlockType.HARD_CYAN_STAINED_GLASS,
                Identifier.fromString("minecraft:hard_cyan_stained_glass")
            )
            register(
                BlockType.HARD_GRAY_STAINED_GLASS,
                Identifier.fromString("minecraft:hard_gray_stained_glass")
            )
            register(
                BlockType.HARD_GREEN_STAINED_GLASS,
                Identifier.fromString("minecraft:hard_green_stained_glass")
            )
            register(
                BlockType.HARD_LIGHT_BLUE_STAINED_GLASS,
                Identifier.fromString("minecraft:hard_light_blue_stained_glass")
            )
            register(
                BlockType.HARD_LIGHT_GRAY_STAINED_GLASS,
                Identifier.fromString("minecraft:hard_light_gray_stained_glass")
            )
            register(
                BlockType.HARD_LIME_STAINED_GLASS,
                Identifier.fromString("minecraft:hard_lime_stained_glass")
            )
            register(
                BlockType.HARD_MAGENTA_STAINED_GLASS,
                Identifier.fromString("minecraft:hard_magenta_stained_glass")
            )
            register(
                BlockType.HARD_ORANGE_STAINED_GLASS,
                Identifier.fromString("minecraft:hard_orange_stained_glass")
            )
            register(
                BlockType.HARD_PINK_STAINED_GLASS,
                Identifier.fromString("minecraft:hard_pink_stained_glass")
            )
            register(
                BlockType.HARD_PURPLE_STAINED_GLASS,
                Identifier.fromString("minecraft:hard_purple_stained_glass")
            )
            register(
                BlockType.HARD_RED_STAINED_GLASS,
                Identifier.fromString("minecraft:hard_red_stained_glass")
            )
            register(
                BlockType.HARD_WHITE_STAINED_GLASS,
                Identifier.fromString("minecraft:hard_white_stained_glass")
            )
            register(
                BlockType.HARD_YELLOW_STAINED_GLASS,
                Identifier.fromString("minecraft:hard_yellow_stained_glass")
            )
            register(
                BlockType.HARD_BLACK_STAINED_GLASS_PANE,
                Identifier.fromString("minecraft:hard_black_stained_glass_pane")
            )
            register(
                BlockType.HARD_BLUE_STAINED_GLASS_PANE,
                Identifier.fromString("minecraft:hard_blue_stained_glass_pane")
            )
            register(
                BlockType.HARD_BROWN_STAINED_GLASS_PANE,
                Identifier.fromString("minecraft:hard_brown_stained_glass_pane")
            )
            register(
                BlockType.HARD_CYAN_STAINED_GLASS_PANE,
                Identifier.fromString("minecraft:hard_cyan_stained_glass_pane")
            )
            register(
                BlockType.HARD_GRAY_STAINED_GLASS_PANE,
                Identifier.fromString("minecraft:hard_gray_stained_glass_pane")
            )
            register(
                BlockType.HARD_GREEN_STAINED_GLASS_PANE,
                Identifier.fromString("minecraft:hard_green_stained_glass_pane")
            )
            register(
                BlockType.HARD_LIGHT_BLUE_STAINED_GLASS_PANE,
                Identifier.fromString("minecraft:hard_light_blue_stained_glass_pane")
            )
            register(
                BlockType.HARD_LIGHT_GRAY_STAINED_GLASS_PANE,
                Identifier.fromString("minecraft:hard_light_gray_stained_glass_pane")
            )
            register(
                BlockType.HARD_LIME_STAINED_GLASS_PANE,
                Identifier.fromString("minecraft:hard_lime_stained_glass_pane")
            )
            register(
                BlockType.HARD_MAGENTA_STAINED_GLASS_PANE,
                Identifier.fromString("minecraft:hard_magenta_stained_glass_pane")
            )
            register(
                BlockType.HARD_ORANGE_STAINED_GLASS_PANE,
                Identifier.fromString("minecraft:hard_orange_stained_glass_pane")
            )
            register(
                BlockType.HARD_PINK_STAINED_GLASS_PANE,
                Identifier.fromString("minecraft:hard_pink_stained_glass_pane")
            )
            register(
                BlockType.HARD_PURPLE_STAINED_GLASS_PANE,
                Identifier.fromString("minecraft:hard_purple_stained_glass_pane")
            )
            register(
                BlockType.HARD_RED_STAINED_GLASS_PANE,
                Identifier.fromString("minecraft:hard_red_stained_glass_pane")
            )
            register(
                BlockType.HARD_WHITE_STAINED_GLASS_PANE,
                Identifier.fromString("minecraft:hard_white_stained_glass_pane")
            )
            register(
                BlockType.HARD_YELLOW_STAINED_GLASS_PANE,
                Identifier.fromString("minecraft:hard_yellow_stained_glass_pane")
            )
            register(BlockType.HARDENED_CLAY, Identifier.fromString("minecraft:hardened_clay"))
            register(BlockType.HAY_BLOCK, Identifier.fromString("minecraft:hay_block"), BlockHayBlock::class.java)
            register(
                BlockType.HEAVY_WEIGHTED_PRESSURE_PLATE,
                Identifier.fromString("minecraft:heavy_weighted_pressure_plate"),
                BlockPressurePlate::class.java
            )
            register(BlockType.HONEY_BLOCK, Identifier.fromString("minecraft:honey_block"))
            register(BlockType.HONEYCOMB_BLOCK, Identifier.fromString("minecraft:honeycomb_block"))
            register(BlockType.HOPPER, Identifier.fromString("minecraft:hopper"), BlockHopper::class.java)
            register(BlockType.HORN_CORAL, Identifier.fromString("minecraft:horn_coral"))
            register(BlockType.ICE, Identifier.fromString("minecraft:ice"))
            register(
                BlockType.INFESTED_DEEPSLATE,
                Identifier.fromString("minecraft:infested_deepslate"),
                BlockInfestedDeepslate::class.java
            )
            register(BlockType.INFO_UPDATE, Identifier.fromString("minecraft:info_update"))
            register(BlockType.INFO_UPDATE2, Identifier.fromString("minecraft:info_update2"))
            register(BlockType.INVISIBLE_BEDROCK, Identifier.fromString("minecraft:invisible_bedrock"))
            register(BlockType.IRON_BARS, Identifier.fromString("minecraft:iron_bars"))
            register(BlockType.IRON_BLOCK, Identifier.fromString("minecraft:iron_block"))
            register(BlockType.IRON_DOOR, Identifier.fromString("minecraft:iron_door"), BlockDoor::class.java)
            register(BlockType.IRON_ORE, Identifier.fromString("minecraft:iron_ore"))
            register(
                BlockType.IRON_TRAPDOOR,
                Identifier.fromString("minecraft:iron_trapdoor"),
                BlockTrapdoor::class.java
            )
            register(BlockType.JIGSAW, Identifier.fromString("minecraft:jigsaw"), BlockJigsaw::class.java)
            register(BlockType.JUKEBOX, Identifier.fromString("minecraft:jukebox"), BlockJukebox::class.java)
            register(BlockType.JUNGLE_BUTTON, Identifier.fromString("minecraft:jungle_button"), BlockButton::class.java)
            register(BlockType.JUNGLE_DOOR, Identifier.fromString("minecraft:jungle_door"), BlockDoor::class.java)
            register(BlockType.JUNGLE_FENCE, Identifier.fromString("minecraft:jungle_fence"))
            register(
                BlockType.JUNGLE_FENCE_GATE,
                Identifier.fromString("minecraft:jungle_fence_gate"),
                BlockFenceGate::class.java
            )
            register(
                BlockType.JUNGLE_HANGING_SIGN,
                Identifier.fromString("minecraft:jungle_hanging_sign"),
                BlockHangingSign::class.java
            )
            register(BlockType.JUNGLE_LOG, Identifier.fromString("minecraft:jungle_log"), BlockLog::class.java)
            register(BlockType.JUNGLE_PLANKS, Identifier.fromString("minecraft:jungle_planks"))
            register(
                BlockType.JUNGLE_PRESSURE_PLATE,
                Identifier.fromString("minecraft:jungle_pressure_plate"),
                BlockPressurePlate::class.java
            )
            register(BlockType.JUNGLE_STAIRS, Identifier.fromString("minecraft:jungle_stairs"), BlockStairs::class.java)
            register(
                BlockType.JUNGLE_STANDING_SIGN,
                Identifier.fromString("minecraft:jungle_standing_sign"),
                BlockStandingSign::class.java
            )
            register(
                BlockType.JUNGLE_TRAPDOOR,
                Identifier.fromString("minecraft:jungle_trapdoor"),
                BlockTrapdoor::class.java
            )
            register(
                BlockType.JUNGLE_WALL_SIGN,
                Identifier.fromString("minecraft:jungle_wall_sign"),
                BlockWallSign::class.java
            )
            register(BlockType.KELP, Identifier.fromString("minecraft:kelp"), BlockKelp::class.java)
            register(BlockType.LADDER, Identifier.fromString("minecraft:ladder"), BlockLadder::class.java)
            register(BlockType.LANTERN, Identifier.fromString("minecraft:lantern"), BlockLantern::class.java)
            register(BlockType.LAPIS_BLOCK, Identifier.fromString("minecraft:lapis_block"))
            register(BlockType.LAPIS_ORE, Identifier.fromString("minecraft:lapis_ore"))
            register(
                BlockType.LARGE_AMETHYST_BUD,
                Identifier.fromString("minecraft:large_amethyst_bud"),
                BlockLargeAmethystBud::class.java
            )
            register(BlockType.LAVA, Identifier.fromString("minecraft:lava"), BlockLava::class.java)
            register(BlockType.OAK_LEAVES, Identifier.fromString("minecraft:oak_leaves"), BlockLeaves::class.java)
            register(BlockType.SPRUCE_LEAVES, Identifier.fromString("minecraft:spruce_leaves"), BlockLeaves::class.java)
            register(BlockType.BIRCH_LEAVES, Identifier.fromString("minecraft:birch_leaves"), BlockLeaves::class.java)
            register(BlockType.JUNGLE_LEAVES, Identifier.fromString("minecraft:jungle_leaves"), BlockLeaves::class.java)
            register(BlockType.ACACIA_LEAVES, Identifier.fromString("minecraft:acacia_leaves"), BlockLeaves2::class.java)
            register(BlockType.DARK_OAK_LEAVES, Identifier.fromString("minecraft:dark_oak_leaves"), BlockLeaves2::class.java)
            register(BlockType.LECTERN, Identifier.fromString("minecraft:lectern"), BlockLectern::class.java)
            register(BlockType.LEVER, Identifier.fromString("minecraft:lever"), BlockLever::class.java)
            register(BlockType.LIGHT_BLOCK, Identifier.fromString("minecraft:light_block"), BlockLightBlock::class.java)
            register(
                BlockType.LIGHT_BLUE_CANDLE,
                Identifier.fromString("minecraft:light_blue_candle"),
                BlockCandle::class.java
            )
            register(
                BlockType.LIGHT_BLUE_CANDLE_CAKE,
                Identifier.fromString("minecraft:light_blue_candle_cake"),
                BlockCandleCake::class.java
            )
            register(BlockType.LIGHT_BLUE_CARPET, Identifier.fromString("minecraft:light_blue_carpet"))
            register(BlockType.LIGHT_BLUE_CONCRETE, Identifier.fromString("minecraft:light_blue_concrete"))
            register(
                BlockType.LIGHT_BLUE_CONCRETE_POWDER,
                Identifier.fromString("minecraft:light_blue_concrete_powder")
            )
            register(
                BlockType.LIGHT_BLUE_GLAZED_TERRACOTTA,
                Identifier.fromString("minecraft:light_blue_glazed_terracotta"),
                BlockGlazedTerracotta::class.java
            )
            register(
                BlockType.LIGHT_BLUE_SHULKER_BOX,
                Identifier.fromString("minecraft:light_blue_shulker_box"),
                BlockShulkerBox::class.java
            )
            register(BlockType.LIGHT_BLUE_STAINED_GLASS, Identifier.fromString("minecraft:light_blue_stained_glass"))
            register(
                BlockType.LIGHT_BLUE_STAINED_GLASS_PANE,
                Identifier.fromString("minecraft:light_blue_stained_glass_pane")
            )
            register(BlockType.LIGHT_BLUE_TERRACOTTA, Identifier.fromString("minecraft:light_blue_terracotta"))
            register(BlockType.LIGHT_BLUE_WOOL, Identifier.fromString("minecraft:light_blue_wool"))
            register(
                BlockType.LIGHT_GRAY_CANDLE,
                Identifier.fromString("minecraft:light_gray_candle"),
                BlockCandle::class.java
            )
            register(
                BlockType.LIGHT_GRAY_CANDLE_CAKE,
                Identifier.fromString("minecraft:light_gray_candle_cake"),
                BlockCandleCake::class.java
            )
            register(BlockType.LIGHT_GRAY_CARPET, Identifier.fromString("minecraft:light_gray_carpet"))
            register(BlockType.LIGHT_GRAY_CONCRETE, Identifier.fromString("minecraft:light_gray_concrete"))
            register(
                BlockType.LIGHT_GRAY_CONCRETE_POWDER,
                Identifier.fromString("minecraft:light_gray_concrete_powder")
            )
            register(
                BlockType.LIGHT_GRAY_SHULKER_BOX,
                Identifier.fromString("minecraft:light_gray_shulker_box"),
                BlockShulkerBox::class.java
            )
            register(BlockType.LIGHT_GRAY_STAINED_GLASS, Identifier.fromString("minecraft:light_gray_stained_glass"))
            register(
                BlockType.LIGHT_GRAY_STAINED_GLASS_PANE,
                Identifier.fromString("minecraft:light_gray_stained_glass_pane")
            )
            register(BlockType.LIGHT_GRAY_TERRACOTTA, Identifier.fromString("minecraft:light_gray_terracotta"))
            register(BlockType.LIGHT_GRAY_WOOL, Identifier.fromString("minecraft:light_gray_wool"))
            register(
                BlockType.LIGHT_WEIGHTED_PRESSURE_PLATE,
                Identifier.fromString("minecraft:light_weighted_pressure_plate"),
                BlockPressurePlate::class.java
            )
            register(
                BlockType.LIGHTNING_ROD,
                Identifier.fromString("minecraft:lightning_rod"),
                BlockLightningRod::class.java
            )
            register(BlockType.LIME_CANDLE, Identifier.fromString("minecraft:lime_candle"), BlockCandle::class.java)
            register(
                BlockType.LIME_CANDLE_CAKE,
                Identifier.fromString("minecraft:lime_candle_cake"),
                BlockCandleCake::class.java
            )
            register(BlockType.LIME_CARPET, Identifier.fromString("minecraft:lime_carpet"))
            register(BlockType.LIME_CONCRETE, Identifier.fromString("minecraft:lime_concrete"))
            register(BlockType.LIME_CONCRETE_POWDER, Identifier.fromString("minecraft:lime_concrete_powder"))
            register(
                BlockType.LIME_GLAZED_TERRACOTTA,
                Identifier.fromString("minecraft:lime_glazed_terracotta"),
                BlockGlazedTerracotta::class.java
            )
            register(
                BlockType.LIME_SHULKER_BOX,
                Identifier.fromString("minecraft:lime_shulker_box"),
                BlockShulkerBox::class.java
            )
            register(BlockType.LIME_STAINED_GLASS, Identifier.fromString("minecraft:lime_stained_glass"))
            register(BlockType.LIME_STAINED_GLASS_PANE, Identifier.fromString("minecraft:lime_stained_glass_pane"))
            register(BlockType.LIME_TERRACOTTA, Identifier.fromString("minecraft:lime_terracotta"))
            register(BlockType.LIME_WOOL, Identifier.fromString("minecraft:lime_wool"))
            register(
                BlockType.LIT_BLAST_FURNACE,
                Identifier.fromString("minecraft:lit_blast_furnace"),
                BlockLitBlastFurnace::class.java
            )
            register(
                BlockType.LIT_DEEPSLATE_REDSTONE_ORE,
                Identifier.fromString("minecraft:lit_deepslate_redstone_ore")
            )
            register(BlockType.LIT_FURNACE, Identifier.fromString("minecraft:lit_furnace"), BlockLitFurnace::class.java)
            register(BlockType.LIT_PUMPKIN, Identifier.fromString("minecraft:lit_pumpkin"), BlockLitPumpkin::class.java)
            register(BlockType.LIT_REDSTONE_LAMP, Identifier.fromString("minecraft:lit_redstone_lamp"))
            register(BlockType.LIT_REDSTONE_ORE, Identifier.fromString("minecraft:lit_redstone_ore"))
            register(BlockType.LIT_SMOKER, Identifier.fromString("minecraft:lit_smoker"), BlockLitSmoker::class.java)
            register(BlockType.LODESTONE, Identifier.fromString("minecraft:lodestone"), BlockLodestone::class.java)
            register(BlockType.LOOM, Identifier.fromString("minecraft:loom"), BlockLoom::class.java)
            register(
                BlockType.MAGENTA_CANDLE,
                Identifier.fromString("minecraft:magenta_candle"),
                BlockCandle::class.java
            )
            register(
                BlockType.MAGENTA_CANDLE_CAKE,
                Identifier.fromString("minecraft:magenta_candle_cake"),
                BlockCandleCake::class.java
            )
            register(BlockType.MAGENTA_CARPET, Identifier.fromString("minecraft:magenta_carpet"))
            register(BlockType.MAGENTA_CONCRETE, Identifier.fromString("minecraft:magenta_concrete"))
            register(BlockType.MAGENTA_CONCRETE_POWDER, Identifier.fromString("minecraft:magenta_concrete_powder"))
            register(
                BlockType.MAGENTA_GLAZED_TERRACOTTA,
                Identifier.fromString("minecraft:magenta_glazed_terracotta"),
                BlockGlazedTerracotta::class.java
            )
            register(
                BlockType.MAGENTA_SHULKER_BOX,
                Identifier.fromString("minecraft:magenta_shulker_box"),
                BlockShulkerBox::class.java
            )
            register(BlockType.MAGENTA_STAINED_GLASS, Identifier.fromString("minecraft:magenta_stained_glass"))
            register(
                BlockType.MAGENTA_STAINED_GLASS_PANE,
                Identifier.fromString("minecraft:magenta_stained_glass_pane")
            )
            register(BlockType.MAGENTA_TERRACOTTA, Identifier.fromString("minecraft:magenta_terracotta"))
            register(BlockType.MAGENTA_WOOL, Identifier.fromString("minecraft:magenta_wool"))
            register(BlockType.MAGMA, Identifier.fromString("minecraft:magma"))
            register(
                BlockType.MANGROVE_BUTTON,
                Identifier.fromString("minecraft:mangrove_button"),
                BlockButton::class.java
            )
            register(BlockType.MANGROVE_DOOR, Identifier.fromString("minecraft:mangrove_door"), BlockDoor::class.java)
            register(
                BlockType.MANGROVE_DOUBLE_SLAB,
                Identifier.fromString("minecraft:mangrove_double_slab"),
                BlockDoubleSlab::class.java
            )
            register(BlockType.MANGROVE_FENCE, Identifier.fromString("minecraft:mangrove_fence"))
            register(
                BlockType.MANGROVE_FENCE_GATE,
                Identifier.fromString("minecraft:mangrove_fence_gate"),
                BlockFenceGate::class.java
            )
            register(
                BlockType.MANGROVE_HANGING_SIGN,
                Identifier.fromString("minecraft:mangrove_hanging_sign"),
                BlockHangingSign::class.java
            )
            register(
                BlockType.MANGROVE_LEAVES,
                Identifier.fromString("minecraft:mangrove_leaves"),
                BlockMangroveLeaves::class.java
            )
            register(BlockType.MANGROVE_LOG, Identifier.fromString("minecraft:mangrove_log"), BlockLog::class.java)
            register(BlockType.MANGROVE_PLANKS, Identifier.fromString("minecraft:mangrove_planks"))
            register(
                BlockType.MANGROVE_PRESSURE_PLATE,
                Identifier.fromString("minecraft:mangrove_pressure_plate"),
                BlockPressurePlate::class.java
            )
            register(
                BlockType.MANGROVE_PROPAGULE,
                Identifier.fromString("minecraft:mangrove_propagule"),
                BlockMangrovePropagule::class.java
            )
            register(BlockType.MANGROVE_ROOTS, Identifier.fromString("minecraft:mangrove_roots"))
            register(BlockType.MANGROVE_SLAB, Identifier.fromString("minecraft:mangrove_slab"), BlockSlab::class.java)
            register(
                BlockType.MANGROVE_STAIRS,
                Identifier.fromString("minecraft:mangrove_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.MANGROVE_STANDING_SIGN,
                Identifier.fromString("minecraft:mangrove_standing_sign"),
                BlockStandingSign::class.java
            )
            register(
                BlockType.MANGROVE_TRAPDOOR,
                Identifier.fromString("minecraft:mangrove_trapdoor"),
                BlockTrapdoor::class.java
            )
            register(
                BlockType.MANGROVE_WALL_SIGN,
                Identifier.fromString("minecraft:mangrove_wall_sign"),
                BlockWallSign::class.java
            )
            register(
                BlockType.MANGROVE_WOOD,
                Identifier.fromString("minecraft:mangrove_wood"),
                BlockMangroveWood::class.java
            )
            register(
                BlockType.MEDIUM_AMETHYST_BUD,
                Identifier.fromString("minecraft:medium_amethyst_bud"),
                BlockMediumAmethystBud::class.java
            )
            register(BlockType.MELON_BLOCK, Identifier.fromString("minecraft:melon_block"))
            register(BlockType.MELON_STEM, Identifier.fromString("minecraft:melon_stem"), BlockMelonStem::class.java)
            register(BlockType.MOB_SPAWNER, Identifier.fromString("minecraft:mob_spawner"), BlockMobSpawner::class.java)
            register(BlockType.MONSTER_EGG, Identifier.fromString("minecraft:monster_egg"), BlockMonsterEgg::class.java)
            register(BlockType.MOSS_BLOCK, Identifier.fromString("minecraft:moss_block"))
            register(BlockType.MOSS_CARPET, Identifier.fromString("minecraft:moss_carpet"))
            register(BlockType.MOSSY_COBBLESTONE, Identifier.fromString("minecraft:mossy_cobblestone"))
            register(
                BlockType.MOSSY_COBBLESTONE_STAIRS,
                Identifier.fromString("minecraft:mossy_cobblestone_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.MOSSY_STONE_BRICK_STAIRS,
                Identifier.fromString("minecraft:mossy_stone_brick_stairs"),
                BlockStairs::class.java
            )
            register(BlockType.MOVING_BLOCK, Identifier.fromString("minecraft:moving_block"), BlockMovingBlock::class.java)
            register(BlockType.MUD, Identifier.fromString("minecraft:mud"))
            register(
                BlockType.MUD_BRICK_DOUBLE_SLAB,
                Identifier.fromString("minecraft:mud_brick_double_slab"),
                BlockDoubleSlab::class.java
            )
            register(BlockType.MUD_BRICK_SLAB, Identifier.fromString("minecraft:mud_brick_slab"), BlockSlab::class.java)
            register(
                BlockType.MUD_BRICK_STAIRS,
                Identifier.fromString("minecraft:mud_brick_stairs"),
                BlockStairs::class.java
            )
            register(BlockType.MUD_BRICK_WALL, Identifier.fromString("minecraft:mud_brick_wall"), BlockWall::class.java)
            register(BlockType.MUD_BRICKS, Identifier.fromString("minecraft:mud_bricks"))
            register(
                BlockType.MUDDY_MANGROVE_ROOTS,
                Identifier.fromString("minecraft:muddy_mangrove_roots"),
                BlockMuddyMangroveRoots::class.java
            )
            register(BlockType.MYCELIUM, Identifier.fromString("minecraft:mycelium"))
            register(BlockType.NETHER_BRICK, Identifier.fromString("minecraft:nether_brick"))
            register(BlockType.NETHER_BRICK_FENCE, Identifier.fromString("minecraft:nether_brick_fence"))
            register(
                BlockType.NETHER_BRICK_STAIRS,
                Identifier.fromString("minecraft:nether_brick_stairs"),
                BlockStairs::class.java
            )
            register(BlockType.NETHER_GOLD_ORE, Identifier.fromString("minecraft:nether_gold_ore"))
            register(BlockType.NETHER_SPROUTS, Identifier.fromString("minecraft:nether_sprouts"))
            register(BlockType.NETHER_WART, Identifier.fromString("minecraft:nether_wart"), BlockNetherWart::class.java)
            register(BlockType.NETHER_WART_BLOCK, Identifier.fromString("minecraft:nether_wart_block"))
            register(BlockType.NETHERITE_BLOCK, Identifier.fromString("minecraft:netherite_block"))
            register(BlockType.NETHERRACK, Identifier.fromString("minecraft:netherrack"))
            register(BlockType.NETHERREACTOR, Identifier.fromString("minecraft:netherreactor"))
            register(
                BlockType.NORMAL_STONE_STAIRS,
                Identifier.fromString("minecraft:normal_stone_stairs"),
                BlockStairs::class.java
            )
            register(BlockType.NOTEBLOCK, Identifier.fromString("minecraft:noteblock"))
            register(BlockType.OAK_FENCE, Identifier.fromString("minecraft:oak_fence"))
            register(
                BlockType.OAK_HANGING_SIGN,
                Identifier.fromString("minecraft:oak_hanging_sign"),
                BlockHangingSign::class.java
            )
            register(BlockType.OAK_LOG, Identifier.fromString("minecraft:oak_log"), BlockLog::class.java)
            register(BlockType.OAK_PLANKS, Identifier.fromString("minecraft:oak_planks"))
            register(BlockType.OAK_STAIRS, Identifier.fromString("minecraft:oak_stairs"), BlockStairs::class.java)
            register(BlockType.OBSERVER, Identifier.fromString("minecraft:observer"), BlockObserver::class.java)
            register(BlockType.OBSIDIAN, Identifier.fromString("minecraft:obsidian"))
            register(
                BlockType.OCHRE_FROGLIGHT,
                Identifier.fromString("minecraft:ochre_froglight"),
                BlockOchreFroglight::class.java
            )
            register(BlockType.ORANGE_CANDLE, Identifier.fromString("minecraft:orange_candle"), BlockCandle::class.java)
            register(
                BlockType.ORANGE_CANDLE_CAKE,
                Identifier.fromString("minecraft:orange_candle_cake"),
                BlockCandleCake::class.java
            )
            register(BlockType.ORANGE_CARPET, Identifier.fromString("minecraft:orange_carpet"))
            register(BlockType.ORANGE_CONCRETE, Identifier.fromString("minecraft:orange_concrete"))
            register(BlockType.ORANGE_CONCRETE_POWDER, Identifier.fromString("minecraft:orange_concrete_powder"))
            register(
                BlockType.ORANGE_GLAZED_TERRACOTTA,
                Identifier.fromString("minecraft:orange_glazed_terracotta"),
                BlockGlazedTerracotta::class.java
            )
            register(
                BlockType.ORANGE_SHULKER_BOX,
                Identifier.fromString("minecraft:orange_shulker_box"),
                BlockShulkerBox::class.java
            )
            register(BlockType.ORANGE_STAINED_GLASS, Identifier.fromString("minecraft:orange_stained_glass"))
            register(BlockType.ORANGE_STAINED_GLASS_PANE, Identifier.fromString("minecraft:orange_stained_glass_pane"))
            register(BlockType.ORANGE_TERRACOTTA, Identifier.fromString("minecraft:orange_terracotta"))
            register(BlockType.ORANGE_WOOL, Identifier.fromString("minecraft:orange_wool"))
            register(BlockType.OXIDIZED_CHISELED_COPPER, Identifier.fromString("minecraft:oxidized_chiseled_copper"))
            register(BlockType.OXIDIZED_COPPER, Identifier.fromString("minecraft:oxidized_copper"))
            register(
                BlockType.OXIDIZED_COPPER_BULB,
                Identifier.fromString("minecraft:oxidized_copper_bulb"),
                BlockOxidizedCopperBulb::class.java
            )
            register(
                BlockType.OXIDIZED_COPPER_DOOR,
                Identifier.fromString("minecraft:oxidized_copper_door"),
                BlockDoor::class.java
            )
            register(BlockType.OXIDIZED_COPPER_GRATE, Identifier.fromString("minecraft:oxidized_copper_grate"))
            register(
                BlockType.OXIDIZED_COPPER_TRAPDOOR,
                Identifier.fromString("minecraft:oxidized_copper_trapdoor"),
                BlockTrapdoor::class.java
            )
            register(BlockType.OXIDIZED_CUT_COPPER, Identifier.fromString("minecraft:oxidized_cut_copper"))
            register(
                BlockType.OXIDIZED_CUT_COPPER_SLAB,
                Identifier.fromString("minecraft:oxidized_cut_copper_slab"),
                BlockSlab::class.java
            )
            register(
                BlockType.OXIDIZED_CUT_COPPER_STAIRS,
                Identifier.fromString("minecraft:oxidized_cut_copper_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.OXIDIZED_DOUBLE_CUT_COPPER_SLAB,
                Identifier.fromString("minecraft:oxidized_double_cut_copper_slab"),
                BlockDoubleSlab::class.java
            )
            register(BlockType.PACKED_ICE, Identifier.fromString("minecraft:packed_ice"))
            register(BlockType.PACKED_MUD, Identifier.fromString("minecraft:packed_mud"))
            register(
                BlockType.PEARLESCENT_FROGLIGHT,
                Identifier.fromString("minecraft:pearlescent_froglight"),
                BlockPearlescentFroglight::class.java
            )
            register(BlockType.PINK_CANDLE, Identifier.fromString("minecraft:pink_candle"), BlockCandle::class.java)
            register(
                BlockType.PINK_CANDLE_CAKE,
                Identifier.fromString("minecraft:pink_candle_cake"),
                BlockCandleCake::class.java
            )
            register(BlockType.PINK_CARPET, Identifier.fromString("minecraft:pink_carpet"))
            register(BlockType.PINK_CONCRETE, Identifier.fromString("minecraft:pink_concrete"))
            register(BlockType.PINK_CONCRETE_POWDER, Identifier.fromString("minecraft:pink_concrete_powder"))
            register(
                BlockType.PINK_GLAZED_TERRACOTTA,
                Identifier.fromString("minecraft:pink_glazed_terracotta"),
                BlockGlazedTerracotta::class.java
            )
            register(BlockType.PINK_PETALS, Identifier.fromString("minecraft:pink_petals"), BlockPinkPetals::class.java)
            register(
                BlockType.PINK_SHULKER_BOX,
                Identifier.fromString("minecraft:pink_shulker_box"),
                BlockShulkerBox::class.java
            )
            register(BlockType.PINK_STAINED_GLASS, Identifier.fromString("minecraft:pink_stained_glass"))
            register(BlockType.PINK_STAINED_GLASS_PANE, Identifier.fromString("minecraft:pink_stained_glass_pane"))
            register(BlockType.PINK_TERRACOTTA, Identifier.fromString("minecraft:pink_terracotta"))
            register(BlockType.PINK_WOOL, Identifier.fromString("minecraft:pink_wool"))
            register(BlockType.PISTON, Identifier.fromString("minecraft:piston"))
            register(BlockType.PISTON_ARM_COLLISION, Identifier.fromString("minecraft:piston_arm_collision"))
            register(BlockType.OAK_PLANKS, Identifier.fromString("minecraft:oak_planks"))
            register(BlockType.SPRUCE_PLANKS, Identifier.fromString("minecraft:spruce_planks"))
            register(BlockType.BIRCH_PLANKS, Identifier.fromString("minecraft:birch_planks"))
            register(BlockType.JUNGLE_PLANKS, Identifier.fromString("minecraft:jungle_planks"))
            register(BlockType.ACACIA_PLANKS, Identifier.fromString("minecraft:acacia_planks"))
            register(BlockType.DARK_OAK_PLANKS, Identifier.fromString("minecraft:dark_oak_planks"))
            register(BlockType.PISTON, Identifier.fromString("minecraft:piston"), BlockPiston::class.java)
            register(
                BlockType.PISTON_ARM_COLLISION,
                Identifier.fromString("minecraft:piston_arm_collision"),
                BlockPistonArmCollision::class.java
            )
            register(
                BlockType.PITCHER_CROP,
                Identifier.fromString("minecraft:pitcher_crop"),
                BlockPitcherCrop::class.java
            )
            register(
                BlockType.PITCHER_PLANT,
                Identifier.fromString("minecraft:pitcher_plant"),
                BlockPitcherPlant::class.java
            )
            register(BlockType.PODZOL, Identifier.fromString("minecraft:podzol"))
            register(
                BlockType.POINTED_DRIPSTONE,
                Identifier.fromString("minecraft:pointed_dripstone"),
                BlockPointedDripstone::class.java
            )
            register(BlockType.POLISHED_ANDESITE, Identifier.fromString("minecraft:polished_andesite"))
            register(
                BlockType.POLISHED_ANDESITE_STAIRS,
                Identifier.fromString("minecraft:polished_andesite_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.POLISHED_BASALT,
                Identifier.fromString("minecraft:polished_basalt"),
                BlockPolishedBasalt::class.java
            )
            register(BlockType.POLISHED_BLACKSTONE, Identifier.fromString("minecraft:polished_blackstone"))
            register(
                BlockType.POLISHED_BLACKSTONE_BRICK_DOUBLE_SLAB,
                Identifier.fromString("minecraft:polished_blackstone_brick_double_slab"),
                BlockDoubleSlab::class.java
            )
            register(
                BlockType.POLISHED_BLACKSTONE_BRICK_SLAB,
                Identifier.fromString("minecraft:polished_blackstone_brick_slab"),
                BlockSlab::class.java
            )
            register(
                BlockType.POLISHED_BLACKSTONE_BRICK_STAIRS,
                Identifier.fromString("minecraft:polished_blackstone_brick_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.POLISHED_BLACKSTONE_BRICK_WALL,
                Identifier.fromString("minecraft:polished_blackstone_brick_wall"),
                BlockWall::class.java
            )
            register(
                BlockType.POLISHED_BLACKSTONE_BRICKS,
                Identifier.fromString("minecraft:polished_blackstone_bricks")
            )
            register(
                BlockType.POLISHED_BLACKSTONE_BUTTON,
                Identifier.fromString("minecraft:polished_blackstone_button"),
                BlockButton::class.java
            )
            register(
                BlockType.POLISHED_BLACKSTONE_DOUBLE_SLAB,
                Identifier.fromString("minecraft:polished_blackstone_double_slab"),
                BlockDoubleSlab::class.java
            )
            register(
                BlockType.POLISHED_BLACKSTONE_PRESSURE_PLATE,
                Identifier.fromString("minecraft:polished_blackstone_pressure_plate"),
                BlockPressurePlate::class.java
            )
            register(
                BlockType.POLISHED_BLACKSTONE_SLAB,
                Identifier.fromString("minecraft:polished_blackstone_slab"),
                BlockSlab::class.java
            )
            register(
                BlockType.POLISHED_BLACKSTONE_STAIRS,
                Identifier.fromString("minecraft:polished_blackstone_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.POLISHED_BLACKSTONE_WALL,
                Identifier.fromString("minecraft:polished_blackstone_wall"),
                BlockWall::class.java
            )
            register(BlockType.POLISHED_DEEPSLATE, Identifier.fromString("minecraft:polished_deepslate"))
            register(
                BlockType.POLISHED_DEEPSLATE_DOUBLE_SLAB,
                Identifier.fromString("minecraft:polished_deepslate_double_slab"),
                BlockDoubleSlab::class.java
            )
            register(
                BlockType.POLISHED_DEEPSLATE_SLAB,
                Identifier.fromString("minecraft:polished_deepslate_slab"),
                BlockSlab::class.java
            )
            register(
                BlockType.POLISHED_DEEPSLATE_STAIRS,
                Identifier.fromString("minecraft:polished_deepslate_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.POLISHED_DEEPSLATE_WALL,
                Identifier.fromString("minecraft:polished_deepslate_wall"),
                BlockWall::class.java
            )
            register(BlockType.POLISHED_DIORITE, Identifier.fromString("minecraft:polished_diorite"))
            register(
                BlockType.POLISHED_DIORITE_STAIRS,
                Identifier.fromString("minecraft:polished_diorite_stairs"),
                BlockStairs::class.java
            )
            register(BlockType.POLISHED_GRANITE, Identifier.fromString("minecraft:polished_granite"))
            register(
                BlockType.POLISHED_GRANITE_STAIRS,
                Identifier.fromString("minecraft:polished_granite_stairs"),
                BlockStairs::class.java
            )
            register(BlockType.POLISHED_TUFF, Identifier.fromString("minecraft:polished_tuff"))
            register(
                BlockType.POLISHED_TUFF_DOUBLE_SLAB,
                Identifier.fromString("minecraft:polished_tuff_double_slab"),
                BlockDoubleSlab::class.java
            )
            register(
                BlockType.POLISHED_TUFF_SLAB,
                Identifier.fromString("minecraft:polished_tuff_slab"),
                BlockSlab::class.java
            )
            register(
                BlockType.POLISHED_TUFF_STAIRS,
                Identifier.fromString("minecraft:polished_tuff_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.POLISHED_TUFF_WALL,
                Identifier.fromString("minecraft:polished_tuff_wall"),
                BlockWall::class.java
            )
            register(BlockType.PORTAL, Identifier.fromString("minecraft:portal"), BlockPortal::class.java)
            register(BlockType.POTATOES, Identifier.fromString("minecraft:potatoes"), BlockPotatoes::class.java)
            register(BlockType.POWDER_SNOW, Identifier.fromString("minecraft:powder_snow"))
            register(
                BlockType.POWERED_COMPARATOR,
                Identifier.fromString("minecraft:powered_comparator"),
                BlockPoweredComparator::class.java
            )
            register(
                BlockType.POWERED_REPEATER,
                Identifier.fromString("minecraft:powered_repeater"),
                BlockPoweredRepeater::class.java
            )
            register(BlockType.PRISMARINE, Identifier.fromString("minecraft:prismarine"), BlockPrismarine::class.java)
            register(
                BlockType.PRISMARINE_BRICKS_STAIRS,
                Identifier.fromString("minecraft:prismarine_bricks_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.PRISMARINE_STAIRS,
                Identifier.fromString("minecraft:prismarine_stairs"),
                BlockStairs::class.java
            )
            register(BlockType.PUMPKIN, Identifier.fromString("minecraft:pumpkin"), BlockPumpkin::class.java)
            register(
                BlockType.PUMPKIN_STEM,
                Identifier.fromString("minecraft:pumpkin_stem"),
                BlockPumpkinStem::class.java
            )
            register(BlockType.PURPLE_CANDLE, Identifier.fromString("minecraft:purple_candle"), BlockCandle::class.java)
            register(
                BlockType.PURPLE_CANDLE_CAKE,
                Identifier.fromString("minecraft:purple_candle_cake"),
                BlockCandleCake::class.java
            )
            register(BlockType.PURPLE_CARPET, Identifier.fromString("minecraft:purple_carpet"))
            register(BlockType.PURPLE_CONCRETE, Identifier.fromString("minecraft:purple_concrete"))
            register(BlockType.PURPLE_CONCRETE_POWDER, Identifier.fromString("minecraft:purple_concrete_powder"))
            register(
                BlockType.PURPLE_GLAZED_TERRACOTTA,
                Identifier.fromString("minecraft:purple_glazed_terracotta"),
                BlockGlazedTerracotta::class.java
            )
            register(
                BlockType.PURPLE_SHULKER_BOX,
                Identifier.fromString("minecraft:purple_shulker_box"),
                BlockShulkerBox::class.java
            )
            register(BlockType.PURPLE_STAINED_GLASS, Identifier.fromString("minecraft:purple_stained_glass"))
            register(BlockType.PURPLE_STAINED_GLASS_PANE, Identifier.fromString("minecraft:purple_stained_glass_pane"))
            register(BlockType.PURPLE_TERRACOTTA, Identifier.fromString("minecraft:purple_terracotta"))
            register(BlockType.PURPLE_WOOL, Identifier.fromString("minecraft:purple_wool"))
            register(
                BlockType.PURPUR_BLOCK,
                Identifier.fromString("minecraft:purpur_block"),
                BlockPurpurBlock::class.java
            )
            register(BlockType.PURPUR_STAIRS, Identifier.fromString("minecraft:purpur_stairs"), BlockStairs::class.java)
            register(
                BlockType.QUARTZ_BLOCK,
                Identifier.fromString("minecraft:quartz_block"),
                BlockQuartzBlock::class.java
            )
            register(BlockType.QUARTZ_BRICKS, Identifier.fromString("minecraft:quartz_bricks"))
            register(BlockType.QUARTZ_ORE, Identifier.fromString("minecraft:quartz_ore"))
            register(BlockType.QUARTZ_STAIRS, Identifier.fromString("minecraft:quartz_stairs"), BlockStairs::class.java)
            register(BlockType.RAIL, Identifier.fromString("minecraft:rail"), BlockRail::class.java)
            register(BlockType.RAW_COPPER_BLOCK, Identifier.fromString("minecraft:raw_copper_block"))
            register(BlockType.RAW_GOLD_BLOCK, Identifier.fromString("minecraft:raw_gold_block"))
            register(BlockType.RAW_IRON_BLOCK, Identifier.fromString("minecraft:raw_iron_block"))
            register(BlockType.RED_CANDLE, Identifier.fromString("minecraft:red_candle"), BlockCandle::class.java)
            register(
                BlockType.RED_CANDLE_CAKE,
                Identifier.fromString("minecraft:red_candle_cake"),
                BlockCandleCake::class.java
            )
            register(BlockType.RED_CARPET, Identifier.fromString("minecraft:red_carpet"))
            register(BlockType.RED_CONCRETE, Identifier.fromString("minecraft:red_concrete"))
            register(BlockType.RED_CONCRETE_POWDER, Identifier.fromString("minecraft:red_concrete_powder"))
            register(BlockType.RED_FLOWER, Identifier.fromString("minecraft:red_flower"), BlockRedFlower::class.java)
            register(
                BlockType.RED_GLAZED_TERRACOTTA,
                Identifier.fromString("minecraft:red_glazed_terracotta"),
                BlockGlazedTerracotta::class.java
            )
            register(BlockType.RED_MUSHROOM, Identifier.fromString("minecraft:red_mushroom"), BlockRedMushroom::class.java)
            register(
                BlockType.RED_MUSHROOM_BLOCK,
                Identifier.fromString("minecraft:red_mushroom_block"),
                BlockRedMushroomBlock::class.java
            )
            register(BlockType.RED_NETHER_BRICK, Identifier.fromString("minecraft:red_nether_brick"))
            register(
                BlockType.RED_NETHER_BRICK_STAIRS,
                Identifier.fromString("minecraft:red_nether_brick_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.RED_SANDSTONE,
                Identifier.fromString("minecraft:red_sandstone"),
                BlockRedSandstone::class.java
            )
            register(
                BlockType.RED_SANDSTONE_STAIRS,
                Identifier.fromString("minecraft:red_sandstone_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.RED_SHULKER_BOX,
                Identifier.fromString("minecraft:red_shulker_box"),
                BlockShulkerBox::class.java
            )
            register(BlockType.RED_STAINED_GLASS, Identifier.fromString("minecraft:red_stained_glass"))
            register(BlockType.RED_STAINED_GLASS_PANE, Identifier.fromString("minecraft:red_stained_glass_pane"))
            register(BlockType.RED_TERRACOTTA, Identifier.fromString("minecraft:red_terracotta"))
            register(BlockType.RED_WOOL, Identifier.fromString("minecraft:red_wool"))
            register(BlockType.REDSTONE_BLOCK, Identifier.fromString("minecraft:redstone_block"))
            register(BlockType.REDSTONE_LAMP, Identifier.fromString("minecraft:redstone_lamp"))
            register(BlockType.REDSTONE_ORE, Identifier.fromString("minecraft:redstone_ore"))
            register(
                BlockType.REDSTONE_TORCH,
                Identifier.fromString("minecraft:redstone_torch"),
                BlockRedstoneTorch::class.java
            )
            register(
                BlockType.REDSTONE_WIRE,
                Identifier.fromString("minecraft:redstone_wire"),
                BlockRedstoneWire::class.java
            )
            register(BlockType.REEDS, Identifier.fromString("minecraft:reeds"), BlockReeds::class.java)
            register(BlockType.REINFORCED_DEEPSLATE, Identifier.fromString("minecraft:reinforced_deepslate"))
            register(
                BlockType.REPEATING_COMMAND_BLOCK,
                Identifier.fromString("minecraft:repeating_command_block"),
                BlockRepeatingCommandBlock::class.java
            )
            register(BlockType.RESERVED6, Identifier.fromString("minecraft:reserved6"))
            register(
                BlockType.RESPAWN_ANCHOR,
                Identifier.fromString("minecraft:respawn_anchor"),
                BlockRespawnAnchor::class.java
            )
            register(BlockType.SAND, Identifier.fromString("minecraft:sand"), BlockSand::class.java)
            register(BlockType.SANDSTONE, Identifier.fromString("minecraft:sandstone"), BlockSandstone::class.java)
            register(
                BlockType.SANDSTONE_STAIRS,
                Identifier.fromString("minecraft:sandstone_stairs"),
                BlockStairs::class.java
            )
            register(BlockType.SAPLING, Identifier.fromString("minecraft:sapling"), BlockSapling::class.java)
            register(
                BlockType.SCAFFOLDING,
                Identifier.fromString("minecraft:scaffolding"),
                BlockScaffolding::class.java
            )
            register(BlockType.SCULK, Identifier.fromString("minecraft:sculk"))
            register(
                BlockType.SCULK_CATALYST,
                Identifier.fromString("minecraft:sculk_catalyst"),
                BlockSculkCatalyst::class.java
            )
            register(
                BlockType.SCULK_SENSOR,
                Identifier.fromString("minecraft:sculk_sensor"),
                BlockSculkSensor::class.java
            )
            register(
                BlockType.SCULK_SHRIEKER,
                Identifier.fromString("minecraft:sculk_shrieker"),
                BlockSculkShrieker::class.java
            )
            register(BlockType.SCULK_VEIN, Identifier.fromString("minecraft:sculk_vein"), BlockSculkVein::class.java)
            register(BlockType.SEA_LANTERN, Identifier.fromString("minecraft:sea_lantern"))
            register(BlockType.SEA_PICKLE, Identifier.fromString("minecraft:sea_pickle"), BlockSeaPickle::class.java)
            register(BlockType.SEAGRASS, Identifier.fromString("minecraft:seagrass"), BlockSeagrass::class.java)
            register(BlockType.SHROOMLIGHT, Identifier.fromString("minecraft:shroomlight"))
            register(
                BlockType.SILVER_GLAZED_TERRACOTTA,
                Identifier.fromString("minecraft:silver_glazed_terracotta"),
                BlockGlazedTerracotta::class.java
            )
            register(BlockType.SKULL, Identifier.fromString("minecraft:skull"), BlockSkull::class.java)
            register(BlockType.SLIME, Identifier.fromString("minecraft:slime"))
            register(
                BlockType.SMALL_AMETHYST_BUD,
                Identifier.fromString("minecraft:small_amethyst_bud"),
                BlockSmallAmethystBud::class.java
            )
            register(
                BlockType.SMALL_DRIPLEAF_BLOCK,
                Identifier.fromString("minecraft:small_dripleaf_block"),
                BlockSmallDripleafBlock::class.java
            )
            register(BlockType.SMITHING_TABLE, Identifier.fromString("minecraft:smithing_table"), BlockSmithingTable::class.java)
            register(BlockType.SMOKER, Identifier.fromString("minecraft:smoker"), BlockSmoker::class.java)
            register(BlockType.SMOOTH_BASALT, Identifier.fromString("minecraft:smooth_basalt"))
            register(
                BlockType.SMOOTH_QUARTZ_STAIRS,
                Identifier.fromString("minecraft:smooth_quartz_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.SMOOTH_RED_SANDSTONE_STAIRS,
                Identifier.fromString("minecraft:smooth_red_sandstone_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.SMOOTH_SANDSTONE_STAIRS,
                Identifier.fromString("minecraft:smooth_sandstone_stairs"),
                BlockStairs::class.java
            )
            register(BlockType.SMOOTH_STONE, Identifier.fromString("minecraft:smooth_stone"))
            register(BlockType.SNIFFER_EGG, Identifier.fromString("minecraft:sniffer_egg"), BlockSnifferEgg::class.java)
            register(BlockType.SNOW, Identifier.fromString("minecraft:snow"))
            register(BlockType.SNOW_LAYER, Identifier.fromString("minecraft:snow_layer"), BlockSnowLayer::class.java)
            register(
                BlockType.SOUL_CAMPFIRE,
                Identifier.fromString("minecraft:soul_campfire"),
                BlockSoulCampfire::class.java
            )
            register(BlockType.SOUL_FIRE, Identifier.fromString("minecraft:soul_fire"), BlockSoulFire::class.java)
            register(
                BlockType.SOUL_LANTERN,
                Identifier.fromString("minecraft:soul_lantern"),
                BlockSoulLantern::class.java
            )
            register(BlockType.SOUL_SAND, Identifier.fromString("minecraft:soul_sand"))
            register(BlockType.SOUL_SOIL, Identifier.fromString("minecraft:soul_soil"))
            register(BlockType.SOUL_TORCH, Identifier.fromString("minecraft:soul_torch"), BlockSoulTorch::class.java)
            register(BlockType.SPONGE, Identifier.fromString("minecraft:sponge"), BlockSponge::class.java)
            register(BlockType.SPORE_BLOSSOM, Identifier.fromString("minecraft:spore_blossom"))
            register(BlockType.SPRUCE_BUTTON, Identifier.fromString("minecraft:spruce_button"), BlockButton::class.java)
            register(BlockType.SPRUCE_DOOR, Identifier.fromString("minecraft:spruce_door"), BlockDoor::class.java)
            register(BlockType.SPRUCE_FENCE, Identifier.fromString("minecraft:spruce_fence"))
            register(
                BlockType.SPRUCE_FENCE_GATE,
                Identifier.fromString("minecraft:spruce_fence_gate"),
                BlockFenceGate::class.java
            )
            register(
                BlockType.SPRUCE_HANGING_SIGN,
                Identifier.fromString("minecraft:spruce_hanging_sign"),
                BlockHangingSign::class.java
            )
            register(BlockType.SPRUCE_LOG, Identifier.fromString("minecraft:spruce_log"), BlockLog::class.java)
            register(BlockType.SPRUCE_PLANKS, Identifier.fromString("minecraft:spruce_planks"))
            register(
                BlockType.SPRUCE_PRESSURE_PLATE,
                Identifier.fromString("minecraft:spruce_pressure_plate"),
                BlockPressurePlate::class.java
            )
            register(BlockType.SPRUCE_STAIRS, Identifier.fromString("minecraft:spruce_stairs"), BlockStairs::class.java)
            register(
                BlockType.SPRUCE_STANDING_SIGN,
                Identifier.fromString("minecraft:spruce_standing_sign"),
                BlockStandingSign::class.java
            )
            register(
                BlockType.SPRUCE_TRAPDOOR,
                Identifier.fromString("minecraft:spruce_trapdoor"),
                BlockTrapdoor::class.java
            )
            register(
                BlockType.SPRUCE_WALL_SIGN,
                Identifier.fromString("minecraft:spruce_wall_sign"),
                BlockWallSign::class.java
            )
            register(
                BlockType.STANDING_BANNER,
                Identifier.fromString("minecraft:standing_banner"),
                BlockStandingBanner::class.java
            )
            register(
                BlockType.STANDING_SIGN,
                Identifier.fromString("minecraft:standing_sign"),
                BlockStandingSign::class.java
            )
            register(
                BlockType.STICKY_PISTON,
                Identifier.fromString("minecraft:sticky_piston"),
                BlockStickyPiston::class.java
            )
            register(
                BlockType.STICKY_PISTON_ARM_COLLISION,
                Identifier.fromString("minecraft:sticky_piston_arm_collision"),
                BlockStickyPistonArmCollision::class.java
            )
            register(BlockType.STONE, Identifier.fromString("minecraft:stone"))
            register(BlockType.GRANITE, Identifier.fromString("minecraft:granite"))
            register(BlockType.POLISHED_ANDESITE, Identifier.fromString("minecraft:polished_granite"))
            register(BlockType.DIORITE, Identifier.fromString("minecraft:diorite"))
            register(BlockType.POLISHED_DIORITE, Identifier.fromString("minecraft:polished_diorite"))
            register(BlockType.ANDESITE, Identifier.fromString("minecraft:andesite"))
            register(BlockType.POLISHED_ANDESITE, Identifier.fromString("minecraft:polished_andesite"))
            register(BlockType.STONE_BRICK_STAIRS, Identifier.fromString("minecraft:stone_brick_stairs"))
            register(BlockType.STONE_BUTTON, Identifier.fromString("minecraft:stone_button"))
            register(BlockType.STONE_PRESSURE_PLATE, Identifier.fromString("minecraft:stone_pressure_plate"))
            register(BlockType.STONE_STAIRS, Identifier.fromString("minecraft:stone_stairs"))
            register(BlockType.STONEBRICK, Identifier.fromString("minecraft:stonebrick"))
            register(
                BlockType.STONE_BLOCK_SLAB,
                Identifier.fromString("minecraft:stone_block_slab"),
                BlockStoneBlockSlab::class.java
            )
            register(
                BlockType.STONE_BLOCK_SLAB2,
                Identifier.fromString("minecraft:stone_block_slab2"),
                BlockStoneBlockSlab2::class.java
            )
            register(
                BlockType.STONE_BLOCK_SLAB3,
                Identifier.fromString("minecraft:stone_block_slab3"),
                BlockStoneBlockSlab3::class.java
            )
            register(
                BlockType.STONE_BLOCK_SLAB4,
                Identifier.fromString("minecraft:stone_block_slab4"),
                BlockStoneBlockSlab4::class.java
            )
            register(
                BlockType.STONE_BRICK_STAIRS,
                Identifier.fromString("minecraft:stone_brick_stairs"),
                BlockStairs::class.java
            )
            register(BlockType.STONE_BUTTON, Identifier.fromString("minecraft:stone_button"), BlockButton::class.java)
            register(
                BlockType.STONE_PRESSURE_PLATE,
                Identifier.fromString("minecraft:stone_pressure_plate"),
                BlockPressurePlate::class.java
            )
            register(BlockType.STONE_STAIRS, Identifier.fromString("minecraft:stone_stairs"), BlockStairs::class.java)
            register(BlockType.STONEBRICK, Identifier.fromString("minecraft:stonebrick"), BlockStonebrick::class.java)
            register(BlockType.STONECUTTER, Identifier.fromString("minecraft:stonecutter"))
            register(
                BlockType.STONECUTTER_BLOCK,
                Identifier.fromString("minecraft:stonecutter_block"),
                BlockStonecutterBlock::class.java
            )
            register(
                BlockType.STRIPPED_ACACIA_LOG,
                Identifier.fromString("minecraft:stripped_acacia_log"),
                BlockLog::class.java
            )
            register(
                BlockType.STRIPPED_BAMBOO_BLOCK,
                Identifier.fromString("minecraft:stripped_bamboo_block"),
                BlockStrippedWood::class.java
            )
            register(
                BlockType.STRIPPED_BIRCH_LOG,
                Identifier.fromString("minecraft:stripped_birch_log"),
                BlockLog::class.java
            )
            register(
                BlockType.STRIPPED_CHERRY_LOG,
                Identifier.fromString("minecraft:stripped_cherry_log"),
                BlockLog::class.java
            )
            register(
                BlockType.STRIPPED_CHERRY_WOOD,
                Identifier.fromString("minecraft:stripped_cherry_wood"),
                BlockWood::class.java
            )
            register(
                BlockType.STRIPPED_CRIMSON_HYPHAE,
                Identifier.fromString("minecraft:stripped_crimson_hyphae"),
                BlockStrippedWood::class.java
            )
            register(
                BlockType.STRIPPED_CRIMSON_STEM,
                Identifier.fromString("minecraft:stripped_crimson_stem"),
                BlockStrippedWood::class.java
            )
            register(
                BlockType.STRIPPED_DARK_OAK_LOG,
                Identifier.fromString("minecraft:stripped_dark_oak_log"),
                BlockLog::class.java
            )
            register(
                BlockType.STRIPPED_JUNGLE_LOG,
                Identifier.fromString("minecraft:stripped_jungle_log"),
                BlockLog::class.java
            )
            register(
                BlockType.STRIPPED_MANGROVE_LOG,
                Identifier.fromString("minecraft:stripped_mangrove_log"),
                BlockLog::class.java
            )
            register(
                BlockType.STRIPPED_MANGROVE_WOOD,
                Identifier.fromString("minecraft:stripped_mangrove_wood"),
                BlockStrippedWood::class.java
            )
            register(
                BlockType.STRIPPED_OAK_LOG,
                Identifier.fromString("minecraft:stripped_oak_log"),
                BlockLog::class.java
            )
            register(
                BlockType.STRIPPED_SPRUCE_LOG,
                Identifier.fromString("minecraft:stripped_spruce_log"),
                BlockLog::class.java
            )
            register(
                BlockType.STRIPPED_WARPED_HYPHAE,
                Identifier.fromString("minecraft:stripped_warped_hyphae"),
                BlockStrippedWood::class.java
            )
            register(
                BlockType.STRIPPED_WARPED_STEM,
                Identifier.fromString("minecraft:stripped_warped_stem"),
                BlockStrippedWood::class.java
            )
            register(
                BlockType.STRIPPED_OAK_WOOD,
                Identifier.fromString("minecraft:stripped_oak_wood"),
                BlockStrippedWood::class.java
            )
            register(
                BlockType.STRIPPED_SPRUCE_WOOD,
                Identifier.fromString("minecraft:stripped_spruce_wood"),
                BlockStrippedWood::class.java
            )
            register(
                BlockType.STRIPPED_BIRCH_WOOD,
                Identifier.fromString("minecraft:stripped_birch_wood"),
                BlockStrippedWood::class.java
            )
            register(
                BlockType.STRIPPED_JUNGLE_WOOD,
                Identifier.fromString("minecraft:stripped_jungle_wood"),
                BlockStrippedWood::class.java
            )
            register(
                BlockType.STRIPPED_ACACIA_WOOD,
                Identifier.fromString("minecraft:stripped_acacia_wood"),
                BlockStrippedWood::class.java
            )
            register(
                BlockType.STRIPPED_DARK_OAK_WOOD,
                Identifier.fromString("minecraft:stripped_dark_oak_wood"),
                BlockStrippedWood::class.java
            )
            register(
                BlockType.STRUCTURE_BLOCK,
                Identifier.fromString("minecraft:structure_block"),
                BlockStructureBlock::class.java
            )
            register(
                BlockType.STRUCTURE_VOID,
                Identifier.fromString("minecraft:structure_void"),
                BlockStructureVoid::class.java
            )
            register(
                BlockType.SUSPICIOUS_GRAVEL,
                Identifier.fromString("minecraft:suspicious_gravel"),
                BlockSuspiciousGravel::class.java
            )
            register(
                BlockType.SUSPICIOUS_SAND,
                Identifier.fromString("minecraft:suspicious_sand"),
                BlockSuspiciousSand::class.java
            )
            register(
                BlockType.SWEET_BERRY_BUSH,
                Identifier.fromString("minecraft:sweet_berry_bush"),
                BlockSweetBerryBush::class.java
            )
            register(BlockType.TALLGRASS, Identifier.fromString("minecraft:tallgrass"), BlockTallgrass::class.java)
            register(BlockType.TARGET, Identifier.fromString("minecraft:target"))
            register(BlockType.TINTED_GLASS, Identifier.fromString("minecraft:tinted_glass"))
            register(BlockType.TNT, Identifier.fromString("minecraft:tnt"), BlockTnt::class.java)
            register(BlockType.TORCH, Identifier.fromString("minecraft:torch"), BlockTorch::class.java)
            register(BlockType.TORCHFLOWER, Identifier.fromString("minecraft:torchflower"))
            register(
                BlockType.TORCHFLOWER_CROP,
                Identifier.fromString("minecraft:torchflower_crop"),
                BlockTorchflowerCrop::class.java
            )
            register(BlockType.TRAPDOOR, Identifier.fromString("minecraft:trapdoor"), BlockTrapdoor::class.java)
            register(
                BlockType.TRAPPED_CHEST,
                Identifier.fromString("minecraft:trapped_chest"),
                BlockTrappedChest::class.java
            )
            register(BlockType.TRIP_WIRE, Identifier.fromString("minecraft:trip_wire"), BlockTripWire::class.java)
            register(
                BlockType.TRIPWIRE_HOOK,
                Identifier.fromString("minecraft:tripwire_hook"),
                BlockTripwireHook::class.java
            )
            register(BlockType.TUBE_CORAL, Identifier.fromString("minecraft:tube_coral"))
            register(BlockType.TUFF, Identifier.fromString("minecraft:tuff"))
            register(
                BlockType.TUFF_BRICK_DOUBLE_SLAB,
                Identifier.fromString("minecraft:tuff_brick_double_slab"),
                BlockDoubleSlab::class.java
            )
            register(
                BlockType.TUFF_BRICK_SLAB,
                Identifier.fromString("minecraft:tuff_brick_slab"),
                BlockSlab::class.java
            )
            register(
                BlockType.TUFF_BRICK_STAIRS,
                Identifier.fromString("minecraft:tuff_brick_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.TUFF_BRICK_WALL,
                Identifier.fromString("minecraft:tuff_brick_wall"),
                BlockWall::class.java
            )
            register(BlockType.TUFF_BRICKS, Identifier.fromString("minecraft:tuff_bricks"))
            register(
                BlockType.TUFF_DOUBLE_SLAB,
                Identifier.fromString("minecraft:tuff_double_slab"),
                BlockDoubleSlab::class.java
            )
            register(BlockType.TUFF_SLAB, Identifier.fromString("minecraft:tuff_slab"), BlockSlab::class.java)
            register(BlockType.TUFF_STAIRS, Identifier.fromString("minecraft:tuff_stairs"), BlockStairs::class.java)
            register(BlockType.TUFF_WALL, Identifier.fromString("minecraft:tuff_wall"), BlockWall::class.java)
            register(BlockType.TURTLE_EGG, Identifier.fromString("minecraft:turtle_egg"), BlockTurtleEgg::class.java)
            register(
                BlockType.TWISTING_VINES,
                Identifier.fromString("minecraft:twisting_vines"),
                BlockTwistingVines::class.java
            )
            register(
                BlockType.UNDERWATER_TORCH,
                Identifier.fromString("minecraft:underwater_torch"),
                BlockUnderwaterTorch::class.java
            )
            register(
                BlockType.UNDYED_SHULKER_BOX,
                Identifier.fromString("minecraft:undyed_shulker_box"),
                BlockUndyedShulkerBox::class.java
            )
            register(BlockType.UNKNOWN, Identifier.fromString("minecraft:unknown"))
            register(
                BlockType.UNLIT_REDSTONE_TORCH,
                Identifier.fromString("minecraft:unlit_redstone_torch"),
                BlockUnlitRedstoneTorch::class.java
            )
            register(
                BlockType.UNPOWERED_COMPARATOR,
                Identifier.fromString("minecraft:unpowered_comparator"),
                BlockUnpoweredComparator::class.java
            )
            register(
                BlockType.UNPOWERED_REPEATER,
                Identifier.fromString("minecraft:unpowered_repeater"),
                BlockUnpoweredRepeater::class.java
            )
            register(
                BlockType.VERDANT_FROGLIGHT,
                Identifier.fromString("minecraft:verdant_froglight"),
                BlockVerdantFroglight::class.java
            )
            register(BlockType.VINE, Identifier.fromString("minecraft:vine"), BlockVine::class.java)
            register(BlockType.WALL_BANNER, Identifier.fromString("minecraft:wall_banner"), BlockWallBanner::class.java)
            register(BlockType.WALL_SIGN, Identifier.fromString("minecraft:wall_sign"), BlockWallSign::class.java)
            register(BlockType.WARPED_BUTTON, Identifier.fromString("minecraft:warped_button"), BlockButton::class.java)
            register(BlockType.WARPED_DOOR, Identifier.fromString("minecraft:warped_door"), BlockDoor::class.java)
            register(
                BlockType.WARPED_DOUBLE_SLAB,
                Identifier.fromString("minecraft:warped_double_slab"),
                BlockDoubleSlab::class.java
            )
            register(BlockType.WARPED_FENCE, Identifier.fromString("minecraft:warped_fence"))
            register(
                BlockType.WARPED_FENCE_GATE,
                Identifier.fromString("minecraft:warped_fence_gate"),
                BlockFenceGate::class.java
            )
            register(BlockType.WARPED_FUNGUS, Identifier.fromString("minecraft:warped_fungus"))
            register(
                BlockType.WARPED_HANGING_SIGN,
                Identifier.fromString("minecraft:warped_hanging_sign"),
                BlockHangingSign::class.java
            )
            register(
                BlockType.WARPED_HYPHAE,
                Identifier.fromString("minecraft:warped_hyphae"),
                BlockWarpedHyphae::class.java
            )
            register(BlockType.WARPED_NYLIUM, Identifier.fromString("minecraft:warped_nylium"))
            register(BlockType.WARPED_PLANKS, Identifier.fromString("minecraft:warped_planks"))
            register(
                BlockType.WARPED_PRESSURE_PLATE,
                Identifier.fromString("minecraft:warped_pressure_plate"),
                BlockPressurePlate::class.java
            )
            register(BlockType.WARPED_ROOTS, Identifier.fromString("minecraft:warped_roots"))
            register(BlockType.WARPED_SLAB, Identifier.fromString("minecraft:warped_slab"), BlockSlab::class.java)
            register(BlockType.WARPED_STAIRS, Identifier.fromString("minecraft:warped_stairs"), BlockStairs::class.java)
            register(
                BlockType.WARPED_STANDING_SIGN,
                Identifier.fromString("minecraft:warped_standing_sign"),
                BlockStandingSign::class.java
            )
            register(BlockType.WARPED_STEM, Identifier.fromString("minecraft:warped_stem"), BlockWarpedStem::class.java)
            register(
                BlockType.WARPED_TRAPDOOR,
                Identifier.fromString("minecraft:warped_trapdoor"),
                BlockTrapdoor::class.java
            )
            register(
                BlockType.WARPED_WALL_SIGN,
                Identifier.fromString("minecraft:warped_wall_sign"),
                BlockWallSign::class.java
            )
            register(BlockType.WARPED_WART_BLOCK, Identifier.fromString("minecraft:warped_wart_block"))
            register(BlockType.WATER, Identifier.fromString("minecraft:water"), BlockWater::class.java)
            register(BlockType.WATERLILY, Identifier.fromString("minecraft:waterlily"))
            register(BlockType.WAXED_CHISELED_COPPER, Identifier.fromString("minecraft:waxed_chiseled_copper"))
            register(BlockType.WAXED_COPPER, Identifier.fromString("minecraft:waxed_copper"))
            register(
                BlockType.WAXED_COPPER_BULB,
                Identifier.fromString("minecraft:waxed_copper_bulb"),
                BlockWaxedCopperBulb::class.java
            )
            register(
                BlockType.WAXED_COPPER_DOOR,
                Identifier.fromString("minecraft:waxed_copper_door"),
                BlockDoor::class.java
            )
            register(BlockType.WAXED_COPPER_GRATE, Identifier.fromString("minecraft:waxed_copper_grate"))
            register(
                BlockType.WAXED_COPPER_TRAPDOOR,
                Identifier.fromString("minecraft:waxed_copper_trapdoor"),
                BlockTrapdoor::class.java
            )
            register(BlockType.WAXED_CUT_COPPER, Identifier.fromString("minecraft:waxed_cut_copper"))
            register(
                BlockType.WAXED_CUT_COPPER_SLAB,
                Identifier.fromString("minecraft:waxed_cut_copper_slab"),
                BlockSlab::class.java
            )
            register(
                BlockType.WAXED_CUT_COPPER_STAIRS,
                Identifier.fromString("minecraft:waxed_cut_copper_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.WAXED_DOUBLE_CUT_COPPER_SLAB,
                Identifier.fromString("minecraft:waxed_double_cut_copper_slab"),
                BlockDoubleSlab::class.java
            )
            register(
                BlockType.WAXED_EXPOSED_CHISELED_COPPER,
                Identifier.fromString("minecraft:waxed_exposed_chiseled_copper")
            )
            register(BlockType.WAXED_EXPOSED_COPPER, Identifier.fromString("minecraft:waxed_exposed_copper"))
            register(
                BlockType.WAXED_EXPOSED_COPPER_BULB,
                Identifier.fromString("minecraft:waxed_exposed_copper_bulb"),
                BlockWaxedExposedCopperBulb::class.java
            )
            register(
                BlockType.WAXED_EXPOSED_COPPER_DOOR,
                Identifier.fromString("minecraft:waxed_exposed_copper_door"),
                BlockDoor::class.java
            )
            register(
                BlockType.WAXED_EXPOSED_COPPER_GRATE,
                Identifier.fromString("minecraft:waxed_exposed_copper_grate")
            )
            register(
                BlockType.WAXED_EXPOSED_COPPER_TRAPDOOR,
                Identifier.fromString("minecraft:waxed_exposed_copper_trapdoor"),
                BlockTrapdoor::class.java
            )
            register(BlockType.WAXED_EXPOSED_CUT_COPPER, Identifier.fromString("minecraft:waxed_exposed_cut_copper"))
            register(
                BlockType.WAXED_EXPOSED_CUT_COPPER_SLAB,
                Identifier.fromString("minecraft:waxed_exposed_cut_copper_slab"),
                BlockSlab::class.java
            )
            register(
                BlockType.WAXED_EXPOSED_CUT_COPPER_STAIRS,
                Identifier.fromString("minecraft:waxed_exposed_cut_copper_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.WAXED_EXPOSED_DOUBLE_CUT_COPPER_SLAB,
                Identifier.fromString("minecraft:waxed_exposed_double_cut_copper_slab"),
                BlockSlab::class.java
            )
            register(
                BlockType.WAXED_OXIDIZED_CHISELED_COPPER,
                Identifier.fromString("minecraft:waxed_oxidized_chiseled_copper")
            )
            register(BlockType.WAXED_OXIDIZED_COPPER, Identifier.fromString("minecraft:waxed_oxidized_copper"))
            register(
                BlockType.WAXED_OXIDIZED_COPPER_BULB,
                Identifier.fromString("minecraft:waxed_oxidized_copper_bulb"),
                BlockWaxedOxidizedCopperBulb::class.java
            )
            register(
                BlockType.WAXED_OXIDIZED_COPPER_DOOR,
                Identifier.fromString("minecraft:waxed_oxidized_copper_door"),
                BlockDoor::class.java
            )
            register(
                BlockType.WAXED_OXIDIZED_COPPER_GRATE,
                Identifier.fromString("minecraft:waxed_oxidized_copper_grate")
            )
            register(
                BlockType.WAXED_OXIDIZED_COPPER_TRAPDOOR,
                Identifier.fromString("minecraft:waxed_oxidized_copper_trapdoor"),
                BlockTrapdoor::class.java
            )
            register(BlockType.WAXED_OXIDIZED_CUT_COPPER, Identifier.fromString("minecraft:waxed_oxidized_cut_copper"))
            register(
                BlockType.WAXED_OXIDIZED_CUT_COPPER_SLAB,
                Identifier.fromString("minecraft:waxed_oxidized_cut_copper_slab"),
                BlockSlab::class.java
            )
            register(
                BlockType.WAXED_OXIDIZED_CUT_COPPER_STAIRS,
                Identifier.fromString("minecraft:waxed_oxidized_cut_copper_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.WAXED_OXIDIZED_DOUBLE_CUT_COPPER_SLAB,
                Identifier.fromString("minecraft:waxed_oxidized_double_cut_copper_slab"),
                BlockDoubleSlab::class.java
            )
            register(
                BlockType.WAXED_WEATHERED_CHISELED_COPPER,
                Identifier.fromString("minecraft:waxed_weathered_chiseled_copper")
            )
            register(BlockType.WAXED_WEATHERED_COPPER, Identifier.fromString("minecraft:waxed_weathered_copper"))
            register(
                BlockType.WAXED_WEATHERED_COPPER_BULB,
                Identifier.fromString("minecraft:waxed_weathered_copper_bulb"),
                BlockWaxedWeatheredCopperBulb::class.java
            )
            register(
                BlockType.WAXED_WEATHERED_COPPER_DOOR,
                Identifier.fromString("minecraft:waxed_weathered_copper_door"),
                BlockDoor::class.java
            )
            register(
                BlockType.WAXED_WEATHERED_COPPER_GRATE,
                Identifier.fromString("minecraft:waxed_weathered_copper_grate")
            )
            register(
                BlockType.WAXED_WEATHERED_COPPER_TRAPDOOR,
                Identifier.fromString("minecraft:waxed_weathered_copper_trapdoor"),
                BlockTrapdoor::class.java
            )
            register(
                BlockType.WAXED_WEATHERED_CUT_COPPER,
                Identifier.fromString("minecraft:waxed_weathered_cut_copper")
            )
            register(
                BlockType.WAXED_WEATHERED_CUT_COPPER_SLAB,
                Identifier.fromString("minecraft:waxed_weathered_cut_copper_slab"),
                BlockSlab::class.java
            )
            register(
                BlockType.WAXED_WEATHERED_CUT_COPPER_STAIRS,
                Identifier.fromString("minecraft:waxed_weathered_cut_copper_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.WAXED_WEATHERED_DOUBLE_CUT_COPPER_SLAB,
                Identifier.fromString("minecraft:waxed_weathered_double_cut_copper_slab"),
                BlockDoubleSlab::class.java
            )
            register(BlockType.WEATHERED_CHISELED_COPPER, Identifier.fromString("minecraft:weathered_chiseled_copper"))
            register(BlockType.WEATHERED_COPPER, Identifier.fromString("minecraft:weathered_copper"))
            register(
                BlockType.WEATHERED_COPPER_BULB,
                Identifier.fromString("minecraft:weathered_copper_bulb"),
                BlockWeatheredCopperBulb::class.java
            )
            register(
                BlockType.WEATHERED_COPPER_DOOR,
                Identifier.fromString("minecraft:weathered_copper_door"),
                BlockDoor::class.java
            )
            register(BlockType.WEATHERED_COPPER_GRATE, Identifier.fromString("minecraft:weathered_copper_grate"))
            register(
                BlockType.WEATHERED_COPPER_TRAPDOOR,
                Identifier.fromString("minecraft:weathered_copper_trapdoor"),
                BlockTrapdoor::class.java
            )
            register(BlockType.WEATHERED_CUT_COPPER, Identifier.fromString("minecraft:weathered_cut_copper"))
            register(
                BlockType.WEATHERED_CUT_COPPER_SLAB,
                Identifier.fromString("minecraft:weathered_cut_copper_slab"),
                BlockSlab::class.java
            )
            register(
                BlockType.WEATHERED_CUT_COPPER_STAIRS,
                Identifier.fromString("minecraft:weathered_cut_copper_stairs"),
                BlockStairs::class.java
            )
            register(
                BlockType.WEATHERED_DOUBLE_CUT_COPPER_SLAB,
                Identifier.fromString("minecraft:weathered_double_cut_copper_slab"),
                BlockDoubleSlab::class.java
            )
            register(BlockType.WEB, Identifier.fromString("minecraft:web"), BlockWeb::class.java)
            register(
                BlockType.WEEPING_VINES,
                Identifier.fromString("minecraft:weeping_vines"),
                BlockWeepingVines::class.java
            )
            register(BlockType.WHEAT, Identifier.fromString("minecraft:wheat"), BlockWheat::class.java)
            register(BlockType.WHITE_CANDLE, Identifier.fromString("minecraft:white_candle"), BlockCandle::class.java)
            register(
                BlockType.WHITE_CANDLE_CAKE,
                Identifier.fromString("minecraft:white_candle_cake"),
                BlockCandleCake::class.java
            )
            register(BlockType.WHITE_CARPET, Identifier.fromString("minecraft:white_carpet"))
            register(BlockType.WHITE_CONCRETE, Identifier.fromString("minecraft:white_concrete"))
            register(BlockType.WHITE_CONCRETE_POWDER, Identifier.fromString("minecraft:white_concrete_powder"))
            register(
                BlockType.WHITE_GLAZED_TERRACOTTA,
                Identifier.fromString("minecraft:white_glazed_terracotta"),
                BlockGlazedTerracotta::class.java
            )
            register(
                BlockType.WHITE_SHULKER_BOX,
                Identifier.fromString("minecraft:white_shulker_box"),
                BlockShulkerBox::class.java
            )
            register(BlockType.WHITE_STAINED_GLASS, Identifier.fromString("minecraft:white_stained_glass"))
            register(BlockType.WHITE_STAINED_GLASS_PANE, Identifier.fromString("minecraft:white_stained_glass_pane"))
            register(BlockType.WHITE_TERRACOTTA, Identifier.fromString("minecraft:white_terracotta"))
            register(BlockType.WHITE_WOOL, Identifier.fromString("minecraft:white_wool"))
            register(BlockType.WITHER_ROSE, Identifier.fromString("minecraft:wither_rose"), BlockWitherRose::class.java)
            register(BlockType.OAK_WOOD, Identifier.fromString("minecraft:oak_wood"), BlockWood::class.java)
            register(BlockType.SPRUCE_WOOD, Identifier.fromString("minecraft:spruce_wood"), BlockWood::class.java)
            register(BlockType.BIRCH_WOOD, Identifier.fromString("minecraft:birch_wood"), BlockWood::class.java)
            register(BlockType.JUNGLE_WOOD, Identifier.fromString("minecraft:jungle_wood"), BlockWood::class.java)
            register(BlockType.ACACIA_WOOD, Identifier.fromString("minecraft:acacia_wood"), BlockWood::class.java)
            register(BlockType.DARK_OAK_WOOD, Identifier.fromString("minecraft:dark_oak_wood"), BlockWood::class.java)
            register(BlockType.WOODEN_BUTTON, Identifier.fromString("minecraft:wooden_button"), BlockButton::class.java)
            register(BlockType.WOODEN_DOOR, Identifier.fromString("minecraft:wooden_door"), BlockDoor::class.java)
            register(
                BlockType.WOODEN_PRESSURE_PLATE,
                Identifier.fromString("minecraft:wooden_pressure_plate"),
                BlockPressurePlate::class.java
            )
            register(BlockType.OAK_SLAB, Identifier.fromString("minecraft:oak_slab"), BlockWoodenSlab::class.java)
            register(BlockType.SPRUCE_SLAB, Identifier.fromString("minecraft:spruce_slab"), BlockWoodenSlab::class.java)
            register(BlockType.BIRCH_SLAB, Identifier.fromString("minecraft:birch_slab"), BlockWoodenSlab::class.java)
            register(BlockType.JUNGLE_SLAB, Identifier.fromString("minecraft:jungle_slab"), BlockWoodenSlab::class.java)
            register(BlockType.ACACIA_SLAB, Identifier.fromString("minecraft:acacia_slab"), BlockWoodenSlab::class.java)
            register(BlockType.DARK_OAK_SLAB, Identifier.fromString("minecraft:dark_oak_slab"), BlockWoodenSlab::class.java)
            register(BlockType.YELLOW_CANDLE, Identifier.fromString("minecraft:yellow_candle"), BlockCandle::class.java)
            register(
                BlockType.YELLOW_CANDLE_CAKE,
                Identifier.fromString("minecraft:yellow_candle_cake"),
                BlockCandleCake::class.java
            )
            register(BlockType.YELLOW_CARPET, Identifier.fromString("minecraft:yellow_carpet"))
            register(BlockType.YELLOW_CONCRETE, Identifier.fromString("minecraft:yellow_concrete"))
            register(BlockType.YELLOW_CONCRETE_POWDER, Identifier.fromString("minecraft:yellow_concrete_powder"))
            register(BlockType.YELLOW_FLOWER, Identifier.fromString("minecraft:yellow_flower"), BlockYellowFlower::class.java)
            register(
                BlockType.YELLOW_GLAZED_TERRACOTTA,
                Identifier.fromString("minecraft:yellow_glazed_terracotta"),
                BlockGlazedTerracotta::class.java
            )
            register(
                BlockType.YELLOW_SHULKER_BOX,
                Identifier.fromString("minecraft:yellow_shulker_box"),
                BlockShulkerBox::class.java
            )
            register(BlockType.YELLOW_STAINED_GLASS, Identifier.fromString("minecraft:yellow_stained_glass"))
            register(BlockType.YELLOW_STAINED_GLASS_PANE, Identifier.fromString("minecraft:yellow_stained_glass_pane"))
            register(BlockType.YELLOW_TERRACOTTA, Identifier.fromString("minecraft:yellow_terracotta"))
            register(BlockType.YELLOW_WOOL, Identifier.fromString("minecraft:yellow_wool"))
        }

        private fun register(blockType: BlockType, identifier: Identifier) {
            register(blockType, identifier, null)
        }

        private fun register(blockType: BlockType, identifier: Identifier, blockClass: Class<out JukeboxBlock?>?) {
            identifierFromBlockType[blockType] = identifier
            blockTypeFromIdentifier[identifier] = blockType
            if (blockClass != null) {
                blockClassFromBlockType[blockType] = blockClass
            }
        }

        fun getIdentifier(blockType: BlockType): Identifier {
            return identifierFromBlockType[blockType]!!
        }

        fun getBlockType(identifier: Identifier): BlockType {
            return blockTypeFromIdentifier[identifier]!!
        }

        fun blockClassExists(blockType: BlockType): Boolean {
            return blockClassFromBlockType.containsKey(blockType)
        }

        fun getBlockClass(blockType: BlockType): Class<out JukeboxBlock?>? {
            return blockClassFromBlockType[blockType]
        }

        fun hasItemBlock(identifier: Identifier): Boolean {
            return blockTypeFromIdentifier.containsKey(identifier)
        }
    }

}