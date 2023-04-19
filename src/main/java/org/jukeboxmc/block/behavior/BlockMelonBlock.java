package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.enchantment.Enchantment;
import org.jukeboxmc.item.enchantment.EnchantmentType;
import org.jukeboxmc.util.Identifier;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMelonBlock extends Block {

    public BlockMelonBlock( Identifier identifier ) {
        super( identifier );
    }

    public BlockMelonBlock( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public List<Item> getDrops( Item item ) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int amount = 3 + random.nextInt( 5 );
        Enchantment enchantment = item.getEnchantment( EnchantmentType.FORTUNE );
        if ( enchantment != null ) {
            amount += random.nextInt( enchantment.getLevel() + 1 );
        }
        return Collections.singletonList( Item.create( ItemType.MELON_SLICE ).setAmount( Math.min( 9, amount ) ) );
    }
}
