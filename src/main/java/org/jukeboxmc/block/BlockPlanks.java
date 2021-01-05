package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPlanks extends Block {

    public BlockPlanks() {
        super( "minecraft:planks" );
    }

    @Override
    public void placeBlock( World world, Vector placePosition, Item itemIndHand ) {
        this.setWoodType( WoodType.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
    }

    public void setWoodType( WoodType woodType ) {
        this.setState( "wood_type", woodType.name().toLowerCase() );
    }

    public WoodType getWoodType() {
        return this.stateExists( "wood_type" ) ? WoodType.valueOf( this.getStringState( "wood_type" ).toUpperCase() ) : WoodType.OAK;
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
