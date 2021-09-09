package jukeboxmc.block;

import org.jukeboxmc.item.ItemLapisOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLapisOre extends Block {

    public BlockLapisOre() {
        super( "minecraft:lapis_ore" );
    }

    @Override
    public ItemLapisOre toItem() {
        return new ItemLapisOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LAPIS_ORE;
    }

}
