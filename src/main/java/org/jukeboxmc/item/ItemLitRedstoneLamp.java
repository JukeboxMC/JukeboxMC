package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockGlowningRedstoneLamp;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLitRedstoneLamp extends Item {

    public ItemLitRedstoneLamp() {
        super ( "minecraft:lit_redstone_lamp" );
    }

    @Override
    public BlockGlowningRedstoneLamp getBlock() {
        return new BlockGlowningRedstoneLamp();
    }
}
