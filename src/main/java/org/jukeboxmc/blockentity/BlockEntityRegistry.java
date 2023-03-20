package org.jukeboxmc.blockentity;

import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityRegistry {

    private static final Map<BlockEntityType, Class<? extends BlockEntity>> BLOCKENTITYCLASS_FROM_BLOCKENTITYTYPE = new LinkedHashMap<>();
    private static final Map<BlockEntityType, String> BLOCKENTITYID = new LinkedHashMap<>();

    public static void init() {
        register( BlockEntityType.BED, BlockEntityBed.class, "Bed" );
        register( BlockEntityType.FURNACE, BlockEntityFurnace.class, "Furnace" );
        register( BlockEntityType.SIGN, BlockEntitySign.class, "Sign" );
        register( BlockEntityType.BEEHIVE, BlockEntityBeehive.class, "Beehive" );
        register( BlockEntityType.BANNER, BlockEntityBanner.class, "Banner" );
        register( BlockEntityType.SKULL, BlockEntitySkull.class, "Skull" );
        register( BlockEntityType.BLAST_FURNACE, BlockEntityBlastFurnace.class, "BlastFurnace" );
        register( BlockEntityType.SMOKER, BlockEntitySmoker.class, "Smoker" );
        register( BlockEntityType.LECTERN, BlockEntityLectern.class, "Lectern" );
        register( BlockEntityType.ENCHANTMENT_TABLE, BlockEntityEnchantmentTable.class, "EnchantTable" );
        register( BlockEntityType.CHEST, BlockEntityChest.class, "Chest" );
        register( BlockEntityType.ENDER_CHEST, BlockEntityEnderChest.class, "EnderChest" );
        register( BlockEntityType.SHULKER_BOX, BlockEntityShulkerBox.class, "ShulkerBox" );
        register( BlockEntityType.DROPPER, BlockEntityDropper.class, "Dropper" );
        register( BlockEntityType.HOPPER, BlockEntityHopper.class, "Hopper" );
        register( BlockEntityType.DISPENSER, BlockEntityDispenser.class, "Dispenser" );
        register( BlockEntityType.LOOM, BlockEntityLoom.class, "Loom" );
        register( BlockEntityType.BARREL, BlockEntityBarrel.class, "Barrel" );
        register( BlockEntityType.BREWING_STAND, BlockEntityBrewingStand.class, "BrewingStand" );
        register( BlockEntityType.MOB_SPAWNER, BlockEntityMobSpawner.class, "Bell" );
        register( BlockEntityType.BELL, BlockEntityBell.class, "MobSpawner" );
    }

    private static void register( BlockEntityType blockEntityType, Class<? extends BlockEntity> blockEntityClass, String blockEntityId ) {
        BLOCKENTITYCLASS_FROM_BLOCKENTITYTYPE.put( blockEntityType, blockEntityClass );
        BLOCKENTITYID.put( blockEntityType, blockEntityId );
    }

    public static Class<? extends BlockEntity> getBlockEntityClass( BlockEntityType blockEntityType ) {
        return BLOCKENTITYCLASS_FROM_BLOCKENTITYTYPE.get( blockEntityType );
    }

    public static String getBlockEntityType( BlockEntityType blockEntityType ) {
        return BLOCKENTITYID.get( blockEntityType );
    }

    public static @Nullable BlockEntityType getBlockEntityTypeById(String blockEntityId ) {
        for ( Map.Entry<BlockEntityType, String> entry : BLOCKENTITYID.entrySet() ) {
            if ( entry.getValue().equalsIgnoreCase( blockEntityId ) ) {
                return entry.getKey();
            }
        }
        return null;
    }

}
