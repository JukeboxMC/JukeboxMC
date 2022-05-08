package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockFence;
import org.jukeboxmc.block.type.WoodType;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFence extends Item implements Burnable {

    public ItemFence() {
        super( "minecraft:fence" );
    }

    @Override
    public ItemType getType() {
        switch ( this.getWoodType() ) {
            case SPRUCE:
                return ItemType.SPRUCE_FENCE;
            case BIRCH:
                return ItemType.BIRCH_FENCE;
            case JUNGLE:
                return ItemType.JUNGLE_FENCE;
            case ACACIA:
                return ItemType.ACACIA_FENCE;
            case DARK_OAK:
                return ItemType.DARK_OAK_FENCE;
            default:
                return ItemType.OAK_FENCE;
        }
    }

    @Override
    public BlockFence getBlock() {
        return new BlockFence().setWoodType( this.getWoodType() );
    }

    public ItemFence setWoodType( WoodType woodenType ) {
        switch ( woodenType ) {
            case SPRUCE:
                this.setBlockRuntimeId( 4018 );
                break;
            case BIRCH:
                this.setBlockRuntimeId( 4019 );
                break;
            case JUNGLE:
                this.setBlockRuntimeId( 4020 );
                break;
            case ACACIA:
                this.setBlockRuntimeId( 4021 );
                break;
            case DARK_OAK:
                this.setBlockRuntimeId( 4022 );
                break;
            default:
                this.setBlockRuntimeId( 4023 );
                break;
        }
        return this;
    }

    public WoodType getWoodType() {
        switch ( this.blockRuntimeId ) {
            case 4019:
                return WoodType.SPRUCE;
            case 4020:
                return WoodType.BIRCH;
            case 4021:
                return WoodType.JUNGLE;
            case 4022:
                return WoodType.ACACIA;
            case 4023:
                return WoodType.DARK_OAK;
            default:
                return WoodType.OAK;

        }
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
