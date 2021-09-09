package jukeboxmc.block;

import org.jukeboxmc.item.ItemSlime;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSlime extends Block {

    public BlockSlime() {
        super( "minecraft:slime" );
    }

    @Override
    public ItemSlime toItem() {
        return new ItemSlime();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SLIME;
    }

}
