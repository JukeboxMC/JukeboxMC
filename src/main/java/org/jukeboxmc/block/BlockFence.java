package org.jukeboxmc.block;

import org.jukeboxmc.block.type.WoodType;
import org.jukeboxmc.item.ItemFence;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFence extends BlockWaterlogable {

    public BlockFence() {
        super( "minecraft:fence" );
    }

    @Override
    public ItemFence toItem() {
        return new ItemFence();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.FENCE;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
    }

    public BlockFence setWoodType( WoodType woodType ) {
        this.setState( "wood_type", woodType.name().toLowerCase() );
        return this;
    }

    public WoodType getWoodType() {
        return this.stateExists( "wood_type" ) ? WoodType.valueOf( this.getStringState( "wood_type" ) ) : WoodType.OAK;
    }
}
