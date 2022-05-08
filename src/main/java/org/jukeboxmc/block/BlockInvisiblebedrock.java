package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemInvisibleBedrock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockInvisiblebedrock extends Block {

    public BlockInvisiblebedrock() {
        super( "minecraft:invisible_bedrock" );
    }

    @Override
    public ItemInvisibleBedrock toItem() {
        return new ItemInvisibleBedrock();
    }

    @Override
    public BlockType getType() {
        return BlockType.INVISIBLE_BEDROCK;
    }

}
