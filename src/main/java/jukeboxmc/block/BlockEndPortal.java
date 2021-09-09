package jukeboxmc.block;

import org.jukeboxmc.item.ItemEndPortal;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEndPortal extends Block {

    public BlockEndPortal() {
        super( "minecraft:end_portal" );
    }

    @Override
    public ItemEndPortal toItem() {
        return new ItemEndPortal();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.END_PORTAL;
    }

}
