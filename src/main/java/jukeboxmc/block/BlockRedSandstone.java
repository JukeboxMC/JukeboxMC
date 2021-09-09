package jukeboxmc.block;

import org.jukeboxmc.item.ItemRedSandstone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedSandstone extends Block {

    public BlockRedSandstone() {
        super( "minecraft:red_sandstone" );
    }

    @Override
    public ItemRedSandstone toItem() {
        return new ItemRedSandstone();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.RED_SANDSTONE;
    }

}
