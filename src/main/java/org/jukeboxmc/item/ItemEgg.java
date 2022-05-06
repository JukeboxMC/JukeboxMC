package org.jukeboxmc.item;

import com.nukkitx.protocol.bedrock.data.SoundEvent;
import org.jukeboxmc.entity.projectile.EntityEgg;
import org.jukeboxmc.event.entity.ProjectileLaunchEvent;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemEgg extends Item {

    public ItemEgg() {
        super( "minecraft:egg" );
    }

    @Override
    public boolean useInAir( Player player, Vector clickVector ) {
        EntityEgg entityEgg = new EntityEgg();
        entityEgg.setShooter( player );
        entityEgg.setLocation( player.getLocation().add( 0, player.getEyeHeight(), 0 ) );
        entityEgg.setVelocity( clickVector.multiply( 1.5f, 1.5f, 1.5f ), false );
        entityEgg.setYaw( player.getYaw() );
        entityEgg.setPitch( player.getPitch() );

        ProjectileLaunchEvent projectileLaunchEvent = new ProjectileLaunchEvent( entityEgg, ProjectileLaunchEvent.Cause.EGG );
        player.getServer().getPluginManager().callEvent( projectileLaunchEvent );
        if ( !projectileLaunchEvent.isCancelled() ) {
            this.updateItem( player, 1 );
            if ( player.getGameMode().equals( GameMode.SURVIVAL ) ) {
                player.getInventory().removeItem( new ItemSnow() );
            }
            entityEgg.spawn();
            player.getWorld().playSound( player.getLocation(), SoundEvent.THROW, -1, "minecraft:player", false, false );
            return true;
        }

        return false;
    }

    @Override
    public int getMaxAmount() {
        return 16;
    }
}
