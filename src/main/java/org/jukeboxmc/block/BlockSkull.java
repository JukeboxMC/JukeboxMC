package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.blockentity.BlockEntitySkull;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemSkull;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSkull extends BlockWaterlogable {

    public BlockSkull() {
        super( "minecraft:skull" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        if ( blockFace == BlockFace.DOWN ) {
            return false;
        }
        this.setBlockFace( blockFace );
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        BlockEntityType.SKULL.<BlockEntitySkull>createBlockEntity( this )
                .setRotation( (byte) ( (int) Math.floor( ( player.getYaw() * 16 / 360 ) + 0.5 ) & 0xF ) )
                .setSkullMeta( (byte) itemIndHand.getMeta() )
                .spawn();
        return true;
    }

    @Override
    public ItemSkull toItem() {
        return new ItemSkull();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SKULL;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntitySkull getBlockEntity() {
        return (BlockEntitySkull) this.world.getBlockEntity( this.getLocation(), this.location.getDimension() );
    }

    public BlockSkull setNoDrop( boolean value ) {
        return this.setState( "no_drop_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isNoDrop() {
        return this.stateExists( "no_drop_bit" ) && this.getByteState( "no_drop_bit" ) == 1;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
