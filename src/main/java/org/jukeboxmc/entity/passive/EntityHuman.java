package org.jukeboxmc.entity.passive;

import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.metadata.EntityFlag;
import org.jukeboxmc.entity.metadata.MetadataFlag;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.inventory.PlayerInventory;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityHuman extends Entity implements InventoryHolder {

    private PlayerInventory playerInventory;

    public EntityHuman() {
        this.playerInventory = new PlayerInventory( this );
    }

    @Override
    public String getName() {
        return this.getNameTag();
    }

    @Override
    public String getEntityType() {
        return "minecraft:player";
    }

    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        return 1.8f;
    }

    @Override
    public float getEyeHeight() {
        return 1.62f;
    }

    public PlayerInventory getInventory() {
        return this.playerInventory;
    }

    public boolean isSneaking() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.SNEAKING );
    }

    public void setSneaking( boolean value ) {
        if ( value != this.isSneaking() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.SNEAKING, value ) );
        }
    }

    public boolean isSprinting() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.SPRINTING );
    }

    public void setSprinting( boolean value ) {
        if ( value != this.isSprinting() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.SPRINTING, value ) );
        }
    }

    public boolean hasAction() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.ACTION );
    }

    public void setAction( boolean value ) {
        if ( value != this.hasAction() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.ACTION, value ) );
        }
    }

    public boolean isSwimming() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.SWIMMING );
    }

    public void setSwimming( boolean value ) {
        if ( value != this.isSwimming() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.SWIMMING, value ) );
        }
    }

    public boolean isGliding() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.GLIDING );
    }

    public void setGliding( boolean value ) {
        if ( value != this.isGliding() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.GLIDING, value ) );
        }
    }
}
