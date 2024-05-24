package org.jukeboxmc.server.inventory

enum class WindowId(private val id: Int) {
    PLAYER(0),
    ARMOR(6),
    CONTAINER(7),
    COMBINED_INVENTORY(12),
    CRAFTING_INPUT(13),
    CRAFTING_OUTPUT(14),
    ENCHANTMENT_TABLE_INPUT(21),
    ENCHANTMENT_TABLE_MATERIAL(22),
    HOTBAR(27),
    INVENTORY(28),
    OFFHAND(33),
    CURSOR(58),
    CREATED_OUTPUT(59),
    OPEN_CONTAINER(61),
    OFFHAND_DEPRECATED(119),
    ARMOR_DEPRECATED(120),
    CURSOR_DEPRECATED(124);

    fun getId(): Int {
        return this.id
    }

    companion object {
        fun getWindowId(windowId: Int): WindowId? {
            for (value in entries) {
                if (value.getId() == windowId) {
                    return value
                }
            }
            return null
        }
    }
}
