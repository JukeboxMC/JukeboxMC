package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.PlantType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
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
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setPlantType( PlantType.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getPlantType().ordinal() );
    }

    public BlockDoublePlant setPlantType( PlantType plantType ) {
        return this.setState( "double_plant_type", plantType.name().toLowerCase() );
    }

    public PlantType getPlantType() {
        return this.stateExists( "double_plant_type" ) ? PlantType.valueOf( this.getStringState( "double_plant_type" ).toUpperCase() ) : PlantType.SUNFLOWER;
    }

    public void setUpperBlock( boolean value ) {
        this.setState( "upper_block_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isUpperBlock( boolean value ) {
        return this.stateExists( "upper_block_bit" ) && this.getByteState( "upper_block_bit" ) == 1;
    }

}
