package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemOchreFroglight;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockOchreFroglight extends BlockFroglight {

    public BlockOchreFroglight() {
        super( "minecraft:ochre_froglight" );
    }

    @Override
    public Item toItem() {
        return new ItemOchreFroglight();
    }

    @Override
    public BlockType getType() {
        return BlockType.OCHRE_FROGLIGHT;
    }
}