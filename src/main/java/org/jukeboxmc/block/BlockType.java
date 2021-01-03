package org.jukeboxmc.block;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@AllArgsConstructor
public enum BlockType {

    AIR( BlockAir.class ),
    GRASS( BlockGrass.class ),
    DIRT( BlockDirt.class ),
    BEDROCK( BlockBedrock.class ),
    TALL_GRASS( BlockTallGrass.class ),
    DOUBLE_PLANT( BlockDoublePlant.class ),
    STONE( BlockStone.class ),
    COBBLESTONE( BlockCobblestone.class ),
    PLANKS( BlockPlanks.class ),
    SAPLING( BlockSapling.class ),
    FLOWING_WATER( BlockFlowingWater.class ),
    WATER( BlockWater.class ),
    FLOWING_LAVA( BlockFlowingLava.class ),
    LAVA( BlockLava.class ),
    SAND( BlockSand.class ),
    GRAVEL( BlockGravel.class ),
    GOLD_ORE( BlockGoldOre.class ),
    IRON_ORE( BlockIronOre.class ),
    COAL_ORE( BlockCoalOre.class ),
    LOG( BlockLog.class ),
    LEAVES( BlockLeaves.class ),
    SPONGE( BlockSponge.class ),
    GLASS( BlockGlass.class ),
    LAPIS_ORE( BlockLapisOre.class ),
    LAPIS_BLOCK( BlockLapisBlock.class ),
    DISPENSER( BlockDispenser.class ),
    SANDSTONE( BlockSandstone.class ),
    NOTEBLOCK( BlockNoteblock.class ),
    BED( BlockBed.class ),
    GOLDEN_RAIL( BlockGoldenRail.class ),
    DETECTOR_RAIL( BlockDetectorRail.class ),
    STICKY_PISTON( BlockStickyPiston.class ),
    WEB( BlockWeb.class ),
    TALLGRASS( BlockTallGrass.class ),
    DEADBUSH( BlockDeadbush.class ),
    PISTON( BlockPiston.class ),
    PISTONARMCOLLISION( BlockPistonarmcollision.class ),
    WOOL( BlockWool.class ),
    ELEMENT_0( BlockElement0.class ),
    YELLOW_FLOWER( BlockYellowFlower.class ),
    RED_FLOWER( BlockRedFlower.class ),
    BROWN_MUSHROOM( BlockBrownMushroom.class ),
    RED_MUSHROOM( BlockRedMushroom.class ),
    GOLD_BLOCK( BlockGoldBlock.class ),
    IRON_BLOCK( BlockIronBlock.class ),
    DOUBLE_STONE_SLAB( BlockDoubleStoneSlab.class ),
    STONE_SLAB( BlockStoneSlab.class ),
    BRICK_BLOCK( BlockBrickBlock.class ),
    TNT( BlockTnt.class ),
    BOOKSHELF( BlockBookshelf.class ),
    MOSSY_COBBLESTONE( BlockMossyCobblestone.class ),
    OBSIDIAN( BlockObsidian.class ),
    TORCH( BlockTorch.class ),
    FIRE( BlockFire.class ),
    MOB_SPAWNER( BlockMobSpawner.class ),
    OAK_STAIRS( BlockOakStairs.class ),
    CHEST( BlockChest.class ),
    REDSTONE_WIRE( BlockRedstoneWire.class ),
    DIAMOND_ORE( BlockDiamondOre.class ),
    DIAMOND_BLOCK( BlockDiamondBlock.class ),
    CRAFTING_TABLE( BlockCraftingTable.class ),
    WHEAT( BlockWheat.class ),
    FARMLAND( BlockFarmland.class ),
    FURNACE( BlockFurnace.class ),
    GLOWING_FURNACE( BlockGlowingFurnace.class ),
    STANDING_SIGN( BlockStandingSign.class ),
    OAK_DOOR( BlockOakDoor.class ),
    LADDER( BlockLadder.class ),
    RAIL( BlockRail.class ),
    STONE_STAIRS( BlockStoneStairs.class ),
    WALL_SIGN( BlockWallSign.class ),
    LEVER( BlockLever.class ),
    STONE_PRESSURE_PLATE( BlockStonePressurePlate.class ),
    IRON_DOOR( BlockIronDoor.class ),
    WOODEN_PRESSURE_PLATE( BlockWoodenPressurePlate.class ),
    REDSTONE_ORE( BlockRedstoneOre.class ),
    GLOWING_REDSTONE_ORE( BlockGlowingRedstoneOre.class ),
    UNLIT_REDSTONE_TORCH( BlockUnlitRedstoneTorch.class ),
    REDSTONE_TORCH( BlockRedstoneTorch.class ),
    STONE_BUTTON( BlockStoneButton.class ),
    SNOW_LAYER( BlockSnowLayer.class ),
    ICE( BlockIce.class ),
    SNOW( BlockSnow.class ),
    CACTUS( BlockCactus.class ),
    CLAY( BlockClay.class ),
    SUGARCANE( BlockSugarCane.class ),
    JUKEBOX( BlockJukebox.class ),
    FENCE( BlockFence.class ),
    PUMPKIN( BlockPumpkin.class ),
    NETHERRACK( BlockNeterrack.class ),
    SOUL_SAND( BlockSoulSand.class ),
    GLOWSTONE( BlockGlowstone.class ),
    PORTAL( BlockPortal.class ),
    JACK_O_LANTERN( BlockJackOLantern.class ),
    CAKE( BlockCake.class ),
    UNPOWERED_REPEATER( BlockUnpoweredRepeater.class ),
    POWERED_REPEATER( BlockPoweredRepeater.class ),
    INVISIBLEBEDROCK( BlockInvisiblebedrock.class ),
    WOODEN_TRAPDOOR( BlockWoodenTrapdoor.class ),
    MONSTER_EGG( BlockMonsterEgg.class ),
    STONEBRICK( BlockStonebrick.class ),
    BROWN_MUSHROOM_BLOCK( BlockBrownMushroomBlock.class ),
    RED_MUSHROOM_BLOCK( BlockRedMushroomBlock.class ),
    IRON_BARS( BlockIronBars.class ),
    GLASS_PANE( BlockGlassPane.class ),
    MELON_BLOCK( BlockMelonBlock.class ),
    PUMPKIN_STEM( BlockPumpkinStem.class ),
    MELON_STEM( BlockMelonStem.class ),
    VINE( BlockVine.class ),
    FENCE_GATE( BlockFenceGate.class ),
    BRICK_STAIRS( BlockBrickStairs.class ),
    STONE_BRICK_STAIRS( BlockStoneBrickStairs.class ),
    MYCELIUM( BlockMycelium.class ),
    WATERLILY( BlockWaterlily.class ),
    NETHER_BRICK_BLOCK( BlockNetherBrickBlock.class ),
    NETHER_BRICK_FENCE( BlockNetherBrickFence.class ),
    NETHER_BRICK_STAIRS( BlockNetherBrickStairs.class ),
    NETHER_WART( BlockNetherWart.class ),
    ENCHANTING_TABLE( BlockEnchantingTable.class ),
    BREWING_STAND( BlockBrewingStand.class ),
    CAULDRON( BlockCauldron.class ),
    END_PORTAL( BlockEndPortal.class ),
    END_PORTAL_FRAME( BlockEndPortalFrame.class ),
    END_STONE( BlockEndStone.class ),
    DRAGON_EGG( BlockDragonEgg.class ),
    REDSTONE_LAMP( BlockRedstoneLamp.class ),
    GLOWNG_REDSTONE_LAMP( BlockGlowningRedstoneLamp.class ),
    DROPPER( BlockDropper.class ),
    ACTIVATOR_RAIL( BlockActivatorRail.class ),
    COCOA( BlockCocoa.class ),
    SANDSTONE_STAIRS( BlockSandstoneStairs.class ),
    EMERALD_ORE( BlockEmeraldOre.class ),
    ENDER_CHEST( BlockEnderChest.class ),
    TRIPWIRE_HOOK( BlockTripwireHook.class ),
    EMERALD_BLOCK( BlockEmeraldBlock.class ),
    SPURCE_STAIRS( BlockSpruceStairs.class ),
    BIRCH_STAIRS( BlockBirchStairs.class ),
    JUNGLE_STAIRS( BlockJungleStairs.class ),
    COMMAND_BLOCK( BlockCommandBlock.class ),
    BEACON( BlockBeacon.class ),
    COBBLESTONE_WALL( BlockCobblestoneWall.class ),
    FLOWER_POT( BlockFlowerPot.class ),
    CARROTS( BlockCarrots.class ),
    POTATOES( BlockPotatoes.class ),
    WOODEN_BUTTON( BlockWoodenButton.class ),
    SKULL( BlockSkull.class ),
    ANVIL( BlockAnvil.class ),
    TRAPPED_CHEST( BlockTrappedChest.class ),
    LIGHT_WEIGHTED_PRESSURE_PLATE( BlockLightWeightedPressurePlate.class ),
    HEAVY_WEIGHTED_PRESSURE_PLATE( BlockHeavyWeightedPressurePlate.class ),
    UNPOWERED_COMPARATOR( BlockUnpoweredCompartor.class ),
    POWERED_COMPARATOR( BlockPoweredComparator.class ),
    DAYLIGHT_DETECTOR( BlockDaylightDetector.class ),
    REDSTONE_BLOCK( BlockRedstoneBlock.class ),
    QUARTZ_ORE( BlockQuartzOre.class ),
    HOPPER( BlockHopper.class ),
    QUARTZ_BLOCK( BlockQuartzBlock.class ),
    QUARTZ_STAIRS( BlockQuartzStairs.class ),
    DOUBLE_WOODEN_SLAB( BlockDoubleStoneSlab.class ),
    WOODEN_SLAB( BlockWoodenSlab.class ),
    STAINED_HARDENED_CLAY( BlockStainedHardenedClay.class ),
    STAINED_GLASS_PANE( BlockStainedGlassPane.class ),
    ACACIA_LEAVES( BlockAcaciaLeaves.class ),
    ACACIA_LOG( BlockAcaciaLog.class ),
    ACACIA_STAIRS( BlockAcaciaStairs.class ),
    DARK_OAK_STAIRS( BlockDarkOakStairs.class ),
    SLIME( BlockSlime.class ),
    IRON_TRAPDOOR( BlockIronTrapdoor.class ),
    PRISMARINE( BlockPrismarine.class ),
    SEALANTERN( BlockSealantern.class ),
    HAY_BLOCK( BlockHayBlock.class ),
    CARPET( BlockCarpet.class ),
    HARDENED_CLAY( BlockHardenedClay.class ),
    COAL_BLOCK( BlockCoalBlock.class ),
    PACKED_ICE( BlockPackedIce.class ),
    STANDING_BANNER( BlockStandingBanner.class ),
    WALL_BANNER( BlockWallBanner.class ),
    DAYLIGHT_DETECTOR_INVERTED( BlockDaylightDetectorInverted.class ),
    RED_SANDSTONE( BlockRedSandstone.class ),
    RED_SANDSTONE_STAIRS( BlockRedSandstoneStairs.class ),
    DOUBLE_RED_SANDSTONE_SLAB( BlockDoubleRedSandstoneSlab.class ),
    RED_SANDSTONE_SLAB( BlockRedSandstoneSlab.class ),
    SPRUCE_FENCE_GATE( BlockSpruceFenceGate.class ),
    BIRCH_FENCE_GATE( BlockBirchFenceGate.class ),
    JUNGLE_FENCE_GATE( BlockJungleFenceGate.class ),
    DARK_OAK_FENCE_GATE( BlockDarkOakFenceGate.class ),
    ACACIA_FENCE_GATE( BlockAcaciaFenceGate.class ),
    REPEATING_COMMAND_BLOCK( BlockRepeatingCommandBlock.class ),
    CHAIN_COMMAND_BLOCK( BlockChainCommandBlock.class ),
    HARD_GLASS_PANE( BlockHardGlassPane.class ),
    HARD_STAINED_GLASS_PANE( BlockHardStainedGlassPane.class ),
    CHEMICAL_HEAT( BlockChemicalHeat.class ),
    SPRUCE_DOOR( BlockSpruceDoor.class ),
    BIRCH_DOOR( BlockBirchDoor.class ),
    JUNGLE_DOOR( BlockJungleDoor.class ),
    ACACIA_DOOR( BlockAcaciaDoor.class ),
    DARK_OAK_DOOR( BlockDarkOakDoor.class ),
    GRASS_PATH( BlockGrassPath.class ),
    FRAME( BlockFrame.class ),
    CHORUS_FLOWER( BlockChorusFlower.class ),
    PURPUR_BLOCK( BlockPurpurBlock.class ),
    COLORED_TORCH_RG( BlockColoredTorchRG.class ),
    PURPUR_STAIRS( BlockPurpurBlock.class ),
    COLORED_TORCH_BP( BlockColoredTorchBP.class ),
    UNDYED_SHULKER_BOX( BlockUndyedShulkerBox.class ),
    END_BRICKS( BlockEndBricks.class ),
    FROSTED_ICE( BlockFrostedIce.class ),
    END_ROD( BlockEndRod.class ),
    END_GATEWAY( BlockEndGateway.class ),
    ALLOW( BlockAllow.class ),
    DENY( BlockDeny.class ),
    BORDER_BLOCK( BlockBorderBlock.class ),
    MAGMA( BlockMagma.class ),
    NETHER_WART_BLOCK( BlockNetherWartBlock.class ),
    RED_NETHER_BRICK( BlockRedNetherBrick.class ),
    BONE_BLOCK( BlockBoneBlock.class ),
    STRUCTURE_VOID( BlockStructureVoid.class ),
    SHULKER_BOX( BlockShulkerBox.class ),
    PURPLE_GLAZED_TERRACOTTA( BlockPurpleGlazedTerracotta.class ),
    WHITE_GLAZED_TERRACOTTA( BlockWhiteGlazedTerracotta.class ),
    ORANGE_GLAZED_TERRACOTTA( BlockOrangeGlazedTerracotta.class ),
    MAGENTA_GLAZED_TERRACOTTA( BlockMagentaGlazedTerracotta.class ),
    LIGHT_BLUE_GLAZED_TERRACOTTA( BlockLightBlueGlazedTerracotta.class ),
    YELLOW_GLAZED_TERRACOTTA( BlockYellowGlazedTerracotta.class ),
    LIME_GLAZED_TERRACOTTA( BlockLimeGlazedTerracotta.class ),
    PINK_GLAZED_TERRACOTTA( BlockPinkGlazedTerracotta.class ),
    GRAY_GLAZED_TERRACOTTA( BlockGrayGlazedTerracotta.class ),
    SILVER_GLAZED_TERRACOTTA( BlockSilverGlazedTerracotta.class ),
    CYAN_GLAZED_TERRACOTTA( BlockCyanGlazedTerracotta.class ),
    BLUE_GLAZED_TERRACOTTA( BlockBlueGlazedTerracotta.class ),
    BROWN_GLAZED_TERRACOTTA( BlockBrownGlazedTerracotta.class ),
    GREEN_GLAZED_TERRACOTTA( BlockGreenGlazedTerracotta.class ),
    RED_GLAZED_TERRACOTTA( BlockRedGlazedTerracotta.class ),
    BLACK_GLAZED_TERRACOTTA( BlockBlackGlazedTerracotta.class ),
    CONCRETE( BlockConcrete.class ),
    CONRETEPOWDER( BlockConcretePowder.class ),
    CHEMISTRY_TABLE( BlockChemistryTable.class ),
    UNDERWATER_TORCH( BlockUnderwaterTorch.class ),
    CHORUS_PLANT( BlockChorusPlant.class ),
    STAINED_GLASS( BlockStainedGlass.class ),
    CAMERA( BlockCamera.class ),
    PODZOL( BlockPodzol.class ),
    BEETROOT( BlockBeetroot.class ),
    STONECUTTER( BlockStonecutter.class ),
    GLOWING_OBSIDIAN( BlockGlowingObsidian.class ),
    NETHERREACTOR( BlockNetherreactor.class ),
    INFO_UPDATE( BlockInfoUpdate.class ),
    INFO_UPDATE2( BlockInfoUpdate2.class ),
    MOVINGBLOCK( BlockMovingblock.class ),
    OBSERVER( BlockObserver.class ),
    STRUCTURE_BLOCK( BlockStructureBlock.class ),
    HARD_GLASS( BlockHardGlass.class ),
    HARD_STAINED_GLASS( BlockHardStainedGlass.class ),
    RESERVED6( BlockReserved6.class ),
    PRISMARINE_STAIRS( BlockPrismarineStairs.class ),
    DARK_PRISMARINE_STAIRS( BlockDarkPrismarineStairs.class ),
    PRISMARINE_BRICKS_STAIRS( BlockPrismarineBricksStairs.class ),
    STRIPPED_SPRUCE_LOG( BlockStrippedSpruceLog.class ),
    STRIPPED_BIRCH_LOG( BlockStrippedBirchLog.class ),
    STRIPPED_JUNGLE_LOG( BlockStrippedJungleLog.class ),
    STRIPPED_ACACIA_LOG( BlockStrippedAcaciaLog.class ),
    STRIPPED_DARK_OAK_LOG( BlockStrippedDarkOakLog.class ),
    STRIPPED_OAK_LOG( BlockStrippedOakLog.class ),
    BLUE_ICE( BlockBlueIce.class ),
    ELEMENT_1( BlockElement1.class ),
    ELEMENT_2( BlockElement2.class ),
    ELEMENT_3( BlockElement3.class ),
    ELEMENT_4( BlockElement4.class ),
    ELEMENT_5( BlockElement5.class ),
    ELEMENT_6( BlockElement6.class ),
    ELEMENT_7( BlockElement7.class ),
    ELEMENT_8( BlockElement8.class ),
    ELEMENT_9( BlockElement9.class ),
    ELEMENT_10( BlockElement10.class ),
    ELEMENT_11( BlockElement11.class ),
    ELEMENT_12( BlockElement12.class ),
    ELEMENT_13( BlockElement13.class ),
    ELEMENT_14( BlockElement14.class ),
    ELEMENT_15( BlockElement15.class ),
    ELEMENT_16( BlockElement16.class ),
    ELEMENT_17( BlockElement17.class ),
    ELEMENT_18( BlockElement18.class ),
    ELEMENT_19( BlockElement19.class ),
    ELEMENT_20( BlockElement20.class ),
    ELEMENT_21( BlockElement21.class ),
    ELEMENT_22( BlockElement22.class ),
    ELEMENT_23( BlockElement23.class ),
    ELEMENT_24( BlockElement24.class ),
    ELEMENT_25( BlockElement25.class ),
    ELEMENT_26( BlockElement26.class ),
    ELEMENT_27( BlockElement27.class ),
    ELEMENT_28( BlockElement28.class ),
    ELEMENT_29( BlockElement29.class ),
    ELEMENT_30( BlockElement30.class ),
    ELEMENT_31( BlockElement31.class ),
    ELEMENT_32( BlockElement32.class ),
    ELEMENT_33( BlockElement33.class ),
    ELEMENT_34( BlockElement34.class ),
    ELEMENT_35( BlockElement35.class ),
    ELEMENT_36( BlockElement36.class ),
    ELEMENT_37( BlockElement37.class ),
    ELEMENT_38( BlockElement38.class ),
    ELEMENT_39( BlockElement39.class ),
    ELEMENT_40( BlockElement40.class ),
    ELEMENT_41( BlockElement41.class ),
    ELEMENT_42( BlockElement42.class ),
    ELEMENT_43( BlockElement43.class ),
    ELEMENT_44( BlockElement44.class ),
    ELEMENT_45( BlockElement45.class ),
    ELEMENT_46( BlockElement46.class ),
    ELEMENT_47( BlockElement47.class ),
    ELEMENT_48( BlockElement48.class ),
    ELEMENT_49( BlockElement49.class ),
    ELEMENT_50( BlockElement50.class ),
    ELEMENT_51( BlockElement51.class ),
    ELEMENT_52( BlockElement52.class ),
    ELEMENT_53( BlockElement53.class ),
    ELEMENT_54( BlockElement54.class ),
    ELEMENT_55( BlockElement55.class ),
    ELEMENT_56( BlockElement56.class ),
    ELEMENT_57( BlockElement57.class ),
    ELEMENT_58( BlockElement58.class ),
    ELEMENT_59( BlockElement59.class ),
    ELEMENT_60( BlockElement60.class ),
    ELEMENT_61( BlockElement61.class ),
    ELEMENT_62( BlockElement62.class ),
    ELEMENT_63( BlockElement63.class ),
    ELEMENT_64( BlockElement64.class ),
    ELEMENT_65( BlockElement65.class ),
    ELEMENT_66( BlockElement66.class ),
    ELEMENT_67( BlockElement67.class ),
    ELEMENT_68( BlockElement68.class ),
    ELEMENT_69( BlockElement69.class ),
    ELEMENT_70( BlockElement70.class ),
    ELEMENT_71( BlockElement71.class ),
    ELEMENT_72( BlockElement72.class ),
    ELEMENT_73( BlockElement73.class ),
    ELEMENT_74( BlockElement74.class ),
    ELEMENT_75( BlockElement75.class ),
    ELEMENT_76( BlockElement76.class ),
    ELEMENT_77( BlockElement77.class ),
    ELEMENT_78( BlockElement78.class ),
    ELEMENT_79( BlockElement79.class ),
    ELEMENT_80( BlockElement80.class ),
    ELEMENT_81( BlockElement81.class ),
    ELEMENT_82( BlockElement82.class ),
    ELEMENT_83( BlockElement83.class ),
    ELEMENT_84( BlockElement84.class ),
    ELEMENT_85( BlockElement85.class ),
    ELEMENT_86( BlockElement86.class ),
    ELEMENT_87( BlockElement87.class ),
    ELEMENT_88( BlockElement88.class ),
    ELEMENT_89( BlockElement89.class ),
    ELEMENT_90( BlockElement90.class ),
    ELEMENT_91( BlockElement91.class ),
    ELEMENT_92( BlockElement92.class ),
    ELEMENT_93( BlockElement93.class ),
    ELEMENT_94( BlockElement94.class ),
    ELEMENT_95( BlockElement95.class ),
    ELEMENT_96( BlockElement96.class ),
    ELEMENT_97( BlockElement97.class ),
    ELEMENT_98( BlockElement98.class ),
    ELEMENT_99( BlockElement99.class ),
    ELEMENT_100( BlockElement100.class ),
    ELEMENT_101( BlockElement101.class ),
    ELEMENT_102( BlockElement102.class ),
    ELEMENT_103( BlockElement103.class ),
    ELEMENT_104( BlockElement104.class ),
    ELEMENT_105( BlockElement105.class ),
    ELEMENT_106( BlockElement106.class ),
    ELEMENT_107( BlockElement107.class ),
    ELEMENT_108( BlockElement108.class ),
    ELEMENT_109( BlockElement109.class ),
    ELEMENT_110( BlockElement110.class ),
    ELEMENT_111( BlockElement111.class ),
    ELEMENT_112( BlockElement112.class ),
    ELEMENT_113( BlockElement113.class ),
    ELEMENT_114( BlockElement114.class ),
    ELEMENT_115( BlockElement115.class ),
    ELEMENT_116( BlockElement116.class ),
    ELEMENT_117( BlockElement117.class ),
    ELEMENT_118( BlockElement118.class ),
    SEAGRASS( BlockSeagrass.class ),
    CORAL( BlockCoral.class ),
    CORAL_BLOCK( BlockCoralBlock.class ),
    CORAL_FAN( BlockCoralFan.class ),
    CORAL_FAN_DEAD( BlockCoralFanDead.class ),
    CORAL_FAN_HANG( BlockCoralFanHang.class ),
    CORAL_FAN_HANG2( BlockCoralFanHang2.class ),
    CORAL_FAN_HANG3( BlockCoralFanHang3.class ),
    KELP( BlockKelp.class ),
    DRIED_HELP_BLOCK( BlockDriedKelpBlock.class ),
    ACACIA_BUTTON( BlockAcaciaButton.class ),
    BIRCH_BUTTON( BlockBirchButton.class ),
    DARK_OAK_BUTTON( BlockDarkOakButton.class ),
    JUNGLE_BUTTON( BlockJungleButton.class ),
    SPRUCE_BUTTON( BlockSpruceButton.class ),
    ACACIA_TRAPDOOR( BlockAcaciaTrapdoor.class ),
    BIRCH_TRAPDOOR( BlockBirchTrapdoor.class ),
    DARK_OAK_TRAPDOOR( BlockDarkOakTrapdoor.class ),
    JUNGLE_TRAPDOOR( BlockJungleTrapdoor.class ),
    SPRUCE_TRAPDOOR( BlockSpruceTrapdoor.class ),
    ACACIA_PRESSURE_PLATE( BlockAcaciaPressurePlate.class ),
    BIRCH_PRESSURE_PLATE( BlockBirchPressurePlate.class ),
    DARK_OAK_PRESSURE_PLATE( BlockDarkOakPressurePlate.class ),
    JUNGLE_PRESSURE_PLATE( BlockJunglePressurePlate.class ),
    SPRUCE_PRESSURE_PLATE( BlockSprucePressurePlate.class ),
    CARVED_PUMPKIN( BlockCarvedPumpkin.class ),
    SEA_PICKLE( BlockSeaPickle.class ),
    CONDUIT( BlockConduit.class ),
    TURTLE_EGG( BlockTurtleEgg.class ),
    BUBBLE_COLUMN( BlockBubbleColumn.class ),
    BARRIER( BlockBarrier.class ),
    END_STONE_BRICK_SLAB( BlockEndStoneBrickSlab.class ),
    BAMBOO( BlockBamboo.class ),
    BAMBOO_SAPLING( BlockBambooSapling.class ),
    SCAFFOLDING( BlockScaffolding.class ),
    MOSSY_STONE_BRICK_SLAB( BlockMossyStoneBrickSlab.class ),
    DOUBLE_END_STONE_BRICK_SLAB( BlockDoubleEndStoneBrickSlab.class ),
    DOUBLE_MOSSY_STONE_BRICK_SLAB( BlockDoubleMossyStoneBrickSlab.class ),
    GRANITE_STAIRS( BlockGraniteStairs.class ),
    DIORITE_STAIRS( BlockDioriteStairs.class ),
    ANDESITE_STAIRS( BlockAndesiteStairs.class ),
    POLISHED_GRANITE_STAIRS( BlockPolishedGraniteStairs.class ),
    POLISHED_DIORITE_STAIRS( BlockPolishedDioriteStairs.class ),
    POLISHED_ANDESITE_STAIRS( BlockPolishedAndesiteStairs.class ),
    MOSSY_STONE_BRICK_STAIRS( BlockMossyStoneBrickStairs.class ),
    SMOOTH_RED_SANDSTONE_STAIRS( BlockRedSandstoneStairs.class ),
    SMOOTH_SANDSTONE_STAIRS( BlockSmoothSandstoneStairs.class ),
    END_BRICK_STAIRS( BlockEndBrickStairs.class ),
    MOSSY_COBBLESTONE_STAIRS( BlockMossyCobblestoneStairs.class ),
    NORMAL_STONE_STAIRS( BlockNormalStoneStairs.class ),
    SPRUCE_STANDING_SIGN( BlockSpruceStandingSign.class ),
    SPRUCE_WALL_SIGN( BlockSpruceeWallSign.class ),
    SMOOTH_STONE( BlockSmoothStone.class ),
    RED_NETHER_BRICK_STAIRS( BlockRedNetherBrickStairs.class ),
    SMOOTH_QUARTZ_STAIRS( BlockSmoothQuartzStairs.class ),
    BIRCH_STANDING_SIGN( BlockBirchStandingSign.class ),
    BIRCH_WALL_SIGN( BlockBirchWallSign.class ),
    JUNGLE_STANDING_SIGN( BlockJungleStandingSign.class ),
    JUNGLE_WALL_SIGN( BlockJungleWallSign.class ),
    ACACIA_STANDING_SIGN( BlockAcaciaStandingSign.class ),
    ACACIA_WALL_SIGN( BlockAcaciaWallSign.class ),
    DARK_OAK_STANDING_SIGN( BlockDarkOakStandingSign.class ),
    DARK_OAK_WALL_SIGN( BlockDarkOakWallSign.class ),
    LECTERN( BlockLectern.class ),
    GRINDSTONE( BlockGrindstone.class ),
    BLAST_FURNACE( BlockBlastFurnace.class ),
    STONECUTTER_BLOCK( BlockStonecutterBlock.class ),
    SMOKER( BlockSmoker.class ),
    GLOWING_SMOKER( BlockGlowingSmoker.class ),
    CARTOGRAPGHY_TABLE( BlockCartographyTableBlock.class ),
    FLETCHING_TABLE( BlockFletchingTable.class ),
    SMITHING_TABLE( BlockSmithingTable.class ),
    BARREL( BlockBarrel.class ),
    LOOM( BlockLoom.class ),
    BELL( BlockBell.class ),
    SWEET_BERRY_BUSH( BlockSweetBerryBush.class ),
    LANTERN( BlockLantern.class ),
    CAMPFIRE( BlockCampfire.class ),
    LAVA_CAULDRON( BlockLavaCauldron.class ),
    JIGSAW( BlockJigsaw.class ),
    WOOD( BlockWood.class ),
    COMPOSTER( BlockComposter.class ),
    GLOWING_BLAST_FURNACE( BlockGlowingBlastFurnace.class ),
    LIGHT_BLOCK( BlockLightBlock.class ),
    WITHER_ROSE( BlockWitherRose.class ),
    STICKYPISTONARMCOLLISION( BlockStickypistonarmcollision.class ),
    BEE_NEST( BlockBeeNest.class ),
    HONEY_BLOCK( BlockHoneyBlock.class ),
    HONEYCOMB_BLOCK( BlockHoneycombBlock.class ),
    LODESTONE( BlockLodestone.class ),
    CRIMSON_ROOTS( BlockCrimsonRoots.class ),
    WARPED_ROOTS( BlockWarpedRoots.class ),
    CRIMSON_STEM( BlockCrimsonStem.class ),
    WARPED_STEM( BlockWarpedStem.class ),
    WARPED_WART_BLOCK( BlockWarpedWartBlock.class ),
    CRIMSON_FUNGUS( BlockCrimsonFungus.class ),
    WARPED_FUNGUS( BlockWarpedFungus.class ),
    SHROOMLIGHT( BlockShroomlight.class ),
    WEEPING_VINES( BlockWeepingVines.class ),
    CRIMSON_NYLIUM( BlockCrimsonNylium.class ),
    WARPED_NYLIUM( BlockWarpedNylium.class ),
    BASALT( BlockBasalt.class ),
    POLISHED_BASALT( BlockPolishedBasalt.class ),
    SOUL_SOIL( BlockSoulSoil.class ),
    SOUL_FIRE( BlockSoulFire.class ),
    NETHER_SPROUTS( BlockNetherSprouts.class ),
    TARGET( BlockTarget.class ),
    STRIPPED_CRIMSON_STEM( BlockStrippedCrimsonStem.class ),
    STRIPPED_WARPED_STEM( BlockStrippedWarpedStem.class ),
    CRIMSON_PLANKS( BlockCrimsonPlanks.class ),
    WARPED_PLANKS( BlockWarpedPlanks.class ),
    CRIMSON_DOOR( BlockCrimsonDoor.class ),
    WARPED_DOOR( BlockWarpedDoor.class ),
    CRIMSON_TRAPDOOR( BlockCrimsonTrapdoor.class ),
    WARPED_TRAPDOOR( BlockWarpedTrapdoor.class ),
    CRIMSON_STANDING_SIGN( BlockCrimsonStandingSign.class ),
    WARPED_STANDING_SIGN( BlockWarpedStandingSign.class ),
    CRIMSON_WALL_SIGN( BlockCrimsonWallSign.class ),
    WARPED_WALL_SIGN( BlockWarpedWallSign.class ),
    CRIMSON_STAIRS( BlockCrimsonStairs.class ),
    WARPED_STAIRS( BlockWarpedStairs.class ),
    CRIMSON_FENCE( BlockCrimsonFence.class ),
    WARPED_FENCE( BlockWarpedFence.class ),
    CRIMSON_FENCE_GATE( BlockCrimsonFenceGate.class ),
    WARPED_FENCE_GATE( BlockWarpedFenceGate.class ),
    CRIMSON_BUTTON( BlockCrimsonButton.class ),
    WARPED_BUTTON( BlockWarpedButton.class ),
    CRIMSON_PRESSURE_PLATE( BlockCrimsonPressurePlate.class ),
    WARPED_PRESSURE_PLATE( BlockWarpedPressurePlate.class ),
    CRIMSON_SLAB( BlockCrimsonSlab.class ),
    WARPED_SLAB( BlockWarpedSlab.class ),
    CRIMSON_DOUBLE_SLAB( BlockCrimsonDoubleSlab.class ),
    WARPED_DOUBLE_SLAB( BlockWarpedDoubleSlab.class ),
    SOUL_TORCH( BlockSoulTorch.class ),
    SOUL_LANTERN( BlockSoulLantern.class ),
    NETHERITE_BLOCK( BlockNetheriteBlock.class ),
    ANCIENT_DEBRIS( BlockAncientDebris.class ),
    RESPAWN_ANCHOR( BlockRespawnAnchor.class ),
    BLACKSTONE( BlockBlackstone.class ),
    POLISHED_BLACKSTONE_BRICKS( BlockPolishedBlackstoneBricks.class ),
    PLOISHED_BLACKSTONE_BRICK_STAIRS( BlockPolishedBlackstoneBrickStairs.class ),
    BLACKSTONE_STAIRS( BlockBlackstoneStairs.class ),
    BLACKSTONE_WALL( BlockBlackstoneWall.class ),
    POLISHED_BLACKSTONE_BRICK_WALL( BlockPolishedBlackstoneBrickWall.class ),
    CHISELED_POLISHED_BLACKSTONE( BlockChiseledPolishedBlackstone.class ),
    CRACKED_POLISHED_BLACKSTONE_BRICKS( BlockCrackedPolishedBlackstoneBricks.class ),
    GILDED_BLACKSTONE( BlockGildedBlackstone.class ),
    BLACKSTONE_SLAB( BlockBlackstoneSlab.class ),
    BLACKSTONE_DOUBLE_SLAB( BlockBlackstoneDoubleSlab.class ),
    POLISHED_BLACKSTONE_BRICK_SLAB( BlockPolishedBlackstoneBrickSlab.class ),
    CHAIN( BlockChain.class ),
    TWISTING_VINES( BlockTwistingVines.class ),
    NETHER_GOLD_ORE( BlockNetherGoldOre.class ),
    CRYING_OBSIDIAN( BlockCryingObsidian.class ),
    SOUL_CAMPFIRE( BlockSoulCampfire.class ),
    POLISHED_BLACKSTONE( BlockPolishedBlackstone.class ),
    POLISHED_BLACKSTONE_STAIRS( BlockPolishedBlackstoneStairs.class ),
    POLISHED_BLACKSTONE_SLAB( BlockPolishedBlackstoneSlab.class ),
    POLISHED_BLACKSTONE_DOUBLE_SLAB( BlockPolishedBlackstoneDoubleSlab.class ),
    POLISHED_BLACKSTONE_PRESSURE_PLATE( BlockPolishedBlackstonePressurePlate.class ),
    POLISHED_BLACKSTONE_BUTTON( BlockPolishedBlackstoneButton.class ),
    POLISHED_BLACKSTONE_WALL( BlockPolishedBlackstoneWall.class ),
    WARPED_HYPHAE( BlockWarpedHyphae.class ),
    CRIMSON_HYPHAE( BlockCrimsonHyphae.class ),
    STRIPPED_CRIMSON_HYPHAE( BlockStrippedCrimsonHyphae.class ),
    STRIPPED_WARPED_HYPHAE( BlockStrippedWarpedHyphae.class ),
    CHISELED_NETHER_BRICKS( BlockChiseledNetherBricks.class ),
    CRACKED_NETHER_BRICKS( BlockCrackedNetherBricks.class ),
    QUARTZ_BRICKS( BlockQuartzBricks.class );

    private static final AtomicBoolean INITIALIZED = new AtomicBoolean( false );

    public static void init() {
        System.out.println( "Loading blocks..." );
        if ( INITIALIZED.get() ) {
            return;
        }
        INITIALIZED.set( true );

        BlockPalette.init();

        System.out.println( "Blocks loading successfully" );
    }

    private Class<? extends Block> blockClass;

    @SneakyThrows
    public <B extends Block> B getBlock() {
        return (B) this.blockClass.newInstance();
    }

}
