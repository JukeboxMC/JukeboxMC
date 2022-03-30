package org.jukeboxmc.world.generator.terra.delegate;

import com.dfsek.terra.api.inventory.ItemStack;
import com.dfsek.terra.api.inventory.item.Damageable;
import com.dfsek.terra.api.inventory.item.ItemMeta;
import org.jukeboxmc.item.Item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public record TerraItemStack(Item item) implements ItemStack, Damageable {

    @Override
    public int getAmount() {
        return this.item.getAmount();
    }

    @Override
    public void setAmount( int amount ) {
        item.setAmount( amount );
    }

    @Override
    public com.dfsek.terra.api.inventory.Item getType() {
        return new TerraItemDelegate( this.item );
    }

    @Override
    public ItemMeta getItemMeta() {
        return new TerraItemMeta( this.item );
    }

    @Override
    public void setItemMeta( ItemMeta itemMeta ) {
        //TODO
    }

    @Override
    public int getDamage() {
        return this.item.getMeta();
    }

    @Override
    public void setDamage( int damage ) {
        this.item.setMeta( damage );
    }

    @Override
    public boolean hasDamage() {
        return this.item.getMeta() > 0;
    }

    @Override
    public Item getHandle() {
        return this.item;
    }
}
