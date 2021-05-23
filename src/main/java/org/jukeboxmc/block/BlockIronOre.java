package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemIronOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockIronOre extends Block {

    public BlockIronOre() {
        super( "minecraft:iron_ore" );
    }

    @Override
    public ItemIronOre toItem() {
        return new ItemIronOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.IRON_ORE;
    }

}
