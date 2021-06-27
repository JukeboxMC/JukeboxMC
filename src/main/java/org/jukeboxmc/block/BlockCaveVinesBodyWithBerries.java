package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCaveVinesBodyWithBerries;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCaveVinesBodyWithBerries extends Block{

    public BlockCaveVinesBodyWithBerries() {
        super( "minecraft:cave_vines_body_with_berries" );
    }

    @Override
    public ItemCaveVinesBodyWithBerries toItem() {
        return new ItemCaveVinesBodyWithBerries();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CAVE_VINES_BODY_WITH_BERRIES;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    public void setGrowingPlantAge( int value ) {
        this.setState( "growing_plant_age", value );
    }

    public int getGrowingPlantAge() {
        return this.stateExists( "growing_plant_age" ) ? this.getIntState( "growing_plant_age" ) : 0;
    }
}
