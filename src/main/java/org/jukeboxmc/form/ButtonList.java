package org.jukeboxmc.form;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jukeboxmc.form.element.Button;
import org.jukeboxmc.form.element.ImageButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GoMint
 * @version 1.0
 */
public class ButtonList extends Form<String> {

    private final List<Button> buttons = new ArrayList<>();
    private String content = "";

    public ButtonList( String title ) {
        super( title );
    }

    public @NotNull ButtonList setContent(String content ) {
        this.content = content;
        this.dirty = true;
        return this;
    }

    public @NotNull ButtonList addButton(String id, String text ) {
        Button button = new Button( id, text );
        this.buttons.add( button );
        this.dirty = true;
        return this;
    }

    public @NotNull ButtonList addImageButton(String id, String text, String imagePath ) {
        ImageButton imageButton = new ImageButton( id, text, imagePath );
        this.buttons.add( imageButton );
        this.dirty = true;
        return this;
    }

    @Override
    public @NotNull String getFormType() {
        return "form";
    }

    @Override
    public @NotNull JSONObject toJSON() {
        // Fast out when cached
        if ( this.cache != null && !this.dirty ) {
            return this.cache;
        }

        // Create new JSON view of this form
        JSONObject jsonObject = super.toJSON();
        JSONArray content = new JSONArray();

        for ( Button button : this.buttons ) {
            content.add( button.toJSON() );
        }

        jsonObject.put( "content", this.content );
        jsonObject.put( "buttons", content );

        // Cache and return
        this.cache = jsonObject;
        this.dirty = false;
        return this.cache;
    }

    @Override
    public @Nullable String parseResponse(@NotNull String json ) {
        // Input is LF terminated
        try {
            int buttonId = Integer.parseInt( json.trim() );
            if ( buttonId + 1 > this.buttons.size() ) {
                return null;
            }

            return this.buttons.get( buttonId ).getId();
        } catch ( NumberFormatException e ) {
            return null;
        }
    }

}