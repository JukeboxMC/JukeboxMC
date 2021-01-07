package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedFlower extends Item {

    public ItemRedFlower() {
        super( "minecraft:red_flower", 38 );
    }

    public void setFlowerType( FlowerType flowerType ) {
        this.setMeta( flowerType.ordinal() );
    }

    public FlowerType getFlowerType() {
        return FlowerType.values()[this.getMeta()];
    }

    public enum FlowerType {
        POPPY,
        BLUE_ORCHID,
        ALLIUM,
        AZURE_BLUET,
        RED_TULIP,
        ORANGE_TULIP,
        WHITE_TULIP,
        PINK_TULIP,
        OXEYE_DAISY,
        CORN_FLOWER,
        LILY_OF_THE_VALLEY
    }
}
