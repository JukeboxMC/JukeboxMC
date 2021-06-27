package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemArrow extends Item {

    public ItemArrow() {
        super ( "minecraft:arrow" );
    }

    public void setArrowType( ArrowType arrowType ) {
        this.setMeta( arrowType.ordinal() );
    }

    public ArrowType getArrowType() {
        return ArrowType.values()[this.getMeta()];
    }

    public enum ArrowType {
        ARROW,
        TRIPPED,
        TRIPPED2,
        TRIPPED3,
        TRIPPED4,
        TRIPPED5,
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
        INSTANT_HEALTH,
        INSTANT_HEALTH_LEVEL_2,
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
        WITHER_LEVEL_2,
        TURTLE_MASTER,
        TURTLE_MASTER_LEVEL_2,
        TURTLE_MASTER_LEVEL_3,
        SLOW_FALLING,
        SLOW_FALLING_LONG,
        SLOWNESS_SHORT
    }
}
