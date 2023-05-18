package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.CoralColor;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.RotationDirection;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemCoralFanDead;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCoralFanDead extends Block implements Waterlogable {

    public BlockCoralFanDead( Identifier identifier ) {
        super( identifier );
    }

    public BlockCoralFanDead( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock(Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace) {
        if (world.getBlock(placePosition) instanceof BlockWater blockWater && blockWater.getLiquidDepth() == 0) {
            world.setBlock(placePosition, Block.create(BlockType.WATER), 1, false);
        }
        world.setBlock(placePosition, this);
        return true;
    }

    @Override
    public Item toItem() {
        return Item.<ItemCoralFanDead>create( ItemType.CORAL_FAN_DEAD ).setCoralColor( this.getCoralColor() );
    }

    public void setCoralDirection( RotationDirection rotationDirection ) {
        this.setState( "coral_fan_direction", rotationDirection.ordinal() );
    }

    public RotationDirection getRotationDirection() {
        return this.stateExists( "coral_fan_direction" ) ? RotationDirection.values()[this.getIntState( "coral_fan_direction" )] : RotationDirection.EAST_WEST;
    }

    public BlockCoralFanDead setCoralColor( CoralColor coralColor ) {
        return this.setState( "coral_color", coralColor.name().toLowerCase() );
    }

    public CoralColor getCoralColor() {
        return this.stateExists( "coral_color" ) ? CoralColor.valueOf( this.getStringState( "coral_color" ) ) : CoralColor.BLUE;
    }
}
