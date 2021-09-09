package jukeboxmc.block;

import org.jukeboxmc.item.ItemBirchDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBirchDoor extends BlockDoor {

    public BlockBirchDoor() {
        super( "minecraft:birch_door" );
    }

    @Override
    public ItemBirchDoor toItem() {
        return new ItemBirchDoor();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BIRCH_DOOR;
    }

    @Override
    public BlockBirchDoor newDoor() {
        return new BlockBirchDoor();
    }
}
