package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemReinforcedDeepslate;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockReinforcedDeepslate extends Block {

    public BlockReinforcedDeepslate() {
        super( "minecraft:reinforced_deepslate" );
    }

    @Override
    public ItemReinforcedDeepslate toItem() {
        return new ItemReinforcedDeepslate();
    }

    @Override
    public BlockType getType() {
        return BlockType.REINFORCED_DEEPSLATE;
    }
}