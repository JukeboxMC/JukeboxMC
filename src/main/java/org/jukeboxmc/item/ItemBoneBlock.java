package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBoneBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBoneBlock extends Item {

    public ItemBoneBlock() {
        super( 216 );
    }

    @Override
    public BlockBoneBlock getBlock() {
        return new BlockBoneBlock();
    }
}
