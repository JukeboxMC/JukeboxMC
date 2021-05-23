package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSplashPotion extends Item {

    public ItemSplashPotion() {
        super( 551 );
    }

    public void setPotionType( PotionType potionType ) {
        this.setMeta( potionType.ordinal() );
    }

    public PotionType getPotionType() {
        return PotionType.values()[this.getMeta()];
    }

    public enum PotionType {
        WATER,
        MUNDANE,
        MUNDANE_LONG,
        THICK,
        AWKWARD,
        NIGHT_VISION,
        NIGHT_VISION_LONG,
        INVISIBILITY,
        INVISIBILITY_LONG,
        JUMP_BOOST,
        JUMP_BOOST_LONG,
        JUMP_BOOST_LEVEL_2,
        FIRE_RESISTANCE,
        FIRE_RESISTANCE_LONG,
        SPEED,
        SPEED_LONG,
        SPEED_LEVEL_2,
        SLOWNESS,
        SLOWNESS_LONG,
        WATER_BREATHING,
        WATER_BREATHING_LONG,
        HEALING,
        HEALING_LEVEL_2,
        INSTANT_DAMAGE,
        INSTANT_DAMAGE_LEVEL_2,
        POISON,
        POISON_LONG,
        POISON_LEVEL_2,
        REGENERATION,
        REGENERATION_LONG,
        REGENERATION_LEVEL_2,
        STRENGTH,
        STRENGTH_LONG,
        STRENGTH_LEVEL_2,
        WEAKNESS,
        WEAKNESS_LONG,
        WITHER
    }


}
