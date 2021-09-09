package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemQuartzOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockQuartzOre extends Block {

    public BlockQuartzOre() {
        super( "minecraft:quartz_ore" );
    }

    @Override
    public ItemQuartzOre toItem() {
        return new ItemQuartzOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.QUARTZ_ORE;
    }

}
