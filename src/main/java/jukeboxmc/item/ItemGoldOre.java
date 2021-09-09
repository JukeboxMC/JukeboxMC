package jukeboxmc.item;

import org.jukeboxmc.block.BlockGoldOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGoldOre extends Item {

    public ItemGoldOre() {
        super ( "minecraft:gold_ore" );
    }

    @Override
    public BlockGoldOre getBlock() {
        return new BlockGoldOre();
    }
}
