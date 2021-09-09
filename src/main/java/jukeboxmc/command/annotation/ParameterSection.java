package jukeboxmc.command.annotation;

import java.lang.annotation.*;

/**
 * @author Kaooot
 * @version 1.0
 */
@Target ( ElementType.TYPE )
@Repeatable ( ParameterSections.class )
@Retention ( RetentionPolicy.RUNTIME )
public @interface ParameterSection {

    /**
     * Represents a section of parameters of a {@link org.jukeboxmc.command.Command}
     *
     * @return a fresh list of {@link Parameter}
     */
    Parameter[] value();
}