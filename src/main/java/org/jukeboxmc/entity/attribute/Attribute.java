package org.jukeboxmc.entity.attribute;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Attribute {

    private String key;
    private float minValue;
    private float maxValue;
    private float currentValue;

    public Attribute( String key, float minValue, float maxValue, float currentValue ) {
        this.key = key;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = currentValue;
    }

    public String getKey() {
        return this.key;
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
