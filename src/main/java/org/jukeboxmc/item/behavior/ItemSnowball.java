package org.jukeboxmc.item.behavior;

import com.nukkitx.protocol.bedrock.data.SoundEvent;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.EntityType;
import org.jukeboxmc.entity.projectile.EntitySnowball;
import org.jukeboxmc.event.entity.ProjectileLaunchEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;

import java.util.Objects;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSnowball extends Item {

    public ItemSnowball( Identifier identifier ) {
        super( identifier );
    }

    public ItemSnowball( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public boolean useInAir(@NotNull Player player, @NotNull Vector clickVector ) {
        EntitySnowball entitySnowball = Objects.requireNonNull(Entity.create( EntityType.SNOWBALL ));
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
                player.getInventory().removeItem( ItemType.SNOWBALL, 1 );
            }
            entitySnowball.spawn();
            player.getWorld().playSound( player.getLocation(), SoundEvent.THROW, -1, "minecraft:player", false, false );
            return true;
        }
        return false;
    }
}
