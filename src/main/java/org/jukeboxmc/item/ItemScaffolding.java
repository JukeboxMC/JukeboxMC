package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockScaffolding;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemScaffolding extends Item {

    public ItemScaffolding() {
        super ( "minecraft:scaffolding" );
    }

    @Override
    public BlockScaffolding getBlock() {
        return new BlockScaffolding();
    }
}
