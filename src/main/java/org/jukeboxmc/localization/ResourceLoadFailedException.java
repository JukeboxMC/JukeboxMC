package org.jukeboxmc.localization;

import lombok.NoArgsConstructor;

/**
 * @author geNAZt
 * @version 1.0
 */
@NoArgsConstructor
public class ResourceLoadFailedException extends Exception {

    public ResourceLoadFailedException( final Throwable cause ) {
        super( cause );
    }

    public ResourceLoadFailedException( final String message, final Throwable cause ) {
        super( message, cause );
    }

    public ResourceLoadFailedException( final String message ) {
        super( message );
    }

}