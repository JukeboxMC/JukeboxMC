package org.jukeboxmc.form.element;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Kaooot
 * @version 1.0
 */
@Value
@Accessors ( fluent = true )
public class NpcDialogueButton {

    String text;
    List<String> commands;
    ButtonMode mode;
    Runnable click;

    public enum ButtonMode {
        BUTTON_MODE,
        ON_ENTER,
        ON_EXIT
    }

    public JsonObject toJsonObject() {
        JsonObject button = new JsonObject();
        button.addProperty( "button_name", this.text );

        JsonArray data = new JsonArray();

        for ( String command : this.commands ) {
            JsonObject cmdLine = new JsonObject();
            cmdLine.addProperty( "cmd_line", command );
            cmdLine.addProperty( "cmd_ver", 19 );

            data.add( cmdLine );
        }

        button.add( "data", data );
        button.addProperty( "mode", this.mode.ordinal() );
        button.addProperty( "text", "" );
        button.addProperty( "type", 1 );

        return button;
    }
}