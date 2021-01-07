package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCobblestoneWall extends Item {

    public ItemCobblestoneWall() {
        super( "minecraft:cobblestone_wall", 139 );
    }

    public void setWallType( WallType wallType ) {
        this.setMeta( wallType.ordinal() );
    }

    public WallType getWallType() {
        return WallType.values()[this.getMeta()];
    }

    public enum WallType {
        COBBLESTONE,
        MOSSY_COBBLESTONE,
        GRANITE,
        DIORITE,
        ANDESITE,
        SANDSTONE,
        BRICK,
        STONE_BRICK,
        MOSSY_STONE_BRICK,
        NETHER_BRICK,
        END_BRICK,
        PRISMARINE,
        RED_SANDSTONE,
        RED_NETHER_BRICK
    }

}
