package org.jukeboxmc.form.element;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jukeboxmc.form.CustomForm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GoMint
 * @version 1.0
 */
public class StepSlider extends Element{

    private final CustomForm form;
    private final List<String> steps = new ArrayList<>();
    private int defaultStep = 0;

    public StepSlider( CustomForm form, String id, String text ) {
        super( id, text );
        this.form = form;
    }

    public StepSlider addStep( String step ) {
        this.steps.add( step );
        this.form.setDirty();
        return this;
    }

    public StepSlider addStep( String step, boolean defaultStep ) {
        if ( defaultStep ) {
            this.defaultStep = this.steps.size();
        }

        this.steps.add( step );
        this.form.setDirty();
        return null;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put( "type", "step_slider" );

        JSONArray jsonSteps = new JSONArray();
        jsonSteps.addAll( this.steps );

        obj.put( "steps", jsonSteps );
        obj.put( "default", this.defaultStep );
        return obj;
    }

    @Override
    public Object getAnswer( Object answerOption ) {
        String answer = (String) answerOption;
        this.defaultStep = this.steps.indexOf( answer );
        return answer;
    }

}