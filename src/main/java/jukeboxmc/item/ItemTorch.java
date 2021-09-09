package jukeboxmc.item;

import org.jukeboxmc.block.BlockTorch;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemTorch extends Item {

    public ItemTorch() {
        super ( "minecraft:torch" );
    }

    @Override
    public BlockTorch getBlock() {
        return new BlockTorch();
    }
}
