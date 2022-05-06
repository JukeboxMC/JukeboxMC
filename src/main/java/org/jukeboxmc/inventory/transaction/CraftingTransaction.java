package org.jukeboxmc.inventory.transaction;

import org.jukeboxmc.Server;
import org.jukeboxmc.event.player.PlayerCraftItemEvent;
import org.jukeboxmc.inventory.InventoryType;
import org.jukeboxmc.inventory.transaction.action.SlotChangeAction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class CraftingTransaction extends InventoryTransaction {

    protected List<Item> inputs;
    protected List<Item> secondaryOutputs;
    protected Item primaryOutput;

    public CraftingTransaction( Player source, List<InventoryAction> actions ) {
        super( source, actions, false );

        this.inputs = new ArrayList<>();
        this.secondaryOutputs = new ArrayList<>();
        init( source, actions );
    }

    public void setInput( Item item ) {
        for ( Item existingInput : this.inputs ) {
            if ( existingInput.equals( item ) ) {
                existingInput.setAmount( existingInput.getAmount() + item.getAmount() );
                return;
            }
        }
        this.inputs.add( item.clone() );
    }

    public List<Item> getInputList() {
        return inputs;
    }

    public void setExtraOutput( Item item ) {
        secondaryOutputs.add( item.clone() );
    }

    public Item getPrimaryOutput() {
        return primaryOutput;
    }

    public void setPrimaryOutput( Item item ) {
        if ( this.primaryOutput == null ) {
            this.primaryOutput = item.clone();
        } else if ( !this.primaryOutput.equals( item ) ) {
            throw new RuntimeException( "Primary result item has already been set and does not match the current item (expected " + this.primaryOutput + ", got " + item + ")" );
        }
    }

    public boolean canExecute() {
        return super.canExecute();
    }

    protected boolean callExecuteEvent() {
        PlayerCraftItemEvent playerCraftItemEvent = new PlayerCraftItemEvent( this.source, this.inputs, this.primaryOutput );
        Server.getInstance().getPluginManager().callEvent( playerCraftItemEvent );
        return !playerCraftItemEvent.isCancelled();
    }

    protected void sendInventories() {
        super.sendInventories();
    }

    public boolean execute() {
        return super.execute();
    }

    public boolean checkForCraftingPart( List<InventoryAction> actions ) {
        for ( InventoryAction action : actions ) {
            if ( action instanceof SlotChangeAction slotChangeAction ) {
                if ( slotChangeAction.getInventory().getInventoryType() == InventoryType.CURSOR && slotChangeAction.getSlot() == 50 &&
                        !slotChangeAction.getSourceItem().equals( slotChangeAction.getTargetItem() ) ) {
                    return true;
                }
            }
        }
        return false;
    }
}
