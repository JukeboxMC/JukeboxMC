package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemOchreFroglight;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockOchreFroglight extends Block {

    public BlockOchreFroglight() {
        super( "minecraft:ochre_froglight" );
    }

    @Override
    public ItemOchreFroglight toItem() {
        return new ItemOchreFroglight();
    }

    @Override
    public BlockType getType() {
        return BlockType.OCHRE_FROGLIGHT;
    }
}