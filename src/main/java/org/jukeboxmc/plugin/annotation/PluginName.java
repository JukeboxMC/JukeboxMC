package org.jukeboxmc.plugin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Target ( ElementType.TYPE )
@Retention ( RetentionPolicy.RUNTIME )
public @interface PluginName {

    String value();
}
