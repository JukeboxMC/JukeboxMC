package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockGlowingFurnace;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBurningFurnace extends Item {

    public ItemBurningFurnace() {
        super("minecraft:lit_furnace", 62);
    }

    @Override
    public Block getBlock() {
        return new BlockGlowingFurnace();
    }
}
