package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCrimsonNylium;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCrimsonNylium extends Block {

    public BlockCrimsonNylium() {
        super( "minecraft:crimson_nylium" );
    }

    @Override
    public ItemCrimsonNylium toItem() {
        return new ItemCrimsonNylium();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CRIMSON_NYLIUM;
    }

}
