package jukeboxmc.item;

import org.jukeboxmc.block.BlockGlowingRedstoneOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGlowingRedstoneOre extends Item {

    public ItemGlowingRedstoneOre() {
        super ( "minecraft:lit_redstone_ore" );
    }

    @Override
    public BlockGlowingRedstoneOre getBlock() {
        return new BlockGlowingRedstoneOre();
    }
}
