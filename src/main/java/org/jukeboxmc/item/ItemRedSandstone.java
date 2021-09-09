package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRedSandstone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedSandstone extends Item {

    public ItemRedSandstone() {
        super ( "minecraft:red_sandstone" );
    }

    @Override
    public BlockRedSandstone getBlock() {
        return new BlockRedSandstone();
    }

    public void setSandStoneType( SandStoneType sandStoneType ) {
        this.setMeta( sandStoneType.ordinal() );
    }

    public SandStoneType getSandStoneType() {
        return SandStoneType.values()[this.getMeta()];
    }

    public enum SandStoneType {
        RED_SANDSTONE,
        CHISELED_RED_SANDSTONE,
        CUT_RED_SANDSTONE,
        SMOOTH_RED_SANDSTONE
    }

}
