package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMangroveSign;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMangroveWallSign extends BlockWallSign {

    public BlockMangroveWallSign() {
        super( "minecraft:mangrove_wall_sign" );
    }

    @Override
    public Item toItem() {
        return new ItemMangroveSign();
    }

    @Override
    public BlockType getType() {
        return BlockType.MANGROVE_WALL_SIGN;
    }
}