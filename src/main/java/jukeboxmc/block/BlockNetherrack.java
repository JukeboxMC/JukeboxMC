package jukeboxmc.block;

import org.jukeboxmc.item.ItemNetherrack;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockNetherrack extends Block {

    public BlockNetherrack() {
        super( "minecraft:netherrack" );
    }

    @Override
    public ItemNetherrack toItem() {
        return new ItemNetherrack();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.NETHERRACK;
    }

}
