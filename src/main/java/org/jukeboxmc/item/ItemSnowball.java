package org.jukeboxmc.item;

import com.nukkitx.protocol.bedrock.data.SoundEvent;
import org.jukeboxmc.entity.projectile.EntitySnowball;
import org.jukeboxmc.event.entity.ProjectileLaunchEvent;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSnowball extends Item {

    public ItemSnowball() {
        super ( "minecraft:snowball" );
    }

    @Override
    public boolean useInAir( Player player, Vector clickVector ) {
        EntitySnowball entitySnowball = new EntitySnowball();
        entitySnowball.setShooter( player );
        entitySnowball.setLocation( player.getLocation().add( 0, player.getEyeHeight(), 0 ) );
        entitySnowball.setVelocity( clickVector.multiply( 1.5f, 1.5f, 1.5f ), false );
        entitySnowball.setYaw( player.getYaw() );
        entitySnowball.setPitch( player.getPitch() );

        ProjectileLaunchEvent projectileLaunchEvent = new ProjectileLaunchEvent( entitySnowball, ProjectileLaunchEvent.Cause.SNOWBALL );
        player.getServer().getPluginManager().callEvent( projectileLaunchEvent );
        if ( !projectileLaunchEvent.isCancelled() ) {
            this.updateItem( player, 1 );
            if ( player.getGameMode().equals( GameMode.SURVIVAL ) ) {
                player.getInventory().removeItem( new ItemSnow() );
            }
            entitySnowball.spawn();
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
