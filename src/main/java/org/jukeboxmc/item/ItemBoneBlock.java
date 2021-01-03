package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBoneBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBoneBlock extends Item {

    public ItemBoneBlock() {
        super( "minecraft:bone_block", 216 );
    }

    @Override
    public Block getBlock() {
        return new BlockBoneBlock();
    }
}
