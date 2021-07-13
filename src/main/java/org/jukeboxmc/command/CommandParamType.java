package org.jukeboxmc.command;

/**
 * @author Kaooot
 * @version 1.0
 */
public enum CommandParamType {

    /**
     * Represents an entity
     */
    TARGET,

    /**
     * Represents a set of strings
     */
    STRING_ENUM,

    /**
     * Represents a boolean value
     */
    BOOL,

    /**
     * Represents an integer value
     */
    INT,

    /**
     * Represents a block position containing x, y and z coordinates
     */
    BLOCK_POS,

    /**
     * Represents a {@link String}
     */
    STRING,

    /**
     * Represents a text which consumes all remaining argument input
     */
    TEXT,

    /**
     * Represents a float decimal value
     */
    FLOAT,

    /**
     * Represents another {@link Command}
     */
    COMMAND
}