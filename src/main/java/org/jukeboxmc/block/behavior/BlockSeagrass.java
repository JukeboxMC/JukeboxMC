package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.SeaGrassType;
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
public class BlockSeagrass extends Block {

    public BlockSeagrass( Identifier identifier ) {
        super( identifier );
    }

    public BlockSeagrass( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block block = world.getBlock( placePosition );
        if ( block.getType().equals( BlockType.WATER ) ) {
            world.setBlock( placePosition, this, 0 );
            world.setBlock( placePosition, block, 1 );
            return true;
        }
        return false;
    }

    @Override
    public void onBlockBreak( Vector breakPosition ) {
        World world = this.getWorld();
        Block block = world.getBlock( breakPosition, 1 );
        if( block instanceof BlockWater ) {
            world.setBlock( breakPosition, block, 0 );
            world.setBlock( breakPosition, Block.create( BlockType.AIR ), 1 );
            return;
        }
        super.onBlockBreak( breakPosition );
    }


    public BlockSeagrass setSeaGrassType( SeaGrassType seaGrassType ) {
        this.setState( "sea_grass_type",seaGrassType.name().toLowerCase() );
        return this;
    }

    public SeaGrassType getSeaGrassType() {
        return this.stateExists( "sea_grass_type" ) ? SeaGrassType.valueOf( this.getStringState( "sea_grass_type" ) ) : SeaGrassType.DEFAULT;
    }
}
