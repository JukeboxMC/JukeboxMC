package org.jukeboxmc.entity.attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Attributes {

    private List<Attribute> attributes = new ArrayList<>();

    public Attributes() {
        this.attributes.add( new Attribute( AttributeType.ABSORPTION, 0, 0, 0 ) );
        this.attributes.add( new Attribute( AttributeType.PLAYER_SATURATION, 0, 20, 20 ) );
        this.attributes.add( new Attribute( AttributeType.PLAYER_EXHAUSTION, 0, 5, 0 ) );
        this.attributes.add( new Attribute( AttributeType.KNOCKBACK_RESISTENCE, 0, 1, 0 ) );
        this.attributes.add( new Attribute( AttributeType.HEALTH, 0, 20, 20 ) );
        this.attributes.add( new Attribute( AttributeType.MOVEMENT, 0, Float.MAX_VALUE, 0.1f ) );
        this.attributes.add( new Attribute( AttributeType.FOLLOW_RANGE, 0, 2048, 16 ) );
        this.attributes.add( new Attribute( AttributeType.PLAYER_HUNGER, 0, 20, 20 ) );
        this.attributes.add( new Attribute( AttributeType.ATTACK_DAMAGE, 0, Float.MAX_VALUE, 1 ) );
        this.attributes.add( new Attribute( AttributeType.PLAYER_LEVEL, 0, 24791, 0 ) );
        this.attributes.add( new Attribute( AttributeType.PLAYER_EXPERIENCE, 0, 1, 0 ) );
        this.attributes.add( new Attribute( AttributeType.UNDERWATER_MOVEMENT, 0, Float.MAX_VALUE, 0.2f ) );
        this.attributes.add( new Attribute( AttributeType.LUCK, -1024, 1024, 0 ) );
        this.attributes.add( new Attribute( AttributeType.FALL_DAMAGE, 0, Float.MAX_VALUE, 1 ) );
        this.attributes.add( new Attribute( AttributeType.HORSE_JUMP_STRENGTH, 0, 20, 0.7f ) );
        this.attributes.add( new Attribute( AttributeType.ZOMBIE_SPAWN_RAINFORCEMENTS, 0, 1, 0 ) );
        this.attributes.add( new Attribute( AttributeType.LAVA_MOVEMENT, 0, Float.MAX_VALUE, 0.2f ) );
    }

    public List<Attribute> getAttributes() {
        return this.attributes;
    }
}
