package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemGravel;
import org.jukeboxmc.item.type.ItemToolType;

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

    @Override
    public double getHardness() {
        return 0.6;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.SHOVEL;
    }

}
