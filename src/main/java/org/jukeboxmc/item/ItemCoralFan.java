package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoralFan extends Item {

    public ItemCoralFan() {
        super( "minecraft:coral_fan", -133 );
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
