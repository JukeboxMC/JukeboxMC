package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.PlantType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemDoublePlant;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDoublePlant extends Block {

    public BlockDoublePlant( Identifier identifier ) {
        super( identifier );
    }

    public BlockDoublePlant( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block blockAbove = world.getBlock( placePosition.add( 0, 1, 0 ) );
        Block blockDown = world.getBlock( placePosition.subtract( 0, 1, 0 ) );

        if ( blockAbove.getType().equals( BlockType.AIR ) && ( blockDown.getType().equals( BlockType.GRASS ) || blockDown.getType().equals( BlockType.DIRT ) ) ) {
            if ( !this.isUpperBlock() ) {
                BlockDoublePlant blockDoublePlant = Block.create( BlockType.DOUBLE_PLANT );
                blockDoublePlant.setLocation( new Location( world, placePosition.add( 0, 1, 0 ) ) );
                blockDoublePlant.setPlantType( this.getPlantType() );
                blockDoublePlant.setUpperBlock( true );
                world.setBlock( placePosition.add( 0, 1, 0 ), blockDoublePlant );
                world.setBlock( placePosition, this );
            } else {
                BlockDoublePlant blockDoublePlant = Block.create( BlockType.DOUBLE_PLANT );
                blockDoublePlant.setLocation( new Location( world, placePosition ) );
                blockDoublePlant.setPlantType( this.getPlantType() );
                blockDoublePlant.setUpperBlock( false );
                world.setBlock( placePosition, blockDoublePlant );
                world.setBlock( placePosition.add( 0, 1, 0 ), this );
            }
            return true;
        }
        return false;
    }

    @Override
    public void onBlockBreak( Vector breakPosition ) {
        if ( this.isUpperBlock() ) {
            this.location.getWorld().setBlock( this.location.subtract( 0, 1, 0 ), Block.create( BlockType.AIR ) );
        } else {
            this.location.getWorld().setBlock( this.location.add( 0, 1, 0 ), Block.create( BlockType.AIR ) );
        }
        this.location.getWorld().setBlock( this.location, Block.create( BlockType.AIR ) );
    }

    @Override
    public Item toItem() {
        return Item.<ItemDoublePlant>create( ItemType.DOUBLE_PLANT ).setPlantType( this.getPlantType() );
    }

    public BlockDoublePlant setPlantType( PlantType plantType ) {
        return this.setState( "double_plant_type", plantType.name().toLowerCase() );
    }

    public PlantType getPlantType() {
        return this.stateExists( "double_plant_type" ) ? PlantType.valueOf( this.getStringState( "double_plant_type" ) ) : PlantType.SUNFLOWER;
    }

    public BlockDoublePlant setUpperBlock( boolean value ) {
        return this.setState( "upper_block_bit", value ? 1 : 0 );
    }

    public boolean isUpperBlock() {
        return this.stateExists( "upper_block_bit" ) && this.getIntState( "upper_block_bit" ) == 1;
    }
}
