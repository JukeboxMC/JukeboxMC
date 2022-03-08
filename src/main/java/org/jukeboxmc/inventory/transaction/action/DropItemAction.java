package org.jukeboxmc.inventory.transaction.action;

import org.jukeboxmc.JukeboxMC;
import org.jukeboxmc.entity.item.EntityItem;
import org.jukeboxmc.event.player.PlayerDropItemEvent;
import org.jukeboxmc.inventory.transaction.InventoryAction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.player.Player;

/**
 * @author CreeperFace
 * @version 1.0
 */
public class DropItemAction extends InventoryAction {

    public DropItemAction( Item source, Item target ) {
        super( source, target );
    }

    public boolean isValid( Player source ) {
        return this.sourceItem != null && this.sourceItem.getItemType().equals( ItemType.AIR );
    }

    @Override
    public boolean onPreExecute( Player player ) {
        return true;
    }

    public boolean execute( Player player ) {
        PlayerDropItemEvent playerDropItemEvent = new PlayerDropItemEvent( player, this.targetItem );
        JukeboxMC.getPluginManager().callEvent( playerDropItemEvent );
        if ( playerDropItemEvent.isCancelled() ) {
            player.getInventory().sendContents( player );
            return false;
        }
        EntityItem entityItem = player.getWorld().dropItem(
                playerDropItemEvent.getItem(),
                player.getLocation().add( 0, player.getEyeHeight(), 0 ),
                player.getLocation().getDirection().multiply( 0.4f, 0.4f, 0.4f ) );
        entityItem.setPlayerHasThrown( player );
        entityItem.spawn();
        return true;
    }

    public void onExecuteSuccess( Player source ) {}

    public void onExecuteFail( Player source ) {}
}
