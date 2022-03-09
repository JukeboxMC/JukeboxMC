package org.jukeboxmc.inventory.transaction;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

/**
 * @author CreeperFace
 * @version 1.0
 */
public abstract class InventoryAction {

    private final long creationTime;

    protected Item sourceItem;

    protected Item targetItem;

    public InventoryAction( Item sourceItem, Item targetItem ) {
        this.sourceItem = sourceItem;
        this.targetItem = targetItem;

        this.creationTime = System.currentTimeMillis();
    }

    public long getCreationTime() {
        return creationTime;
    }

    public Item getSourceItem() {
        return sourceItem.clone();
    }

    public Item getTargetItem() {
        return targetItem.clone();
    }

    public boolean onPreExecute( Player source ) {
        return true;
    }

    abstract public boolean isValid( Player source );

    public void onAddToTransaction( InventoryTransaction transaction ) {}

    abstract public boolean execute( Player source );

    abstract public void onExecuteSuccess( Player source );

    abstract public void onExecuteFail( Player source );
}
