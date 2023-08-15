package org.jukeboxmc.form.element;

import com.nimbusds.jose.shaded.json.JSONObject;

/**
 * @author GoMint
 * @version 1.0
 */
public class ImageButton extends Button {

    private final String image;

    public ImageButton( String id, String text, String image ) {
        super( id, text );
        this.image = image;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject button = super.toJSON();
        JSONObject jsonIcon = new JSONObject();
        jsonIcon.put( "type", this.image.startsWith( "http" ) || this.image.startsWith( "https" ) ? "url" : "path" );
        jsonIcon.put( "data", this.image );
        button.put( "image", jsonIcon );
        return button;
    }

}