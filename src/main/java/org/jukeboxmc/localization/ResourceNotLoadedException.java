package org.jukeboxmc.localization;

import lombok.NoArgsConstructor;

/**
 * @author geNAZt
 * @version 1.0
 */
@NoArgsConstructor
public class ResourceNotLoadedException extends Exception {

    public ResourceNotLoadedException( final Throwable cause ) {
        super( cause );
    }

    public ResourceNotLoadedException( final String message, final Throwable cause ) {
        super( message, cause );
    }

    public ResourceNotLoadedException( final String message ) {
        super( message );
    }
}