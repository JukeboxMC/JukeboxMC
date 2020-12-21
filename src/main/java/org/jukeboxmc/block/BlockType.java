package org.jukeboxmc.block;

import lombok.AllArgsConstructor;
import lombok.Getter;
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
    DOUBLE_PLANT( BlockDoublePlant.class );

    private static final AtomicBoolean INITIALIZED = new AtomicBoolean( false );

    public static void init() {
        if ( INITIALIZED.get() ) {
            return;
        }
        INITIALIZED.set( true );

        for ( BlockType value : values() ) {
            value.getBlock();
        }
    }

    private Class<? extends Block> blockClass;

    @SneakyThrows
    public <B extends Block> B getBlock() {
        return (B) this.blockClass.newInstance();
    }

}
