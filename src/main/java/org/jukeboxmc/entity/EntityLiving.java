package org.jukeboxmc.entity;

import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.entity.attribute.AttributeType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class EntityLiving extends Entity {

    protected final Map<AttributeType, Attribute> attributes = new HashMap<>();

    public EntityLiving() {
        super();
        this.addAttribute( AttributeType.HEALTH );
        this.addAttribute( AttributeType.ABSORPTION );
        this.addAttribute( AttributeType.ATTACK_DAMAGE );
        this.addAttribute( AttributeType.FOLLOW_RANGE );
        this.addAttribute( AttributeType.MOVEMENT );
        this.addAttribute( AttributeType.KNOCKBACK_RESISTENCE );
    }

    public void addAttribute( AttributeType attributeType ) {
        this.attributes.put( attributeType, attributeType.getAttribute() );
    }

    public Attribute getAttribute( AttributeType attributeType ) {
        return this.attributes.get( attributeType );
    }

    public void setAttributes( AttributeType attributes, float value ) {
        Attribute attribute = this.attributes.get( attributes );
        if ( attribute == null ) {
            return;
        }
        attribute.setCurrentValue( value );
    }

    public float getAttributeValue( AttributeType attributeType ) {
        return this.getAttribute( attributeType ).getCurrentValue();
    }

    public Collection<Attribute> getAttributes() {
        return this.attributes.values();
    }

    public float getHealth() {
        return this.getAttributeValue( AttributeType.HEALTH );
    }

    public void setHealth( float value ) {
        if ( value < 0 ) {
            value = 0;
        }
        this.setAttributes( AttributeType.HEALTH, value );
    }

    public float getAbsorption() {
        return this.getAttributeValue( AttributeType.ABSORPTION );
    }

    public void setAbsorption( float value ) {
        this.setAttributes( AttributeType.ABSORPTION, value );
    }

    public float getAttackDamage() {
        return this.getAttributeValue( AttributeType.ATTACK_DAMAGE );
    }

    public void setAttackDamage( float value ) {
        this.setAttributes( AttributeType.ATTACK_DAMAGE, value );
    }

    public float getFollowRange() {
        return this.getAttributeValue( AttributeType.FOLLOW_RANGE );
    }

    public void setFollowRange( float value ) {
        this.setAttributes( AttributeType.FOLLOW_RANGE, value );
    }

    public float getMovement() {
        return this.getAttributeValue( AttributeType.MOVEMENT );
    }

    public void setMovement( float value ) {
        this.setAttributes( AttributeType.MOVEMENT, value );
    }

    public float getKnockbackResistence() {
        return this.getAttributeValue( AttributeType.KNOCKBACK_RESISTENCE );
    }

    public void setKnockbackResistence( float value ) {
        this.setAttributes( AttributeType.KNOCKBACK_RESISTENCE, value );
    }
}
