package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemWarpedHyphae;
import org.jukeboxmc.math.Axis;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

public class BlockWarpedHyphae extends Block {

    public BlockWarpedHyphae() {
        super("minecraft:warped_hyphae");
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        if ( blockFace == BlockFace.UP || blockFace == BlockFace.DOWN ) {
            this.setAxis( Axis.Y );
        } else if ( blockFace == BlockFace.NORTH || blockFace == BlockFace.SOUTH ) {
            this.setAxis( Axis.Z );
        } else {
            this.setAxis( Axis.X );
        }

        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemWarpedHyphae toItem() {
        return new ItemWarpedHyphae();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WARPED_HYPHAE;
    }

    public void setAxis( Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ) ) : Axis.Y;
    }
}