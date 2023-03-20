package org.jukeboxmc.form.element;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

/**
 * @author GoMint
 * @version 1.0
 */
public class Toggle extends Element {

    private boolean value;

    public Toggle( String id, String text, boolean value ) {
        super( id, text );
        this.value = value;
    }

    @Override
    public @NotNull JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put( "type", "toggle" );
        obj.put( "default", this.value );
        return obj;
    }

    @Override
    public @NotNull Object getAnswer(Object answerOption ) {
        boolean answer = (boolean) answerOption;
        this.value = answer;
        return answer;
    }

}