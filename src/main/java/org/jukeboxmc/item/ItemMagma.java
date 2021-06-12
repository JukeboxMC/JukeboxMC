package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockMagma;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMagma extends Item {

    public ItemMagma() {
        super ( "minecraft:magma" );
    }

    @Override
    public BlockMagma getBlock() {
        return new BlockMagma();
    }
}
