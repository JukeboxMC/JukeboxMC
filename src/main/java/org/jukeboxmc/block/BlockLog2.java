package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.LogType2;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemLog2;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Axis;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLog2 extends Block {

    public BlockLog2() {
        super( "minecraft:log2" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        if ( blockFace == BlockFace.UP || blockFace == BlockFace.DOWN ) {
            this.setAxis( Axis.Y );
        } else if ( blockFace == BlockFace.NORTH || blockFace == BlockFace.SOUTH ) {
            this.setAxis( Axis.Z );
        } else {
            this.setAxis( Axis.X );
        }

        world.setBlock( placePosition, this, 0 );
        return true;
    }

    @Override
    public ItemLog2 toItem() {
        BlockLog2 blockLog2 = new BlockLog2();
        blockLog2.setLogType( this.getLogType() );
        blockLog2.setAxis( Axis.Y );
        return new ItemLog2( blockLog2.getRuntimeId() );
    }

    @Override
    public BlockType getType() {
        return switch ( this.getLogType() ) {
            case ACACIA -> BlockType.ACACIA_LOG;
            case DARK_OAK -> BlockType.DARK_OAK_LOG;
        };
    }

    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
    }

    public void setAxis( Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ) ) : Axis.Y;
    }

    public BlockLog2 setLogType( LogType2 logType ) {
        return this.setState( "new_log_type", logType.name().toLowerCase() );
    }

    public LogType2 getLogType() {
        return this.stateExists( "new_log_type" ) ? LogType2.valueOf( this.getStringState( "new_log_type" ) ) : LogType2.ACACIA;
    }


}
