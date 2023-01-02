package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSugarCane extends Block {

    public BlockSugarCane( Identifier identifier ) {
        super( identifier );
    }

    public BlockSugarCane( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block block = world.getBlock( blockPosition );

        if ( world.getBlock( placePosition.subtract( 0, 1, 0 ) ).getType().equals( BlockType.SUGAR_CANE ) ) {
            world.setBlock( blockPosition.add( 0, 1, 0 ), this );
            return true;
        } else if ( block.getType().equals( BlockType.GRASS ) || block.getType().equals( BlockType.DIRT ) || block.getType().equals( BlockType.SAND ) ) {
            Block blockNorth = block.getSide( BlockFace.NORTH );
            Block blockEast = block.getSide( BlockFace.EAST );
            Block blockSouth = block.getSide( BlockFace.SOUTH );
            Block blockWest = block.getSide( BlockFace.WEST );
            if ( ( blockNorth.getType().equals( BlockType.WATER ) ) || ( blockEast.getType().equals( BlockType.WATER ) ) || ( blockSouth.getType().equals( BlockType.WATER ) ) || ( blockWest.getType().equals( BlockType.WATER ) ) ) {
                world.setBlock( placePosition, this );
                return true;
            }
        }
        return false;
    }

    public void setAge( int value ) {
        this.setState( "age", value );
    }

    public int getAge() {
        return this.stateExists( "age" ) ? this.getIntState( "age" ) : 0;
    }
}
