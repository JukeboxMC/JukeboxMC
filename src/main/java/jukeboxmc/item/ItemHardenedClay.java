package jukeboxmc.item;

import org.jukeboxmc.block.BlockHardenedClay;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemHardenedClay extends Item {

    public ItemHardenedClay() {
        super ( "minecraft:hardened_clay" );
    }

    @Override
    public BlockHardenedClay getBlock() {
        return new BlockHardenedClay();
    }
}
