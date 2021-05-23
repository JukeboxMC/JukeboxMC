package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemGravel;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGravel extends Block {

    public BlockGravel() {
        super( "minecraft:gravel" );
    }

    @Override
    public ItemGravel toItem() {
        return new ItemGravel();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GRAVEL;
    }

}
