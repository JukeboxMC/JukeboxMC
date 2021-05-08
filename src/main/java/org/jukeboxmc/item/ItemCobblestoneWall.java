package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCobblestoneWall;
import org.jukeboxmc.block.type.WallType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCobblestoneWall extends Item {

    public ItemCobblestoneWall() {
        super( 139, 967 );
    }

    @Override
    public ItemType getItemType() {
        switch ( this.getWallType() ) {
            case MOSSY_COBBLESTONE:
                return ItemType.MOSSY_COBBLESTONE_WALL;
            case GRANITE:
                return ItemType.GRANITE_WALL;
            case DIORITE:
                return ItemType.DIORITE_WALL;
            case ANDESITE:
                return ItemType.ANDESITE_WALL;
            case SANDSTONE:
                return ItemType.SANDSTONE_WALL;
            case RED_SANDSTONE:
                return ItemType.RED_SANDSTONE_WALL;
            case STONE_BRICK:
                return ItemType.STONE_BRICK_WALL;
            case MOSSY_STONE_BRICK:
                return ItemType.MOSSY_STONE_BRICK_WALL;
            case BRICK:
                return ItemType.BRICK_WALL;
            case NETHER_BRICK:
                return ItemType.NETHER_BRICK_WALL;
            case RED_NETHER_BRICK:
                return ItemType.RED_NETHER_BRICK_WALL;
            case END_BRICK:
                return ItemType.ENDSTONE_WALL;
            case PRISMARINE:
                return ItemType.PRISMARINE_WALL;
            default:
                return ItemType.COBBLESTONE_WALL;
        }
    }

    @Override
    public BlockCobblestoneWall getBlock() {
        WallType wallType = this.getWallType();
        return new BlockCobblestoneWall().setWallBlockType( wallType );
    }

    public ItemCobblestoneWall setWallType( WallType wallType ) {
        switch ( wallType ) {
            case MOSSY_COBBLESTONE:
                this.setBlockRuntimeId( 968 );
                break;
            case GRANITE:
                this.setBlockRuntimeId( 969 );
                break;
            case DIORITE:
                this.setBlockRuntimeId( 970 );
                break;
            case ANDESITE:
                this.setBlockRuntimeId( 971 );
                break;
            case SANDSTONE:
                this.setBlockRuntimeId( 972 );
                break;
            case RED_SANDSTONE:
                this.setBlockRuntimeId( 979 );
                break;
            case STONE_BRICK:
                this.setBlockRuntimeId( 974 );
                break;
            case MOSSY_STONE_BRICK:
                this.setBlockRuntimeId( 975 );
                break;
            case BRICK:
                this.setBlockRuntimeId( 973 );
                break;
            case NETHER_BRICK:
                this.setBlockRuntimeId( 976 );
                break;
            case RED_NETHER_BRICK:
                this.setBlockRuntimeId( 980 );
                break;
            case END_BRICK:
                this.setBlockRuntimeId( 977 );
                break;
            case PRISMARINE:
                this.setBlockRuntimeId( 978 );
                break;
            default:
                this.setBlockRuntimeId( 967 );
                break;
        }
        return this;
    }

    public WallType getWallType() {
        switch ( this.blockRuntimeId ) {
            case 968:
                return WallType.MOSSY_COBBLESTONE;
            case 969:
                return WallType.GRANITE;
            case 970:
                return WallType.DIORITE;
            case 971:
                return WallType.ANDESITE;
            case 972:
                return WallType.SANDSTONE;
            case 979:
                return WallType.RED_SANDSTONE;
            case 974:
                return WallType.STONE_BRICK;
            case 975:
                return WallType.MOSSY_STONE_BRICK;
            case 973:
                return WallType.BRICK;
            case 976:
                return WallType.NETHER_BRICK;
            case 980:
                return WallType.RED_NETHER_BRICK;
            case 977:
                return WallType.END_BRICK;
            case 978:
                return WallType.PRISMARINE;
            default:
                return WallType.COBBLESTONE;
        }
    }

}
