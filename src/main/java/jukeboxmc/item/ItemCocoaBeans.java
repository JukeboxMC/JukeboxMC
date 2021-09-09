package jukeboxmc.item;

import org.jukeboxmc.block.BlockCocoa;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCocoaBeans extends Item {

    public ItemCocoaBeans() {
        super ( "minecraft:cocoa_beans" );
    }

    @Override
    public BlockCocoa getBlock() {
        return new BlockCocoa();
    }
}
