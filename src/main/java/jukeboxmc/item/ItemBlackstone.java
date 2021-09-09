package jukeboxmc.item;

import org.jukeboxmc.block.BlockBlackstone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBlackstone extends Item {

    public ItemBlackstone() {
        super ( "minecraft:blackstone" );
    }

    @Override
    public BlockBlackstone getBlock() {
        return new BlockBlackstone();
    }
}
