package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.enchantment.Enchantment;
import org.jukeboxmc.item.enchantment.EnchantmentType;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.util.Utils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCopperOre extends Block {

    public BlockCopperOre( Identifier identifier ) {
        super( identifier );
    }

    public BlockCopperOre( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public List<Item> getDrops( Item item ) {
        if ( this.isCorrectToolType( item ) && this.isCorrectTierType( item ) ) {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            int amount = Utils.randomRange( random, 2, 5 );
            Enchantment enchantment = item.getEnchantment( EnchantmentType.FORTUNE );
            if ( enchantment != null ) {
                amount += Utils.randomRange( random, 0, enchantment.getLevel() + 1 );
            }
            return Collections.singletonList( Item.create( ItemType.COPPER_INGOT ).setAmount( amount ) );
        }
        return Collections.emptyList();
    }
}
