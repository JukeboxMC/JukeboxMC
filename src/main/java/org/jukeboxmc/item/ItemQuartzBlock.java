package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemQuartzBlock extends Item {

    public ItemQuartzBlock() {
        super( "minecraft:quartz_block", 155 );
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
