package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockQuartzBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemQuartzBlock extends Item {

    public ItemQuartzBlock() {
        super( "minecraft:quartz_block", 155 );
    }

    @Override
    public BlockQuartzBlock getBlock() {
        return new BlockQuartzBlock();
    }

    public void setQuartzType( QuartzType quartzType ) {
        this.setMeta( quartzType.ordinal() );
    }

    public QuartzType getQuartzType() {
        return QuartzType.values()[this.getMeta()];
    }

    public enum QuartzType {
        QUARTZ,
        CHISELED_QUARTZ,
        PILLAR_QUARTZ,
        SMOOTH_QUARTZ
    }

}
