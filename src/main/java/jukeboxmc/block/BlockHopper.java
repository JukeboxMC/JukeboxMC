package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemHopper;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockHopper extends BlockWaterlogable {

    public BlockHopper() {
        super( "minecraft:hopper" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setBlockFace( blockFace == BlockFace.UP ? BlockFace.DOWN : blockFace.opposite() );
        this.setToggle( false );
        return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
    }

    @Override
    public ItemHopper toItem() {
        return new ItemHopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.HOPPER;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public void setToggle( boolean value ) {
        this.setState( "toggle_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isToggle() {
        return this.stateExists( "toggle_bit" ) && this.getByteState( "toggle_bit" ) == 1;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
