package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.PlantType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemDoublePlant;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDoublePlant extends Block {

    public BlockDoublePlant() {
        super( "minecraft:double_plant" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block blockAbove = world.getBlock( placePosition.add( 0, 1, 0 ) );
        Block blockDown = world.getBlock( placePosition.subtract( 0, 1, 0 ) );

        if ( blockAbove instanceof BlockAir && ( blockDown instanceof BlockGrass || blockDown instanceof BlockDirt ) ) {
            if ( !this.isUpperBlock() ) {
                BlockDoublePlant blockDoublePlant = new BlockDoublePlant();
                blockDoublePlant.setLocation( new Location( world, placePosition.add( 0, 1, 0 ) ) );
                blockDoublePlant.setPlantType( this.getPlantType() );
                blockDoublePlant.setUpperBlock( true );
                world.setBlock( placePosition.add( 0, 1, 0 ), blockDoublePlant );
                world.setBlock( placePosition, this );
            } else {
                BlockDoublePlant blockDoublePlant = new BlockDoublePlant();
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
    public boolean onBlockBreak( Vector breakPosition ) {
        if ( this.isUpperBlock() ) {
            this.world.setBlock( this.location.subtract( 0, 1, 0 ), new BlockAir() );
        } else {
            this.world.setBlock( this.location.add( 0, 1, 0 ), new BlockAir() );
        }
        this.world.setBlock( this.location, new BlockAir() );
        return true;
    }

    @Override
    public ItemDoublePlant toItem() {
        return new ItemDoublePlant( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DOUBLE_PLANT;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean canBeReplaced( Block block ) {
        this.onBlockBreak( block.getLocation() );
        return true;
    }

    public BlockDoublePlant setPlantType( PlantType plantType ) {
        return this.setState( "double_plant_type", plantType.name().toLowerCase() );
    }

    public PlantType getPlantType() {
        return this.stateExists( "double_plant_type" ) ? PlantType.valueOf( this.getStringState( "double_plant_type" ) ) : PlantType.SUNFLOWER;
    }

    public BlockDoublePlant setUpperBlock( boolean value ) {
       return this.setState( "upper_block_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isUpperBlock() {
        return this.stateExists( "upper_block_bit" ) && this.getByteState( "upper_block_bit" ) == 1;
    }

}
