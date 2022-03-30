package org.jukeboxmc.world.generator.terra.handles;

import com.dfsek.terra.api.handle.ItemHandle;
import com.dfsek.terra.api.inventory.Item;
import com.dfsek.terra.api.inventory.item.Enchantment;
import org.jukeboxmc.item.enchantment.EnchantmentType;
import org.jukeboxmc.world.generator.terra.delegate.TerraAdapter;
import org.jukeboxmc.world.generator.terra.delegate.TerraEnchantmentDelegate;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


public class TerraItemHandle implements ItemHandle {

    @Override
    public Item createItem( String s ) {
        String[] itemArgs = s.split( ":" );
        org.jukeboxmc.item.Item item;
        if ( itemArgs.length == 1 ) {
            item = new org.jukeboxmc.item.Item( "minecraft:" + s );
        } else if ( itemArgs.length == 2 ) {
            String first = itemArgs[0];
            if ( first.equals( "minecraft" ) ) {
                item = new org.jukeboxmc.item.Item( s );
            } else {
                try {
                    int meta = Integer.parseInt( itemArgs[1] );
                    item = new org.jukeboxmc.item.Item( "minecraft:" + first );
                    item.setMeta( meta );
                } catch ( NumberFormatException e ) {
                    item = new org.jukeboxmc.item.Item( s );
                }
            }
        } else if ( itemArgs.length == 3 ) {
            try {
                int meta = Integer.parseInt( itemArgs[2] );
                item = new org.jukeboxmc.item.Item( itemArgs[0] + ":" + itemArgs[1] );
                item.setMeta( meta );
            } catch ( NumberFormatException e ) {
                item = new org.jukeboxmc.item.Item( s );
            }
        } else {
            item = new org.jukeboxmc.item.Item( s );
        }
        return TerraAdapter.adapt( item );
    }

    @Override
    public Enchantment getEnchantment( String s ) {
        if ( s.startsWith( "minecraft:" ) ) s = s.substring( 10 );
        return new TerraEnchantmentDelegate( EnchantmentType.valueOf( s.toUpperCase() ).getEnchantment() );
    }

    @Override
    public Set<Enchantment> getEnchantments() {
        return Arrays.stream( EnchantmentType.values() )
                .map( EnchantmentType::getEnchantment )
                .map( TerraEnchantmentDelegate::new )
                .collect( Collectors.toSet() );
    }
}
