package org.jukeboxmc.entity.attribute;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum AttributeType {

    ABSORPTION( "minecraft:absorption" ),
    PLAYER_SATURATION( "minecraft:player.saturation" ),
    PLAYER_EXHAUSTION( "minecraft:player.exhaustion" ),
    KNOCKBACK_RESISTENCE( "minecraft:knockback_resistance" ),
    HEALTH( "minecraft:health" ),
    MOVEMENT( "minecraft:movement" ),
    FOLLOW_RANGE( "minecraft:follow_range" ),
    PLAYER_HUNGER( "minecraft:player.hunger" ),
    ATTACK_DAMAGE( "minecraft:attack_damage" ),
    PLAYER_LEVEL( "minecraft:player.level" ),
    PLAYER_EXPERIENCE( "minecraft:player.experience" ),
    UNDERWATER_MOVEMENT( "minecraft:underwater_movement" ),
    LUCK( "minecraft:luck" ),
    FALL_DAMAGE( "minecraft:fall_damage" ),
    HORSE_JUMP_STRENGTH( "minecraft:horse.jump_strength" ),
    ZOMBIE_SPAWN_RAINFORCEMENTS( "minecraft:zombie.spawn_reinforcements" ),
    LAVA_MOVEMENT( "minecraft:lava_movement" );

    private String name;

    AttributeType( String name ) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
