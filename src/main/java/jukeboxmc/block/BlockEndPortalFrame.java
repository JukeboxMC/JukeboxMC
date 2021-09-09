package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemEndPortalFrame;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEndPortalFrame extends BlockWaterlogable {

    public BlockEndPortalFrame() {
        super( "minecraft:end_portal_frame" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setDirection( player.getDirection().opposite() );
        return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
    }

    @Override
    public ItemEndPortalFrame toItem() {
        return new ItemEndPortalFrame();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.END_PORTAL_FRAME;
    }

    public void setEndPortalEye( boolean value ) {
        this.setState( "end_portal_eye_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isEndPortalEye() {
        return this.stateExists( "end_portal_eye_bit" ) && this.getByteState( "end_portal_eye_bit" ) == 1;
    }

    public void setDirection( Direction direction ) {
        switch ( direction ) {
            case SOUTH:
                this.setState( "direction", 0 );
                break;
            case WEST:
                this.setState( "direction", 1 );
                break;
            case NORTH:
                this.setState( "direction", 2 );
                break;
            case EAST:
                this.setState( "direction", 3 );
                break;
        }
    }

    public Direction getDirection() {
        int value = this.stateExists( "direction" ) ? this.getIntState( "direction" ) : 0;
        switch ( value ) {
            case 0:
                return Direction.SOUTH;
            case 1:
                return Direction.WEST;
            case 2:
                return Direction.NORTH;
            default:
                return Direction.EAST;
        }
    }

}
