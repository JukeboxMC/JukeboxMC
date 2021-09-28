package org.jukeboxmc.command.annotation;

import java.lang.annotation.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Target ( ElementType.TYPE )
@Retention ( RetentionPolicy.RUNTIME )
public @interface PermissionMessage {

    String value() default "";
}
