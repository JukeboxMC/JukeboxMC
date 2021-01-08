package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCoralFanDead;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoralFanDead extends Item {

    public ItemCoralFanDead() {
        super( "minecraft:coral_fan_dead", -134 );
    }

    @Override
    public BlockCoralFanDead getBlock() {
        return new BlockCoralFanDead();
    }

    public void setCoralType( CoralType coralType ) {
        this.setMeta( coralType.ordinal() );
    }

    public CoralType getCoralType() {
        return CoralType.values()[this.getMeta()];
    }

    public enum CoralType {
        TUBE,
        BRAIN,
        BUBBLE,
        FIRE,
        HORN
    }
}
