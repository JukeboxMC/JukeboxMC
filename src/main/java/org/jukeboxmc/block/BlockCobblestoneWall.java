package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.WallType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemCobblestoneWall;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCobblestoneWall extends BlockWall {

    public BlockCobblestoneWall() {
        super( "minecraft:cobblestone_wall" );
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.updateWall();
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemCobblestoneWall toItem() {
        return new ItemCobblestoneWall().setWallType( this.getWallBlockType() );
    }

    public BlockCobblestoneWall setWallBlockType( WallType wallType ) {
        this.setState( "wall_block_type", wallType.name().toLowerCase() );
        if ( this.getWorld() != null ) {
            this.getWorld().sendBlockUpdate( this );
            this.getChunk().setBlock( this.location, this.layer, this.runtimeId );
        }
        return this;
    }

    public WallType getWallBlockType() {
        return this.stateExists( "wall_block_type" ) ? WallType.valueOf( this.getStringState( "wall_block_type" ).toUpperCase() ) : WallType.COBBLESTONE;
    }

}
