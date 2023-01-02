package org.jukeboxmc.item.behavior;

import com.nukkitx.protocol.bedrock.data.SoundEvent;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.EntityType;
import org.jukeboxmc.entity.projectile.EntityEgg;
import org.jukeboxmc.event.entity.ProjectileLaunchEvent;
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
public class ItemEgg extends Item {

    public ItemEgg( Identifier identifier ) {
        super( identifier );
    }

    public ItemEgg( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public boolean useInAir( Player player, Vector clickVector ) {
        EntityEgg entityEgg = Entity.create( EntityType.EGG );
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
                player.getInventory().removeItem( ItemType.EGG, 1 );
            }
            entityEgg.spawn();
            player.getWorld().playSound( player.getLocation(), SoundEvent.THROW, -1, "minecraft:player", false, false );
            return true;
        }

        return false;
    }
}
