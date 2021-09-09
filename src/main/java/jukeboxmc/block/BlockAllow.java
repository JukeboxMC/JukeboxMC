package jukeboxmc.block;

import org.jukeboxmc.item.ItemAllow;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAllow extends Block {

    public BlockAllow() {
        super( "minecraft:allow" );
    }

    @Override
    public ItemAllow toItem() {
        return new ItemAllow();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ALLOW;
    }

}
