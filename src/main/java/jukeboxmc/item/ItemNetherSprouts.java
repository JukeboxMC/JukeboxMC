package jukeboxmc.item;

import org.jukeboxmc.block.BlockNetherSprouts;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetherSprouts extends Item {

    public ItemNetherSprouts() {
        super ( "minecraft:nether_sprouts" );
    }

    @Override
    public BlockNetherSprouts getBlock() {
        return new BlockNetherSprouts();
    }
}
