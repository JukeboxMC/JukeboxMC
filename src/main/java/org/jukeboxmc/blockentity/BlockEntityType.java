package org.jukeboxmc.blockentity;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jukeboxmc.block.Block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@AllArgsConstructor
public enum BlockEntityType {

    FURNACE( "Furnace", BlockEntityFurnace.class ),
    SIGN( "Sign", BlockEntitySign.class ),
    BED( "Bed", BlockEntityBed.class ),
    BEEHIVE( "Beehive", BlockEntityBeehive.class ),
    BANNER( "Banner", BlockEntityBanner.class ),
    SKULL( "Skull", BlockEntitySkull.class );

    private final String blockEntityId;
    private final Class<? extends BlockEntity> blockEntityClass;

    public String getBlockEntityId() {
        return this.blockEntityId;
    }

    public Class<? extends BlockEntity> getBlockEntityClass() {
        return this.blockEntityClass;
    }

    @SneakyThrows
    public <E extends BlockEntity> E createBlockEntity( Block block ) {
        return (E) this.blockEntityClass.getConstructor( Block.class ).newInstance( block );
    }

    public static String getId( Class<? extends BlockEntity> blockEntityClass ) {
        for ( BlockEntityType blockEntityType : values() ) {
            if ( blockEntityType.getBlockEntityClass() == blockEntityClass ) {
                return blockEntityType.getBlockEntityId();
            }
        }
        return null;
    }

    public static BlockEntity getBlockEntityById( String blockEntityId, Block block ) {
        for ( BlockEntityType value : BlockEntityType.values() ) {
            if ( value.getBlockEntityId().equals( blockEntityId ) ) {
                return value.createBlockEntity( block );
            }
        }
        return null;
    }
}
