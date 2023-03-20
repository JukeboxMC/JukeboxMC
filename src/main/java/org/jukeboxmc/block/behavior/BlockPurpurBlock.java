package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.Axis;
import org.jukeboxmc.block.data.PurpurType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemPurpurBlock;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPurpurBlock extends Block {

    public BlockPurpurBlock( Identifier identifier ) {
        super( identifier );
    }

    public BlockPurpurBlock( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock(@NotNull Player player, @NotNull World world, Vector blockPosition, @NotNull Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
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
    public Item toItem() {
        return Item.<ItemPurpurBlock>create( ItemType.PURPUR_BLOCK ).setPurpurType( this.getPurpurType() );
    }

    public void setAxis(@NotNull Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public @NotNull Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ) ) : Axis.Y;
    }

    public BlockPurpurBlock setPurpurType(@NotNull PurpurType purpurType ) {
        return this.setState( "chisel_type", purpurType.name().toLowerCase() );
    }

    public @NotNull PurpurType getPurpurType() {
        return this.stateExists( "chisel_type" ) ? PurpurType.valueOf( this.getStringState( "chisel_type" ) ) : PurpurType.DEFAULT;
    }
}
