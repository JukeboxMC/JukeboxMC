package jukeboxmc.item;

import org.jukeboxmc.block.BlockSpruceTrapdoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSpruceTrapdoor extends Item {

    public ItemSpruceTrapdoor() {
        super ( "minecraft:spruce_trapdoor" );
    }

    @Override
    public BlockSpruceTrapdoor getBlock() {
        return new BlockSpruceTrapdoor();
    }
}
