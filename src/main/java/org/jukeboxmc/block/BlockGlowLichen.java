package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemGlowLichen;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGlowLichen extends Block{

    public BlockGlowLichen() {
        super( "minecraft:glow_lichen" );
    }

    @Override
    public ItemGlowLichen toItem() {
        return new ItemGlowLichen();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GLOW_LICHEN;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
