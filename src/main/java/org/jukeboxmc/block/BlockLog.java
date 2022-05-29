package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.LogType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemLog;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Axis;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLog extends Block {

    public BlockLog() {
        super( "minecraft:log" );
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

        world.setBlock( placePosition, this, 0);
        return true;
    }

    @Override
    public ItemLog toItem() {
        BlockLog blockLog = new BlockLog();
        blockLog.setLogType( this.getLogType() );
        blockLog.setAxis( Axis.Y );
        return new ItemLog( blockLog.getRuntimeId() );
    }

    @Override
    public BlockType getType() {
        return switch ( this.getLogType() ) {
            case OAK -> BlockType.OAK_LOG;
            case SPRUCE -> BlockType.SPRUCE_LOG;
            case BIRCH -> BlockType.BIRCH_LOG;
            case JUNGLE -> BlockType.JUNGLE_LOG;
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

    public BlockLog setLogType( LogType logType ) {
        return this.setState( "old_log_type", logType.name().toLowerCase() );
    }

    public LogType getLogType() {
        return this.stateExists( "old_log_type" ) ? LogType.valueOf( this.getStringState( "old_log_type" ) ) : LogType.OAK;
    }

    public void setAxis( Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ) ) : Axis.Y;
    }
}
