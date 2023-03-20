package org.jukeboxmc.item.behavior;

import com.nukkitx.protocol.bedrock.data.SoundEvent;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.EntityType;
import org.jukeboxmc.entity.projectile.EntityFishingHook;
import org.jukeboxmc.event.entity.ProjectileLaunchEvent;
import org.jukeboxmc.item.Burnable;
import org.jukeboxmc.item.Durability;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;

import java.time.Duration;
import java.util.Objects;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFishingRod extends Item implements Durability, Burnable {

    public ItemFishingRod( Identifier identifier ) {
        super( identifier );
    }

    public ItemFishingRod( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public boolean useInAir(@NotNull Player player, Vector clickVector ) {
        if ( player.getEntityFishingHook() == null ) {
            EntityFishingHook entityFishingHook = Objects.requireNonNull(Entity.create( EntityType.FISHING_HOOK ));
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
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }

    @Override
    public int getMaxDurability() {
        return 384;
    }
}
