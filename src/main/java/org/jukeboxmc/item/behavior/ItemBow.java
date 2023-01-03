package org.jukeboxmc.item.behavior;

import org.jukeboxmc.Server;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.EntityType;
import org.jukeboxmc.entity.projectile.EntityArrow;
import org.jukeboxmc.event.entity.ProjectileLaunchEvent;
import org.jukeboxmc.item.Burnable;
import org.jukeboxmc.item.Durability;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.enchantment.Enchantment;
import org.jukeboxmc.item.enchantment.EnchantmentInfinity;
import org.jukeboxmc.item.enchantment.EnchantmentType;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.Sound;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBow extends Item implements Durability, Burnable {

    public ItemBow( Identifier identifier ) {
        super( identifier );
    }

    public ItemBow( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public boolean useInAir( Player player, Vector clickVector ) {
        player.setAction( true );
        return super.useInAir( player, clickVector );
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }

    @Override
    public int getMaxDurability() {
        return 384;
    }

    public void shoot( Player player ) {
        if ( player.getActionStart() == -1 ) {
            return;
        }

        float tick = (float) ( Server.getInstance().getCurrentTick() - player.getActionStart() ) / 20;
        float force = Math.min( ( tick * tick + tick * 2 ) / 3, 1 ) * 2;

        player.setAction( false );

        if ( !player.getInventory().contains( Item.create( ItemType.ARROW ) ) && ( !player.getGameMode().equals( GameMode.CREATIVE ) ) ) {
            return;
        }

        int powerModifier = 0;
        Enchantment power =this.getEnchantment( EnchantmentType.POWER );
        if ( power != null ) {
            powerModifier = power.getLevel();
        }

        int punchModifier = 0;
        Enchantment punch = this.getEnchantment( EnchantmentType.PUNCH );
        if ( punch != null ) {
            punchModifier = punch.getLevel();
        }

        int flameModifier = 0;
        Enchantment flame = this.getEnchantment( EnchantmentType.FLAME );
        if ( flame != null ) {
            flameModifier = flame.getLevel();
        }

        EntityArrow arrow = Entity.create( EntityType.ARROW );
        arrow.setShooter( player );
        arrow.setLocation( new Location( player.getWorld(), player.getX(), player.getY() + player.getEyeHeight(), player.getZ(), ( player.getYaw() > 180 ? 360 : 0 ) - player.getYaw(), -player.getPitch() ) );
        arrow.setVelocity( new Vector(
                (float) ( -Math.sin( player.getYaw() / 180 * Math.PI ) * Math.cos( player.getPitch() / 180 * Math.PI ) ),
                (float) ( -Math.sin( player.getPitch() / 180 * Math.PI ) ),
                (float) ( Math.cos( player.getYaw() / 180 * Math.PI ) * Math.cos( player.getPitch() / 180 * Math.PI ) ) ).multiply( force, force, force ), false );
        arrow.setFlameModifier( flameModifier );
        arrow.setPunchModifier( punchModifier );
        arrow.setPowerModifier( powerModifier );
        arrow.setPickupDelay( 500, TimeUnit.MILLISECONDS );
        arrow.setForce( force );

        ProjectileLaunchEvent event = new ProjectileLaunchEvent( arrow, ProjectileLaunchEvent.Cause.BOW );
        player.getWorld().getServer().getPluginManager().callEvent( event );
        if ( !event.isCancelled() ) {
            EnchantmentInfinity enchantmentInfinity = (EnchantmentInfinity) this.getEnchantment( EnchantmentType.INFINITY );
            if ( enchantmentInfinity == null ) {
                this.updateItem( player, 1 );
                if ( player.getGameMode().equals( GameMode.SURVIVAL ) ) {
                    player.getInventory().removeItem( ItemType.ARROW, 1 );
                }
            } else {
                arrow.setWasInfinityArrow( true );
            }

            arrow.spawn();
            arrow.setBurning( flameModifier > 0 );
            player.playSound( Sound.RANDOM_BOW, 1, 1 );
        }
    }
}
