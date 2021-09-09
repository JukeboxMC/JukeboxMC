package jukeboxmc.block;

import org.jukeboxmc.item.ItemMycelium;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMycelium extends Block {

    public BlockMycelium() {
        super( "minecraft:mycelium" );
    }

    @Override
    public ItemMycelium toItem() {
        return new ItemMycelium();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MYCELIUM;
    }

}
