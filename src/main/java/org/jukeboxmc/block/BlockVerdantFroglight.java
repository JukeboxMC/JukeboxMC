package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemVerdantFroglight;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockVerdantFroglight extends Block {

    public BlockVerdantFroglight() {
        super( "minecraft:verdant_froglight" );
    }

    @Override
    public ItemVerdantFroglight toItem() {
        return new ItemVerdantFroglight();
    }

    @Override
    public BlockType getType() {
        return BlockType.VERDANT_FROGLIGHT;
    }
}