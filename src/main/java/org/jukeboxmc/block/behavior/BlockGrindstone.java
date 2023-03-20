package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.Attachment;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGrindstone extends Block {

    public BlockGrindstone( Identifier identifier ) {
        super( identifier );
    }

    public BlockGrindstone( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock(@NotNull Player player, @NotNull World world, Vector blockPosition, @NotNull Vector placePosition, Vector clickedPosition, Item itemInHand, @NotNull BlockFace blockFace ) {
        this.setDirection( player.getDirection().opposite() );
        if ( blockFace == BlockFace.UP ) {
            this.setAttachment( Attachment.STANDING );
        } else if ( blockFace == BlockFace.DOWN ) {
            this.setAttachment( Attachment.HANGING );
        } else {
            if (blockFace.toDirection() != null) {
                this.setDirection(blockFace.toDirection());
            }
            this.setAttachment( Attachment.SIDE );
        }
        return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace );
    }

    @Override
    public boolean interact(@NotNull Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( player.getGrindstoneInventory(), blockPosition );
        return true;
    }

    public void setAttachment(@NotNull Attachment attachment ) {
        this.setState( "attachment", attachment.name().toLowerCase() );
    }

    public @NotNull Attachment getAttachment() {
        return this.stateExists( "attachment" ) ? Attachment.valueOf( this.getStringState( "attachment" ) ) : Attachment.STANDING;
    }

    public void setDirection(@NotNull Direction direction ) {
        switch ( direction ) {
            case SOUTH -> this.setState( "direction", 0 );
            case WEST -> this.setState( "direction", 1 );
            case NORTH -> this.setState( "direction", 2 );
            case EAST -> this.setState( "direction", 3 );
        }
    }

    public @NotNull Direction getDirection() {
        int value = this.stateExists( "direction" ) ? this.getIntState( "direction" ) : 0;
        return switch ( value ) {
            case 0 -> Direction.SOUTH;
            case 1 -> Direction.WEST;
            case 2 -> Direction.NORTH;
            default -> Direction.EAST;
        };
    }
}
