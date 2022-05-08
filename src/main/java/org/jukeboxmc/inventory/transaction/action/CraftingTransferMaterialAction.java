package org.jukeboxmc.inventory.transaction.action;

import org.jukeboxmc.inventory.transaction.CraftingTransaction;
import org.jukeboxmc.inventory.transaction.InventoryAction;
import org.jukeboxmc.inventory.transaction.InventoryTransaction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.player.Player;

/**
 * @author CreeperFace
 * @version 1.0
 */
public class CraftingTransferMaterialAction extends InventoryAction {

    public CraftingTransferMaterialAction( Item sourceItem, Item targetItem ) {
        super( sourceItem, targetItem );
    }

    @Override
    public void onAddToTransaction( InventoryTransaction transaction ) {
        if ( transaction instanceof CraftingTransaction ) {
            if ( this.sourceItem == null || this.sourceItem.getType().equals( ItemType.AIR ) ) {
                ( (CraftingTransaction) transaction ).setInput( this.targetItem );
            } else if ( this.targetItem == null || this.targetItem.getType().equals( ItemType.AIR ) ) {
                ( (CraftingTransaction) transaction ).setExtraOutput( this.sourceItem );
            } else {
                throw new RuntimeException( "Invalid " + getClass().getName() + ", either source or target item must be air, got source: " + this.sourceItem + ", target: " + this.targetItem );
            }
        } else {
            throw new RuntimeException( getClass().getName() + " can only be added to CraftingTransactions" );
        }
    }

    @Override
    public boolean isValid( Player source ) {
        return true;
    }

    @Override
    public boolean execute( Player source ) {
        return true;
    }

    @Override
    public void onExecuteSuccess( Player player ) {

    }

    @Override
    public void onExecuteFail( Player source ) {

    }
}
