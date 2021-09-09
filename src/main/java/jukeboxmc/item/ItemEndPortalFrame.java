package jukeboxmc.item;

import org.jukeboxmc.block.BlockEndPortalFrame;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemEndPortalFrame extends Item {

    public ItemEndPortalFrame() {
        super ( "minecraft:end_portal_frame" );
    }

    @Override
    public BlockEndPortalFrame getBlock() {
        return new BlockEndPortalFrame();
    }
}
