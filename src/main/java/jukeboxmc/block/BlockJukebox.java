package jukeboxmc.block;

import org.jukeboxmc.item.ItemJukebox;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockJukebox extends Block {

    public BlockJukebox() {
        super( "minecraft:jukebox" );
    }

    @Override
    public ItemJukebox toItem() {
        return new ItemJukebox();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.JUKEBOX;
    }

}
