package org.jukeboxmc.entity.passiv;

import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.inventory.PlayerInventory;

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
}
