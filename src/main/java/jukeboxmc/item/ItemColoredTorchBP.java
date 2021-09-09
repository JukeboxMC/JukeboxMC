package jukeboxmc.item;

import org.jukeboxmc.block.BlockColoredTorchBP;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemColoredTorchBP extends Item {

    public ItemColoredTorchBP() {
        super ( "minecraft:colored_torch_bp" );
    }

    @Override
    public BlockColoredTorchBP getBlock() {
        return new BlockColoredTorchBP();
    }
}
