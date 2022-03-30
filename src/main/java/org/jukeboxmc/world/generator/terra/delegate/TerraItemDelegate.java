package org.jukeboxmc.world.generator.terra.delegate;

import com.dfsek.terra.api.inventory.Item;
import com.dfsek.terra.api.inventory.ItemStack;
import org.jukeboxmc.item.type.Durability;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public record TerraItemDelegate(org.jukeboxmc.item.Item item) implements Item {

    @Override
    public ItemStack newItemStack( int i) {
        final var tmp = this.item.clone();
        tmp.setAmount(i);
        return new TerraItemStack(tmp);
    }

    @Override
    public double getMaxDurability() {
        if ( this.item instanceof Durability ) {
            return ( (Durability) this.item ).getMaxDurability();
        }
        return 0;
    }

    @Override
    public org.jukeboxmc.item.Item getHandle() {
        return item;
    }
}
