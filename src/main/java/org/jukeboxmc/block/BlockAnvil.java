package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAnvil extends Block {

    public BlockAnvil() {
        super( "minecraft:anvil" );
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        switch ( itemIndHand.getMeta() ) {
            case 4:
                this.setDamage( Damage.SLIGHTLY_DAMAGED );
                break;
            case 8:
                this.setDamage( Damage.VERY_DAMAGED );
                break;
            default:
                this.setDamage( Damage.UNDAMAGED );
                break;
        }
        world.setBlock( placePosition, this );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getDamage().ordinal() );
    }

    public void setDamage( Damage damage ) {
        this.setState( "damage", damage.name().toLowerCase() );
    }

    public Damage getDamage() {
        return this.stateExists( "damage" ) ? Damage.valueOf( this.getStringState( "damage" ).toUpperCase() ) : Damage.UNDAMAGED;
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

    public enum Damage {
        UNDAMAGED,
        SLIGHTLY_DAMAGED,
        VERY_DAMAGED,
        BROKEN
    }
}
