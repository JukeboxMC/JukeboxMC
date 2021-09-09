package jukeboxmc.block;

import org.jukeboxmc.item.ItemCrimsonFungus;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCrimsonFungus extends Block {

    public BlockCrimsonFungus() {
        super( "minecraft:crimson_fungus" );
    }

    @Override
    public ItemCrimsonFungus toItem() {
        return new ItemCrimsonFungus();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CRIMSON_FUNGUS;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
