package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockColoredTorchBP;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemColoredTorchBP extends Item {

    public ItemColoredTorchBP() {
        super( "minecraft:colored_torch_bp", 204 );
    }

    @Override
    public Block getBlock() {
        return new BlockColoredTorchBP();
    }
}
