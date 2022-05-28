package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemSugarCane;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSugarCane extends Block {

    public BlockSugarCane() {
        super( "minecraft:reeds" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block block = world.getBlock( blockPosition );

        if ( world.getBlock( placePosition.subtract( 0, 1, 0 ) ).getType().equals( BlockType.SUGARCANE ) ) {
            world.setBlock( blockPosition.add( 0, 1, 0 ), this );
            return true;
        } else if ( block.getType().equals( BlockType.GRASS ) || block.getType().equals( BlockType.DIRT ) || block.getType().equals( BlockType.SAND ) ) {
            Block blockNorth = block.getSide( BlockFace.NORTH );
            Block blockEast = block.getSide( BlockFace.EAST );
            Block blockSouth = block.getSide( BlockFace.SOUTH );
            Block blockWest = block.getSide( BlockFace.WEST );
            if ( ( blockNorth instanceof BlockWater ) || ( blockEast instanceof BlockWater ) || ( blockSouth instanceof BlockWater ) || ( blockWest instanceof BlockWater ) ) {
                world.setBlock( placePosition, this );
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemSugarCane toItem() {
        return new ItemSugarCane();
    }

    @Override
    public BlockType getType() {
        return BlockType.SUGARCANE;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public void setAge( int value ) {
        this.setState( "age", value );
    }

    public int getAge() {
        return this.stateExists( "age" ) ? this.getIntState( "age" ) : 0;
    }
}
