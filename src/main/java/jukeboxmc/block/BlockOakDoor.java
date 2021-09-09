package jukeboxmc.block;

import org.jukeboxmc.item.ItemOakDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockOakDoor extends BlockDoor {

    public BlockOakDoor() {
        super( "minecraft:wooden_door" );
    }

    @Override
    public ItemOakDoor toItem() {
        return new ItemOakDoor();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.OAK_DOOR;
    }

    @Override
    public BlockOakDoor newDoor() {
        return new BlockOakDoor();
    }
}
