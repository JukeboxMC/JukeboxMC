package jukeboxmc.item;

import org.jukeboxmc.block.BlockJungleDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemJungleDoor extends Item {

    public ItemJungleDoor() {
        super ( "minecraft:jungle_door" );
    }

    @Override
    public BlockJungleDoor getBlock() {
        return new BlockJungleDoor();
    }
}
