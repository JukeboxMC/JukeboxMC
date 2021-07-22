package org.jukeboxmc.command;

/**
 * @author Kaooot
 * @version 1.0
 */
public enum CommandParamType {

    INT( 0x01 ),
    FLOAT( 0x02 ),
    VALUE( 0x04 ),
    WILDCARD_INT( 0x05 ),
    OPERATOR( 0x06 ),
    TARGET( 0x07 ),
    WILDCARD_TARGET( 0x09 ),
    FILE_PATH( 0x10 ),
    STRING( 0x20 ),
    BLOCK_POS( 0x28 ),
    POSITION( 0x29 ),
    MESSAGE( 0x2C ),
    RAWTEXT( 0x2E ),
    JSON( 0x32 ),
    BLOCK_STATE( 0x3C ),
    COMMAND( 0x3F );

    private final int identifier;

    CommandParamType( int identifier ) {
        this.identifier = identifier;
    }

    public int getIdentifier() {
        return this.identifier;
    }
}