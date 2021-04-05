package org.jukeboxmc.entity.attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Attributes {

    private Map<AttributeType, Attribute> attributes = new HashMap<>();

    public Attributes() {
        this.attributes.put( AttributeType.ABSORPTION, new Attribute( "minecraft:absorption", 0, 0, 0 ) );
        this.attributes.put( AttributeType.PLAYER_SATURATION, new Attribute( "minecraft:player.saturation", 0, 20, 20 ) );
        this.attributes.put( AttributeType.PLAYER_EXHAUSTION, new Attribute( "minecraft:player.exhaustion", 0, 5, 0 ) );
        this.attributes.put( AttributeType.KNOCKBACK_RESISTENCE, new Attribute( "minecraft:knockback_resistance", 0, 1, 0 ) );
        this.attributes.put( AttributeType.HEALTH, new Attribute( "minecraft:health", 0, 20, 20 ) );
        this.attributes.put( AttributeType.MOVEMENT, new Attribute( "minecraft:movement", 0, 1, 0.1f ) );
        this.attributes.put( AttributeType.FOLLOW_RANGE, new Attribute( "minecraft:follow_range", 0, 2048, 16 ) );
        this.attributes.put( AttributeType.PLAYER_HUNGER, new Attribute( "minecraft:player.hunger", 0, 20, 20 ) );
        this.attributes.put( AttributeType.ATTACK_DAMAGE, new Attribute( "minecraft:attack_damage", 0, Float.MAX_VALUE, 1 ) );
        this.attributes.put( AttributeType.PLAYER_LEVEL, new Attribute( "minecraft:player.level", 0, 24791, 0 ) );
        this.attributes.put( AttributeType.PLAYER_EXPERIENCE, new Attribute( "minecraft:player.experience", 0, 1, 0 ) );
        this.attributes.put( AttributeType.LUCK, new Attribute( "minecraft:luck", -1024, 1024, 0 ) );
        this.attributes.put( AttributeType.FALL_DAMAGE, new Attribute( "minecraft:fall_damage", 0, Float.MAX_VALUE, 1 ) );
        this.attributes.put( AttributeType.HORSE_JUMP_STRENGTH, new Attribute( "minecraft:horse.jump_strength", 0, 20, 0.7f ) );
        this.attributes.put( AttributeType.ZOMBIE_SPAWN_RAINFORCEMENTS, new Attribute( "minecraft:zombie.spawn_reinforcements",0, 1, 0 ) );
    }

    public Attribute getAttribute( AttributeType attributeType ) {
        return this.attributes.get( attributeType );
    }

    public List<Attribute> getAttributes() {
        return new ArrayList<>( this.attributes.values() );
    }
}
