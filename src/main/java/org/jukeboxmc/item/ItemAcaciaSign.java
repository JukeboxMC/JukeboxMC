package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAcaciaStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaSign extends Item {

    public ItemAcaciaSign() {
        super( "minecraft:acacia_sign", 569 );
    }

    @Override //TODO Calculate
    public BlockAcaciaStandingSign getBlock() {
        return new BlockAcaciaStandingSign();
    }
}
