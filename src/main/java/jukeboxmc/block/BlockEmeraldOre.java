package jukeboxmc.block;

import org.jukeboxmc.item.ItemEmeraldOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEmeraldOre extends Block {

    public BlockEmeraldOre() {
        super( "minecraft:emerald_ore" );
    }

    @Override
    public ItemEmeraldOre toItem() {
        return new ItemEmeraldOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.EMERALD_ORE;
    }

}
