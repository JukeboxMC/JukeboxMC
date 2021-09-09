package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.blockentity.BlockEntitySign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class BlockWallSign extends Block {

    public BlockWallSign( String identifier ) {
        super( identifier );
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntitySign getBlockEntity() {
        return (BlockEntitySign) this.world.getBlockEntity( this.location, this.location.getDimension() );
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
