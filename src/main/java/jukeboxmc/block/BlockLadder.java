package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemLadder;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLadder extends BlockWaterlogable {

    public BlockLadder() {
        super( "minecraft:ladder" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );

        if ( !targetBlock.isTransparent() && blockFace != BlockFace.UP && blockFace != BlockFace.DOWN ) {
            this.setBlockFace( blockFace );
            return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        }

        return false;
    }

    @Override
    public ItemLadder toItem() {
        return new ItemLadder();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LADDER;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
