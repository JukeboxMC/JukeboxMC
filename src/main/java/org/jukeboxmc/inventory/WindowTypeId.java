package org.jukeboxmc.inventory;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum WindowTypeId {

    INVENTORY( (byte) -1 ),
    CONTAINER( (byte) 0 ),
    WORKBENCH( (byte) 1 ),
    FURNACE( (byte) 2 ),
    ENCHANTMENT( (byte) 3 ),
    BREWING_STAND( (byte) 4 ),
    ANVIL( (byte) 5 ),
    DISPENSER( (byte) 6 ),
    DROPPER( (byte) 7 ),
    HOPPER( (byte) 8 ),
    CAULDRON( (byte) 9 ),
    MINECART_CHEST( (byte) 10 ),
    MINECART_HOPPER( (byte) 11 ),
    HORSE( (byte) 12 ),
    BEACON( (byte) 13 ),
    STRUCTURE_EDITOR( (byte) 14 ),
    TRADING( (byte) 15 ),
    COMMAND_BLOCK( (byte) 16 ),
    JUKEBOX( (byte) 17 ),
    ARMOR( (byte) 18 ),
    HAND( (byte) 19 ),
    COMPOUND_CREATOR( (byte) 20 ),
    ELEMENT_CONSTRUCTOR( (byte) 21 ),
    MATERIAL_REDUCER( (byte) 22 ),
    LAB_TABLE( (byte) 23 ),
    LOOM( (byte) 24 ),
    LECTERN( (byte) 25 ),
    GRINDSTONE( (byte) 26 ),
    BLAST_FURNACE( (byte) 27 ),
    SMOKER( (byte) 28 ),
    STONECUTTER( (byte) 29 ),
    CARTOGRAPHY( (byte) 30 ),
    HUD( (byte) 31 ),
    JIGSAW_EDITOR( (byte) 32 ),
    SMITHING_TABLE( (byte) 33 );

    private byte id;

    WindowTypeId( byte id ) {
        this.id = id;
    }

    public byte getId() {
        return this.id;
    }
}
