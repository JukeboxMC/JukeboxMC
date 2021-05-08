package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPlanks;
import org.jukeboxmc.block.type.WoodType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPlanks extends Item {

    public ItemPlanks() {
        super( 5,4812 );
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
        return new BlockPlanks().setWoodType( this.getWoodType() );
    }

    public ItemPlanks setWoodType( WoodType woodenType ) {
        switch ( woodenType ) {
            case SPRUCE:
                this.setBlockRuntimeId( 4813 );
                break;
            case BIRCH:
                this.setBlockRuntimeId( 4814 );
                break;
            case JUNGLE:
                this.setBlockRuntimeId( 4815 );
                break;
            case ACACIA:
                this.setBlockRuntimeId( 4816 );
                break;
            case DARK_OAK:
                this.setBlockRuntimeId( 4817 );
                break;
            default:
                this.setBlockRuntimeId( 4812 );
                break;
        }
        return this;
    }

    public WoodType getWoodType() {
        switch ( this.blockRuntimeId ) {
            case 4813:
                return WoodType.SPRUCE;
            case 4814:
                return WoodType.BIRCH;
            case 4815:
                return WoodType.JUNGLE;
            case 4816:
                return WoodType.ACACIA;
            case 4817:
                return WoodType.DARK_OAK;
            default:
                return WoodType.OAK;

        }
    }

}
