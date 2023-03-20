package org.jukeboxmc.form;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author GoMint
 * @version 1.0
 */
public class FormResponse {

    private final Map<String, Object> answers = new HashMap<>();

    public void addAnswer( String id, Object data ) {
        this.answers.put( id, data );
    }

    public @Nullable Boolean getToggle(String id ) {
        Object val = this.answers.get( id );
        if ( val != null ) {
            if ( val instanceof Boolean ) {
                return (Boolean) val;
            }
        }

        return null;
    }

    public @Nullable String getStepSlider(String id ) {
        Object val = this.answers.get( id );
        if ( val != null ) {
            if ( val instanceof String ) {
                return (String) val;
            }
        }

        return null;
    }

    public @Nullable Float getSlider(String id ) {
        Object val = this.answers.get( id );
        if ( val != null ) {
            if ( val instanceof Double ) {
                return ( (Double) val ).floatValue();
            }
        }

        return null;
    }

    public @Nullable String getInput(String id ) {
        Object val = this.answers.get( id );
        if ( val != null ) {
            if ( val instanceof String ) {
                return (String) val;
            }
        }

        return null;
    }

    public @Nullable String getDropbox(String id ) {
        Object val = this.answers.get( id );
        if ( val != null ) {
            if ( val instanceof String ) {
                return (String) val;
            }
        }

        return null;
    }

}