package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockColoredTorchRG;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemColoredTorchRG extends Item {

    public ItemColoredTorchRG() {
        super( "minecraft:colored_torch_rg", 202 );
    }

    @Override
    public Block getBlock() {
        return new BlockColoredTorchRG();
    }
}
