package org.jukeboxmc.entity.passiv;

import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.metadata.EntityFlag;
import org.jukeboxmc.entity.metadata.Metadata;
import org.jukeboxmc.entity.metadata.MetadataFlag;
import org.jukeboxmc.inventory.PlayerInventory;
import org.jukeboxmc.network.packet.SetEntityDataPacket;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityHuman extends Entity {

    private PlayerInventory playerInventory;

    public EntityHuman() {
        this.playerInventory = new PlayerInventory( this );
    }

    @Override
    public String getEntityType() {
        return "minecraft:player";
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
}
