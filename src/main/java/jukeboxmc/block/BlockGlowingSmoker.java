package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.ItemSmoker;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGlowingSmoker extends Block {

    public BlockGlowingSmoker() {
        super( "minecraft:lit_smoker" );
    }

    @Override
    public ItemSmoker toItem() {
        return new ItemSmoker();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LIT_SMOKER;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
