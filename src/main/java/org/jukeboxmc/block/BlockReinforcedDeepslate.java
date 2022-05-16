package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
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
    public Item toItem() {
        return new ItemReinforcedDeepslate();
    }

    @Override
    public BlockType getType() {
        return BlockType.REINFORCED_DEEPSLATE;
    }

    @Override
    public double getHardness() {
        return 55;
    }
}