package org.jukeboxmc.item;

import org.jukeboxmc.Server;
import org.jukeboxmc.entity.projectile.EntityArrow;
import org.jukeboxmc.event.entity.ProjectileLaunchEvent;
import org.jukeboxmc.item.enchantment.*;
import org.jukeboxmc.item.type.Burnable;
import org.jukeboxmc.item.type.Durability;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.Sound;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBow extends Item implements Durability, Burnable {

    public ItemBow() {
        super( "minecraft:bow" );
    }

    @Override
    public boolean useInAir( Player player, Vector clickVector ) {
        player.setAction( true );
        return super.useInAir( player, clickVector );
    }

    @Override
    public int getMaxDurability() {
        return 384;
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }

    public void shoot( Player player ) {
        if ( player.getActionStart() == -1 ) {
            return;
        }

        float tick = (float) ( Server.getInstance().getCurrentTick() - player.getActionStart() ) / 20;
        float forece = Math.min( ( tick * tick + tick * 2 ) / 3, 1 ) * 2;

        player.setAction( false );

        if ( !player.getInventory().contains( new ItemArrow() ) && ( !player.getGameMode().equals( GameMode.CREATIVE ) ) ) {
            return;
        }

        int powerModifier = 0;
        EnchantmentPower power = (EnchantmentPower) this.getEnchantment( EnchantmentType.POWER );
        if ( power != null ) {
            powerModifier = power.getLevel();
        }

        int punchModifier = 0;
        EnchantmentPunch punch = (EnchantmentPunch) this.getEnchantment( EnchantmentType.PUNCH );
        if ( punch != null ) {
            punchModifier = punch.getLevel();
        }

        int flameModifier = 0;
        EnchantmentFlame flame = (EnchantmentFlame) this.getEnchantment( EnchantmentType.FLAME );
        if ( flame != null ) {
            flameModifier = flame.getLevel();
        }

        EntityArrow arrow = new EntityArrow();
        arrow.setShooter( player );
        arrow.setLocation( new Location( player.getWorld(), player.getX(), player.getY() + player.getEyeHeight(), player.getZ(), ( player.getYaw() > 180 ? 360 : 0 ) - player.getYaw(), -player.getPitch() ) );
        arrow.setVelocity( new Vector(
                (float) ( -Math.sin( player.getYaw() / 180 * Math.PI ) * Math.cos( player.getPitch() / 180 * Math.PI ) ),
                (float) ( -Math.sin( player.getPitch() / 180 * Math.PI ) ),
                (float) ( Math.cos( player.getYaw() / 180 * Math.PI ) * Math.cos( player.getPitch() / 180 * Math.PI ) ) ).multiply( forece, forece, forece ), false );
        arrow.setFlameModifier( flameModifier );
        arrow.setPunchModifier( punchModifier );
        arrow.setPowerModifier( powerModifier );
        arrow.setPickupDelay( 500, TimeUnit.MILLISECONDS );
        arrow.setForce( forece );

        ProjectileLaunchEvent event = new ProjectileLaunchEvent( arrow, ProjectileLaunchEvent.Cause.BOW );
        player.getWorld().getServer().getPluginManager().callEvent( event );
        if ( !event.isCancelled() ) {
            EnchantmentInfinity enchantmentInfinity = (EnchantmentInfinity) this.getEnchantment( EnchantmentType.INFINITY );
            if ( enchantmentInfinity == null ) {
                this.updateItem( player, 1 );
                if ( player.getGameMode().equals( GameMode.SURVIVAL ) ) {
                    player.getInventory().removeItem( new ItemArrow() );
                }
            } else {
                arrow.setWasInfinityArrow( true );
            }

            arrow.spawn();
            arrow.setBurning( flameModifier > 0 );
            player.playSound( Sound.RANDOM_BOW, 1, 1 );
        }
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 200 );
    }
}
