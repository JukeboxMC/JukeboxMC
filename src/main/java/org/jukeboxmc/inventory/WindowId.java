package org.jukeboxmc.inventory;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum WindowId {

    PLAYER( (byte) 0 ),
    ARMOR( (byte) 6 ),
    CONTAINER( (byte) 7 ),
    COMBINED_INVENTORY( (byte) 12 ),
    CRAFTING_INPUT( (byte) 13 ),
    CRAFTING_OUTPUT( (byte) 14 ),
    ENCHANTMENT_TABLE_INPUT( (byte) 21 ),
    ENCHANTMENT_TABLE_MATERIAL( (byte) 22 ),
    HOTBAR( (byte) 27 ),
    INVENTORY( (byte) 28 ),
    OFFHAND( (byte) 33 ),
    CURSOR( (byte) 58 ),
    CREATED_OUTPUT( (byte) 59 ),
    OPEN_CONTAINER( (byte) 60 ),
    OFFHAND_DEPRECATED( (byte) 119 ),
    ARMOR_DEPRECATED( (byte) 120 ),
    CURSOR_DEPRECATED( (byte) 124 );

    private byte id;

    WindowId( byte id ) {
        this.id = id;
    }

    public byte getId() {
        return this.id;
    }
}
