package org.jukeboxmc.block;

import org.jukeboxmc.block.type.WoodType;
import org.jukeboxmc.item.ItemPlanks;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPlanks extends Block {

    public BlockPlanks() {
        super( "minecraft:planks" );
    }

    @Override
    public ItemPlanks toItem() {
        return new ItemPlanks( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.PLANKS;
    }

    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
    }

    public BlockPlanks setWoodType( WoodType woodType ) {
        this.setState( "wood_type", woodType.name().toLowerCase() );
        return this;
    }

    public WoodType getWoodType() {
        return this.stateExists( "wood_type" ) ? WoodType.valueOf( this.getStringState( "wood_type" ) ) : WoodType.OAK;
    }
}
