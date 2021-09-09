package jukeboxmc.item;

import org.jukeboxmc.block.BlockGlowLichen;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGlowLichen extends Item{

    public ItemGlowLichen() {
        super( "minecraft:glow_lichen" );
    }

    @Override
    public BlockGlowLichen getBlock() {
        return new BlockGlowLichen();
    }
}
