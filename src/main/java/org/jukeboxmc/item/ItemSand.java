package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockSand;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSand extends Item {

    public ItemSand() {
        super( "minecraft:sand", 12 );
    }

    @Override
    public Block getBlock() {
        return new BlockSand();
    }

    public void setSandType( SandType sandType ) {
        this.setMeta( sandType.ordinal() );
    }

    public SandType getSandType() {
        return SandType.values()[this.getMeta()];
    }

    public enum SandType {
        SAND,
        RED_SAND
    }
}
