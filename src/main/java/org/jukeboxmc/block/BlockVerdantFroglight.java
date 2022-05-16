package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemVerdantFroglight;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockVerdantFroglight extends BlockFroglight {

    public BlockVerdantFroglight() {
        super( "minecraft:verdant_froglight" );
    }

    @Override
    public Item toItem() {
        return new ItemVerdantFroglight();
    }

    @Override
    public BlockType getType() {
        return BlockType.VERDANT_FROGLIGHT;
    }
}