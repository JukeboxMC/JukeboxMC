package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemChemistryTable;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockChemistryTable extends Block {

    public BlockChemistryTable() {
        super( "minecraft:chemistry_table" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setChemistryTableType( ChemistryTableType.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemChemistryTable toItem() {
        return new ItemChemistryTable();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CHEMISTRY_TABLE;
    }

    public void setChemistryTableType( ChemistryTableType chemistryTableType ) {
        this.setState( "chemistry_table_type", chemistryTableType.name().toLowerCase() );
    }

    public ChemistryTableType getChemistryTableType() {
        return this.stateExists( "chemistry_table_type" ) ? ChemistryTableType.valueOf( this.getStringState( "chemistry_table_type" ) ) : ChemistryTableType.COMPUND_CREATOR;
    }

    public void setDirection( Direction direction ) {
        switch ( direction ) {
            case SOUTH:
                this.setState( "direction", 0 );
                break;
            case WEST:
                this.setState( "direction", 1 );
                break;
            case NORTH:
                this.setState( "direction", 2 );
                break;
            case EAST:
                this.setState( "direction", 3 );
                break;
        }
    }

    public Direction getDirection() {
        int value = this.stateExists( "direction" ) ? this.getIntState( "direction" ) : 0;
        switch ( value ) {
            case 0:
                return Direction.SOUTH;
            case 1:
                return Direction.WEST;
            case 2:
                return Direction.NORTH;
            default:
                return Direction.EAST;
        }
    }

    public enum ChemistryTableType {
        COMPUND_CREATOR,
        MATERIAL_REDUCER,
        ELEMENT_CONSTRUCTOR,
        LAB_TABLE
    }
}
