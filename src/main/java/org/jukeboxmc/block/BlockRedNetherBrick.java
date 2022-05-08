package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemRedNetherBrick;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedNetherBrick extends Block {

    public BlockRedNetherBrick() {
        super( "minecraft:red_nether_brick" );
    }

    @Override
    public ItemRedNetherBrick toItem() {
        return new ItemRedNetherBrick();
    }

    @Override
    public BlockType getType() {
        return BlockType.RED_NETHER_BRICK;
    }

    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }
}
