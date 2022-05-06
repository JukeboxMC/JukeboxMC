package org.jukeboxmc.item;

import com.nukkitx.protocol.bedrock.data.SoundEvent;
import org.jukeboxmc.entity.projectile.EntityFishingHook;
import org.jukeboxmc.event.entity.ProjectileLaunchEvent;
import org.jukeboxmc.item.type.Burnable;
import org.jukeboxmc.item.type.Durability;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFishingRod extends Item implements Durability, Burnable {

    public ItemFishingRod() {
        super( "minecraft:fishing_rod" );
    }

    @Override
    public boolean useInAir( Player player, Vector clickVector ) {
        if ( player.getEntityFishingHook() == null ) {
            EntityFishingHook entityFishingHook = new EntityFishingHook();
            entityFishingHook.setShooter( player );
            entityFishingHook.setLocation( player.getLocation().add( 0, player.getEyeHeight(), 0 ) );

            float force = 1.6f;
            entityFishingHook.setVelocity( new Vector(
                    (float) ( -Math.sin( Math.toRadians( player.getYaw() ) ) * Math.cos( Math.toRadians( player.getPitch() ) ) * force * force ),
                    (float) ( -Math.sin( Math.toRadians( player.getPitch() ) ) * force * force + 0.4f ),
                    (float) ( Math.cos( Math.toRadians( player.getYaw() ) ) * Math.cos( Math.toRadians( player.getPitch() ) ) * force * force ) ), false );

            ProjectileLaunchEvent projectileLaunchEvent = new ProjectileLaunchEvent( entityFishingHook, ProjectileLaunchEvent.Cause.SNOWBALL );
            player.getServer().getPluginManager().callEvent( projectileLaunchEvent );
            if ( !projectileLaunchEvent.isCancelled() ) {
                entityFishingHook.spawn();
                this.updateItem( player, 1 );
                player.setEntityFishingHook( entityFishingHook );
                player.getWorld().playSound( player.getLocation(), SoundEvent.THROW, -1, "minecraft:player", false, false );
                return true;
            }
            return false;
        } else {
            player.getEntityFishingHook().close();
            player.setEntityFishingHook( null );
        }
        return true;
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }

    @Override
    public int getMaxDurability() {
        return 384;
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
