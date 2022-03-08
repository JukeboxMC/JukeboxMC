package org.jukeboxmc.inventory;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class CraftingInventory extends ContainerInventory {

    public CraftingInventory( InventoryHolder holder, long holderId, int slots ) {
        super( holder, holderId, slots );
    }
}
