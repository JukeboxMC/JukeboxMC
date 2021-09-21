package org.jukeboxmc.item.behavior;

import org.jukeboxmc.Server;
import org.jukeboxmc.event.player.PlayerConsumeItemEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class ItemFoodBehavior extends Item {

    public ItemFoodBehavior( String identifier ) {
        super( identifier );
    }

    @Override
    public boolean useInAir( Player player, Vector clickVector ) {
        if ( player.isHungry() ) {
            PlayerConsumeItemEvent playerConsumeItemEvent = new PlayerConsumeItemEvent( player, this );
            Server.getInstance().getPluginManager().callEvent( playerConsumeItemEvent );
            if ( playerConsumeItemEvent.isCancelled() ) {
                player.getInventory().sendContents( player );
                return false;
            }
            player.addHunger( this.getHunger() );
            float saturation = Math.min( player.getSaturation() + this.getSaturation(), player.getHunger() );
            player.setSaturation( saturation );
            this.updateItem( player, false );
        }
        return false;
    }

    public abstract float getSaturation();

    public abstract int getHunger();

}
