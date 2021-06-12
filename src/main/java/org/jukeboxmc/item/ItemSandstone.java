package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSandstone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSandstone extends Item {

    public ItemSandstone() {
        super ( "minecraft:sandstone" );
    }

    @Override
    public BlockSandstone getBlock() {
        return new BlockSandstone();
    }

    public void setSandStoneType( SandStoneType sandStoneType ) {
        this.setMeta( sandStoneType.ordinal() );
    }

    public SandStoneType getSandStoneType() {
        return SandStoneType.values()[this.getMeta()];
    }

    public enum SandStoneType {
        SANDSTONE,
        CHISELED_SANDSTONE,
        CUT_SANDSTONE,
        SMOOTH_SANDSTONE
    }
}
