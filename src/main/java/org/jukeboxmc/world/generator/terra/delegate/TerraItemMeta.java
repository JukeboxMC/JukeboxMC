package org.jukeboxmc.world.generator.terra.delegate;

import com.dfsek.terra.api.inventory.item.Enchantment;
import com.dfsek.terra.api.inventory.item.ItemMeta;
import org.jukeboxmc.item.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public record TerraItemMeta(Item item) implements ItemMeta {

    @Override
    public void addEnchantment( Enchantment enchantment, int i ) {

    }

    @Override
    public Map<Enchantment, Integer> getEnchantments() {
        return new HashMap<>();
    }

    @Override
    public Item getHandle() {
        return this.item;
    }
}
