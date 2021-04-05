package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
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
        this.setWallBlockType( WallType.values()[itemIndHand.getMeta()] );
        this.update();
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getWallBlockType().ordinal() );
    }

    public void setWallBlockType( WallType wallType ) {
        this.setState( "wall_block_type", wallType.name().toLowerCase() );
        this.getWorld().sendBlockUpdate( this );
        this.getChunk().setBlock( this.location, this.layer, this.runtimeId );
    }

    public WallType getWallBlockType() {
        return this.stateExists( "wall_block_type" ) ? WallType.valueOf( this.getStringState( "wall_block_type" ).toUpperCase() ) : WallType.COBBLESTONE;
    }

    public enum WallType {
        COBBLESTONE,
        MOSSY_COBBLESTONE,
        GRANITE,
        DIORITE,
        ANDESITE,
        SANDSTONE,
        BRICK,
        STONE_BRICK,
        MOSSY_STONE_BRICK,
        NETHER_BRICK,
        END_BRICK,
        PRISMARINE,
        RED_SANDSTONE,
        RED_NETHER_BRICK
    }
}
