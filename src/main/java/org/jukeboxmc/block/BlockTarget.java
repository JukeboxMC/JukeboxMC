package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemTarget;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockTarget extends Block {

    public BlockTarget() {
        super( "minecraft:target" );
    }

    @Override
    public ItemTarget toItem() {
        return new ItemTarget();
    }

    @Override
    public BlockType getType() {
        return BlockType.TARGET;
    }

}
