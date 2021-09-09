package jukeboxmc.item;

import org.jukeboxmc.block.BlockDeepslateLapisOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeepslateLapisOre extends Item{

    public ItemDeepslateLapisOre() {
        super( "minecraft:deepslate_lapis_ore" );
    }

    @Override
    public BlockDeepslateLapisOre getBlock() {
        return new BlockDeepslateLapisOre();
    }
}
