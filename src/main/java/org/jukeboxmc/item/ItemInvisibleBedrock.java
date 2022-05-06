package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockInvisiblebedrock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemInvisibleBedrock extends Item {

    public ItemInvisibleBedrock() {
        super ( "minecraft:invisible_bedrock" );
    }

    @Override
    public BlockInvisiblebedrock getBlock() {
        return new BlockInvisiblebedrock();
    }
}
