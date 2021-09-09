package jukeboxmc.block;

import org.jukeboxmc.item.ItemMelonStem;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMelonStem extends Block {

    public BlockMelonStem() {
        super( "minecraft:melon_stem" );
    }

    @Override
    public ItemMelonStem toItem() {
        return new ItemMelonStem();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MELON_STEM;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public void setGrowth( int value ) { //0-7
        this.setState( "growth", value );
    }

    public int getGrowth() {
        return this.stateExists( "growth" ) ? this.getIntState( "growth" ) : 0;
    }
}
