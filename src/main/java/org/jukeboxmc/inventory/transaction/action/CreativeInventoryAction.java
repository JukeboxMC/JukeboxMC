package org.jukeboxmc.inventory.transaction.action;

import org.jukeboxmc.inventory.transaction.InventoryAction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.utils.BedrockResourceLoader;

/**
 * @author CreeperFace
 * @version 1.0
 */
public class CreativeInventoryAction extends InventoryAction {

    public static final int TYPE_DELETE_ITEM = 0;

    protected int actionType;

    public CreativeInventoryAction( Item source, Item target ) {
        super( source, target );
    }

    public boolean isValid( Player player ) {
        if ( player.getGameMode().equals( GameMode.CREATIVE ) ) {
            return this.actionType == TYPE_DELETE_ITEM || BedrockResourceLoader.getCreativeItems().stream().anyMatch( value -> value.get( "id" ).equals( this.sourceItem.getIdentifier() ) );
        }
        return false;
    }

    public int getActionType() {
        return actionType;
    }

    public boolean execute( Player source ) {
        return true;
    }

    public void onExecuteSuccess( Player source ) {
    }

    public void onExecuteFail( Player source ) {
    }
}
