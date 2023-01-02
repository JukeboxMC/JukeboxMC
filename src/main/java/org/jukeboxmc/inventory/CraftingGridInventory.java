package org.jukeboxmc.inventory;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class CraftingGridInventory extends ContainerInventory{

    public CraftingGridInventory( InventoryHolder holder, int holderId, int size ) {
        super( holder, holderId, size );
    }

    public abstract int getOffset();
}
