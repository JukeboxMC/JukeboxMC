package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPlanks;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.WoodType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPlanks extends Item {

    public ItemPlanks( int blockRuntimeId ) {
        super( "minecraft:planks", blockRuntimeId );
    }

    @Override
    public ItemType getItemType() {
        switch ( this.getWoodType() ) {
            case SPRUCE:
                return ItemType.SPRUCE_PLANKS;
            case BIRCH:
                return ItemType.BIRCH_PLANKS;
            case JUNGLE:
                return ItemType.JUNGLE_PLANKS;
            case ACACIA:
                return ItemType.ACACIA_PLANKS;
            case DARK_OAK:
                return ItemType.DARK_OAK_PLANKS;
            default:
                return ItemType.OAK_PLANKS;
        }
    }

    @Override
    public BlockPlanks getBlock() {
        return (BlockPlanks) BlockType.getBlock( this.blockRuntimeId );
    }


    public WoodType getWoodType() {
        return this.getBlock().getWoodType();
    }

}
