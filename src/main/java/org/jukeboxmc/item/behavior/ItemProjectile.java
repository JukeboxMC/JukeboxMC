package org.jukeboxmc.item.behavior;

import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.projectile.EntityProjectile;
import org.jukeboxmc.event.entity.ProjectileLaunchEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemSnow;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.LevelSound;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class ItemProjectile extends Item {

    public ItemProjectile( String identifier ) {
        super( identifier );
    }

    public abstract Entity getEntity();

    public abstract float getThrowForce();

    public abstract ProjectileLaunchEvent.Cause getCause();

    @Override
    public boolean useInAir( Player player, Vector clickVector ) {
        Entity entity = this.getEntity();
        entity.setLocation( player.getLocation().add( 0, player.getEyeHeight(), 0 ) );
        entity.setVelocity( clickVector, false );
        entity.setYaw( player.getYaw() );
        entity.setPitch( player.getPitch() );

        entity.setVelocity( entity.getVelocity().multiply( this.getThrowForce(), this.getThrowForce(), this.getThrowForce() ) );

        if ( entity instanceof EntityProjectile ) {
            ( (EntityProjectile) entity ).setShooter( player );

            ProjectileLaunchEvent projectileLaunchEvent = new ProjectileLaunchEvent( entity, this.getCause() );
            player.getServer().getPluginManager().callEvent( projectileLaunchEvent );
            if ( !projectileLaunchEvent.isCancelled() ) {
                this.updateItem( player, 1 );
                if ( player.getGameMode().equals( GameMode.SURVIVAL ) ) {
                    player.getInventory().removeItem( new ItemSnow() );
                }
                entity.spawn();
                player.getWorld().playSound( player.getLocation(), LevelSound.THROW, -1, "minecraft:player", false, false );
                return true;
            }
        }
        return false;
    }
}
