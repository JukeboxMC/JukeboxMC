package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemDeepslateIronOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateIronOre extends Block {

    public BlockDeepslateIronOre() {
        super( "minecraft:deepslate_iron_ore" );
    }

    @Override
    public ItemDeepslateIronOre toItem() {
        return new ItemDeepslateIronOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE_IRON_ORE;
    }
}
