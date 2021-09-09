package jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedBlackstone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedBlackstone extends Item {

    public ItemPolishedBlackstone() {
        super ( "minecraft:polished_blackstone" );
    }

    @Override
    public BlockPolishedBlackstone getBlock() {
        return new BlockPolishedBlackstone();
    }
}
