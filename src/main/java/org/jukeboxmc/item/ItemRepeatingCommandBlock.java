package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRepeatingCommandBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRepeatingCommandBlock extends Item {

    public ItemRepeatingCommandBlock() {
        super( 188 );
    }

    @Override
    public BlockRepeatingCommandBlock getBlock() {
        return new BlockRepeatingCommandBlock();
    }
}
