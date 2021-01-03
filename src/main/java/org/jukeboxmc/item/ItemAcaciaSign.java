package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAcaciaStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaSign extends Item {

    public ItemAcaciaSign() {
        super("minecraft:acacia_sign", 569);
    }

    @Override
    public Block getBlock() {
        return new BlockAcaciaStandingSign();
    }
}
