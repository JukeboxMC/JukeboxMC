package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStructureBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStructureBlock extends Item {

    public ItemStructureBlock() {
        super( "minecraft:structure_block", 252 );
    }

    @Override
    public BlockStructureBlock getBlock() {
        return new BlockStructureBlock();
    }
}
