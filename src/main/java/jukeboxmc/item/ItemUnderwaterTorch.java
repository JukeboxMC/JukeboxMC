package jukeboxmc.item;

import org.jukeboxmc.block.BlockUnderwaterTorch;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemUnderwaterTorch extends Item {

    public ItemUnderwaterTorch() {
        super( "minecraft:underwater_torch");
    }

    @Override
    public BlockUnderwaterTorch getBlock() {
        return new BlockUnderwaterTorch();
    }
}
