package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.Axis;
import org.jukeboxmc.block.data.WoodType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemWood;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWood extends Block {

    public BlockWood( Identifier identifier ) {
        super( identifier );
    }

    public BlockWood( Identifier identifier, NbtMap blockStates ) {
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
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public Item toItem() {
        return Item.<ItemWood>create( ItemType.WOOD ).setWoodType( this.getWoodType() ).setStripped( this.isStripped() );
    }

    public BlockWood setWoodType(@NotNull WoodType woodType ) {
        return this.setState( "wood_type", woodType.name().toLowerCase() );
    }

    public @NotNull WoodType getWoodType() {
        return this.stateExists( "wood_type" ) ? WoodType.valueOf( this.getStringState( "wood_type" ) ) : WoodType.OAK;
    }

    public BlockWood setStripped( boolean value ) {
        return this.setState( "stripped_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isStripped() {
        return this.stateExists( "stripped_bit" ) && this.getByteState( "stripped_bit" ) == 1;
    }

    public void setAxis(@NotNull Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public @NotNull Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ) ) : Axis.Y;
    }
}
