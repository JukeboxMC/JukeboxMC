package jukeboxmc.block;

import org.jukeboxmc.item.ItemRedstoneOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedstoneOre extends Block {

    public BlockRedstoneOre() {
        super( "minecraft:redstone_ore" );
    }

    @Override
    public ItemRedstoneOre toItem() {
        return new ItemRedstoneOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.REDSTONE_ORE;
    }

}
