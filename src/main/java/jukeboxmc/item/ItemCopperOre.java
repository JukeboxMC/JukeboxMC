package jukeboxmc.item;

import org.jukeboxmc.block.BlockCopperOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCopperOre extends Item{

    public ItemCopperOre() {
        super( "minecraft:copper_ore" );
    }

    @Override
    public BlockCopperOre getBlock() {
        return new BlockCopperOre();
    }
}
