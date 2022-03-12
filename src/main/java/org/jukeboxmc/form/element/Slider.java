package org.jukeboxmc.form.element;

import org.json.simple.JSONObject;


/**
 * @author GoMint
 * @version 1.0
 */
public class Slider extends Element {

    private final float min;
    private final float max;
    private final float step;
    private float defaultValue;

    public Slider( String id, String text, float min, float max, float step, float defaultValue ) {
        super( id, text );
        this.min = min;
        this.max = max;
        this.step = step;
        this.defaultValue = defaultValue;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put( "type", "slider" );
        obj.put( "min", this.min );
        obj.put( "max", this.max );

        if ( this.step > 0 ) {
            obj.put( "step", this.step );
        }

        if ( this.defaultValue > this.min ) {
            obj.put( "default", this.defaultValue );
        }

        return obj;
    }

    @Override
    public Object getAnswer( Object answerOption ) {
        Double answer = (Double) answerOption;
        this.defaultValue = answer.floatValue();
        return answer;
    }

}