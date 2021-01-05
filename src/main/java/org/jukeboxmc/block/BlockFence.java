package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFence extends Block {

    public BlockFence() {
        super( "minecraft:fence" );
    }

    public void setWoodType( BlockPlanks.WoodType woodType ) {
        this.setState( "wood_type", woodType.name().toLowerCase() );
    }

    public BlockPlanks.WoodType getWoodType() {
        return this.stateExists( "wood_type" ) ? BlockPlanks.WoodType.valueOf( this.getStringState( "wood_type" ).toUpperCase() ) : BlockPlanks.WoodType.OAK;
    }

    public enum WoodType {
        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE,
        ACACIA,
        DARK_OAK
    }
}
