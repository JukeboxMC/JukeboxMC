package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemGoldOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGoldOre extends Block {

    public BlockGoldOre() {
        super( "minecraft:gold_ore" );
    }

    @Override
    public ItemGoldOre toItem() {
        return new ItemGoldOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GOLD_ORE;
    }

}
