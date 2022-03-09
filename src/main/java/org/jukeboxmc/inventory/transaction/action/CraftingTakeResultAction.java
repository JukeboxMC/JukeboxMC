package org.jukeboxmc.inventory.transaction.action;

import org.jukeboxmc.inventory.transaction.CraftingTransaction;
import org.jukeboxmc.inventory.transaction.InventoryAction;
import org.jukeboxmc.inventory.transaction.InventoryTransaction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

/**
 * @author CreeperFace
 * @version 1.0
 */
public class CraftingTakeResultAction extends InventoryAction {

    public CraftingTakeResultAction( Item sourceItem, Item targetItem ) {
        super( sourceItem, targetItem );
    }

    public void onAddToTransaction( InventoryTransaction transaction ) {
        if ( transaction instanceof CraftingTransaction ) {
            ( (CraftingTransaction) transaction ).setPrimaryOutput( this.getSourceItem() );
        } else {
            throw new RuntimeException( getClass().getName() + " can only be added to CraftingTransactions" );
        }
    }

    @Override
    public boolean isValid( Player player ) {
        return true;
    }

    @Override
    public boolean execute( Player player ) {
        return true;
    }

    @Override
    public void onExecuteSuccess( Player player ) {

    }

    @Override
    public void onExecuteFail( Player player ) {

    }
}
