package org.jukeboxmc.item;

import org.jukeboxmc.entity.projectile.EntityArrow;
import org.jukeboxmc.event.entity.ProjectileLaunchEvent;
import org.jukeboxmc.item.enchantment.*;
import org.jukeboxmc.item.type.Burnable;
import org.jukeboxmc.item.type.Durability;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtType;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.Sound;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrossbow extends Item implements Durability, Burnable {

    private boolean canShoot;

    public ItemCrossbow() {
        super( "minecraft:crossbow" );
    }

    @Override
    public boolean useInAir( Player player, Vector clickVector ) {
        if ( player.hasAction() && this.getChargedItem() == null ) {
            this.setChargedItem( new ItemArrow() );
            player.getInventory().setItemInHand( this );
        } else if ( this.getChargedItem() != null && !this.canShoot ) {
            player.getInventory().sendItemInHand();
            this.canShoot = true;
            return false;
        } else if ( this.canShoot && this.getChargedItem() != null ) {
            this.shoot( player );
            ItemCrossbow newItemCrossbow = new ItemCrossbow();
            newItemCrossbow.setChargedItem( null );
            newItemCrossbow.setAmount( this.amount );
            newItemCrossbow.setMeta( this.meta );
            newItemCrossbow.setDurability( this.durability );
            player.getInventory().setItemInHand( newItemCrossbow );
            return false;
        }
        return !player.hasAction();
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }

    @Override
    public int getMaxDurability() {
        return 464;
    }

    public void shoot( Player player ) {
        float forece = 2;

        if ( !player.getInventory().contains( new ItemArrow() ) && ( !player.getGameMode().equals( GameMode.CREATIVE ) ) ) {
            return;
        }

        player.setAction( false );

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
        arrow.setGravity( 0.02f );

        ProjectileLaunchEvent event = new ProjectileLaunchEvent( arrow, ProjectileLaunchEvent.Cause.CROSSBOW );
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

    public void setChargedItem( Item chargedItem ) {
        if ( this.nbt == null ) {
            this.nbt = NbtMap.EMPTY;
        }
        if ( chargedItem != null ) {
            this.nbt = this.nbt.toBuilder().putCompound( "chargedItem", chargedItem.toNetwork( true ).build() ).build();
        }
    }

    public Item getChargedItem() {
        if ( this.nbt != null && this.nbt.containsKey( "chargedItem", NbtType.COMPOUND ) ) {
            NbtMap chargedItem = this.nbt.getCompound( "chargedItem" );
            String name = chargedItem.getString( "Name", "minecraft:arrow" );
            Item item = ItemType.get( name );
            item.setAmount( chargedItem.getByte( "Count", (byte) 1 ) );
            item.setMeta( chargedItem.getByte( "Damage", (byte) 0 ) );
            item.setNBT( chargedItem );
            return item;
        }
        return null;
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 200 );
    }
}
