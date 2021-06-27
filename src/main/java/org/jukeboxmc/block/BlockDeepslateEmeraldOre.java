package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemDeepslateEmeraldOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateEmeraldOre extends Block{

    public BlockDeepslateEmeraldOre() {
        super( "minecraft:deepslate_emerald_ore" );
    }

    @Override
    public ItemDeepslateEmeraldOre toItem() {
        return new ItemDeepslateEmeraldOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE_EMERALD_ORE;
    }
}
