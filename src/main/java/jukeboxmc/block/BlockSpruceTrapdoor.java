package jukeboxmc.block;

import org.jukeboxmc.item.ItemSpruceTrapdoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSpruceTrapdoor extends BlockTrapdoor {

    public BlockSpruceTrapdoor() {
        super( "minecraft:spruce_trapdoor" );
    }

    @Override
    public ItemSpruceTrapdoor toItem() {
        return new ItemSpruceTrapdoor();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SPRUCE_TRAPDOOR;
    }

}
