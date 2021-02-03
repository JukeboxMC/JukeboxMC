package org.jukeboxmc.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Target( ElementType.METHOD )
@Retention ( RetentionPolicy.RUNTIME )
public @interface EventHandler {

    EventPriority priority() default EventPriority.NORMAL;

}
