package org.jukeboxmc.item;

import org.jukeboxmc.Server;
import org.jukeboxmc.event.player.PlayerConsumeItemEvent;
import org.jukeboxmc.item.behavior.ItemFoodBehavior;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemHoneyBottle extends ItemFoodBehavior {

    public ItemHoneyBottle() {
        super ( "minecraft:honey_bottle" );
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
        }
        this.updateItem( player, false );
        return false;
    }

    @Override
    public void updateItem( Player player, boolean playSound ) {
        if ( player.getGameMode().equals( GameMode.SURVIVAL ) ) {
            boolean removeItem = ( this.amount -= 1 ) <= 0;
            if ( removeItem ) {
                player.getInventory().setItemInHand( new ItemAir() );
            }
            player.getInventory().setItemInHand( this );
            player.getInventory().addItem( new ItemGlassBottle() );
        }
    }

    @Override
    public float getSaturation() {
        return 1.2f;
    }

    @Override
    public int getHunger() {
        return 6;
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }
}
