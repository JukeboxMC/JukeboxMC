package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemRedstoneBlock;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedstoneBlock extends Block {

    public BlockRedstoneBlock() {
        super( "minecraft:redstone_block" );
    }

    @Override
    public ItemRedstoneBlock toItem() {
        return new ItemRedstoneBlock();
    }

    @Override
    public BlockType getType() {
        return BlockType.REDSTONE_BLOCK;
    }

    @Override
    public double getHardness() {
        return 5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

}
