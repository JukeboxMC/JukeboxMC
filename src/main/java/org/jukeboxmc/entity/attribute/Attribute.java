package org.jukeboxmc.entity.attribute;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Attribute {

    private AttributeType attributeType;
    private float minValue;
    private float maxValue;
    private float currentValue;

    public Attribute( AttributeType attributeType, float minValue, float maxValue, float currentValue ) {
        this.attributeType = attributeType;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = currentValue;
    }

    public AttributeType getAttributeType() {
        return this.attributeType;
    }

    public float getMinValue() {
        return this.minValue;
    }

    public void setMinValue( float minValue ) {
        this.minValue = minValue;
    }

    public float getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue( float maxValue ) {
        this.maxValue = maxValue;
    }

    public float getCurrentValue() {
        return this.currentValue;
    }

    public void setCurrentValue( float currentValue ) {
        this.currentValue = currentValue;
    }
}
