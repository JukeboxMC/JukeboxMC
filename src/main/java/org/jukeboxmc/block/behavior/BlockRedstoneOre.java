package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
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
public class BlockRedstoneOre extends Block {

    public BlockRedstoneOre( Identifier identifier ) {
        super( identifier );
    }

    public BlockRedstoneOre( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public List<Item> getDrops( Item item ) {
        if ( this.isCorrectToolType( item ) && this.isCorrectTierType( item ) ) {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            int amount = 4 + random.nextInt( 2 );
            Enchantment enchantment = item.getEnchantment( EnchantmentType.FORTUNE );
            if ( enchantment != null ) {
                amount += random.nextInt( enchantment.getLevel() + 1 );
            }
            return Collections.singletonList( Item.create( ItemType.LAPIS_LAZULI ).setAmount( amount ) );
        }
        return Collections.emptyList();
    }
}
