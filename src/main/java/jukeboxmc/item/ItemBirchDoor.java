package jukeboxmc.item;

import org.jukeboxmc.block.BlockBirchDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBirchDoor extends Item {

    public ItemBirchDoor() {
        super("minecraft:birch_door" );
    }

    @Override
    public BlockBirchDoor getBlock() {
        return new BlockBirchDoor();
    }
}
