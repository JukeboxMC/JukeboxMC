package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBlastFurnace;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBlastFurnace extends Item {

    public ItemBlastFurnace() {
        super( "minecraft:blast_furnace", -196 );
    }

    @Override
    public Block getBlock() {
        return new BlockBlastFurnace();
    }
}
