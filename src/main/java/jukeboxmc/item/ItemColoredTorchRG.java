package jukeboxmc.item;

import org.jukeboxmc.block.BlockColoredTorchRG;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemColoredTorchRG extends Item {

    public ItemColoredTorchRG() {
        super ( "minecraft:colored_torch_rg" );
    }

    @Override
    public BlockColoredTorchRG getBlock() {
        return new BlockColoredTorchRG();
    }
}
