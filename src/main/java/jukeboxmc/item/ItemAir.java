package jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAir extends Item {

    public ItemAir() {
        super( "minecraft:air" );
    }

    @Override
    public ItemType getItemType() {
        return ItemType.AIR;
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }
}
