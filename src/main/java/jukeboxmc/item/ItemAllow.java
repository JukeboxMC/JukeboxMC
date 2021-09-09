package jukeboxmc.item;

import org.jukeboxmc.block.BlockAllow;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAllow extends Item {

    public ItemAllow() {
        super( "minecraft:allow" );
    }

    @Override
    public BlockAllow getBlock() {
        return new BlockAllow();
    }
}
