package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockChiseledPolishedBlackstone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChiseledPolishedBlackstone extends Item {

    public ItemChiseledPolishedBlackstone() {
        super( "minecraft:chiseled_polished_blackstone", -279 );
    }

    @Override
    public BlockChiseledPolishedBlackstone getBlock() {
        return new BlockChiseledPolishedBlackstone();
    }
}
