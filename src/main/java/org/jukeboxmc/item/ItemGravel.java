package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockGravel;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGravel extends Item {

    public ItemGravel() {
        super ( "minecraft:gravel" );
    }

    @Override
    public BlockGravel getBlock() {
        return new BlockGravel();
    }
}
