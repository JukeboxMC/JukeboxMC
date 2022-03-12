package org.jukeboxmc.form;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jukeboxmc.form.element.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GoMint
 * @version 1.0
 */
public class CustomForm extends Form<FormResponse> {

    private final List<Element> elements = new ArrayList<>();

    public CustomForm( String title ) {
        super( title );
    }

    public Dropdown createDropdown( String id, String text ) {
        Dropdown dropdown = new Dropdown( this, id, text );
        this.elements.add( dropdown );
        this.dirty = true;
        return dropdown;
    }

    public CustomForm addInputField( String id, String text, String placeHolder, String defaultValue ) {
        Input input = new Input( id, text, placeHolder, defaultValue );
        this.elements.add( input );
        this.dirty = true;
        return this;
    }

    public CustomForm addLabel( String text ) {
        Label label = new Label( "", text );
        this.elements.add( label );
        this.dirty = true;
        return this;
    }

    public CustomForm addSlider( String id, String text, float min, float max, float step, float defaultValue ) {
        Slider slider = new Slider( id, text, min, max, step, defaultValue );
        this.elements.add( slider );
        this.dirty = true;
        return this;
    }

    public StepSlider createStepSlider( String id, String text ) {
        StepSlider stepSlider = new StepSlider( this, id, text );
        this.elements.add( stepSlider );
        this.dirty = true;
        return stepSlider;
    }

    public CustomForm addToggle( String id, String text, boolean value ) {
        Toggle toggle = new Toggle( id, text, value );
        this.elements.add( toggle );
        this.dirty = true;
        return this;
    }

    @Override
    public String getFormType() {
        return "custom_form";
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();

        JSONArray content = (JSONArray) obj.get( "content" );
        for ( Element element : this.elements ) {
            content.add( element.toJSON() );
        }

        return obj;
    }

    @Override
    public FormResponse parseResponse( String json ) {
        // Response is an array with values
        try {
            FormResponse response = new FormResponse();
            JSONArray answers = (JSONArray) new JSONParser().parse( json.trim() );
            for ( int i = 0; i < answers.size(); i++ ) {
                Element element = this.elements.get( i );
                response.addAnswer( element.getId(), element.getAnswer( answers.get( i ) ) );
            }

            return response;
        } catch ( Exception e ) {
            return null;
        }
    }

}