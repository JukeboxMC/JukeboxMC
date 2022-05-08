package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCobblestoneWall;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.WallType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCobblestoneWall extends Item {

    public ItemCobblestoneWall( int blockRuntimeId ) {
        super( "minecraft:cobblestone_wall", blockRuntimeId );
    }

    @Override
    public ItemType getType() {
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
        return (BlockCobblestoneWall) BlockType.getBlock( this.blockRuntimeId );
    }

    public WallType getWallType() {
        return this.getBlock().getWallBlockType();
    }

}
