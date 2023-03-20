package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.Server;
import org.jukeboxmc.event.player.PlayerConsumeItemEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class ItemFood extends Item {

    public ItemFood( Identifier identifier ) {
        super( identifier );
    }

    public ItemFood( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public boolean useInAir(@NotNull Player player, Vector clickVector ) {
        if ( player.isHungry() || player.getGameMode().equals( GameMode.CREATIVE ) ) {
            return true;
        }
        player.setHunger( player.getHunger() );
        player.getInventory().sendContents( player );
        return false;
    }

    @Override
    public boolean onUse(@NotNull Player player ) {
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
            this.setAmount( this.amount - 1 );
            player.getInventory().setItemInHand( this );
            return true;
        }
        return false;
    }

    public abstract float getSaturation();

    public abstract int getHunger();

}
