package org.jukeboxmc.block;

import org.apache.commons.math3.util.FastMath;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.SignDirection;
import org.jukeboxmc.block.type.BlockColor;
import org.jukeboxmc.blockentity.BlockEntityBanner;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class BlockBanner extends BlockWaterlogable {

    public BlockBanner( String identifier ) {
        super( identifier );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block block = world.getBlock( placePosition );
        if ( blockFace == BlockFace.UP ) {
            this.setSignDirection( SignDirection.values()[(int) FastMath.floor( ( ( player.getLocation().getYaw() + 180 ) * 16 / 360 ) + 0.5 ) & 0x0f] );
            world.setBlock( placePosition, this );
        } else {
            BlockWallBanner blockWallBanner = new BlockWallBanner();
            blockWallBanner.setBlockFace( blockFace );
            world.setBlock( placePosition, blockWallBanner, 0 );
        }
        if( block instanceof BlockWater ) {
            world.setBlock( placePosition, block, 1 );
        }
        int type = itemIndHand.getNBT() != null ? itemIndHand.getNBT().getInt( "Type", 0 ) : 0;
        BlockEntityType.BANNER.<BlockEntityBanner>createBlockEntity( this ).setColor( BlockColor.values()[itemIndHand.getMeta()] ).setType( type ).spawn();
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntityBanner getBlockEntity() {
        return (BlockEntityBanner) this.world.getBlockEntity( this.getLocation(), this.location.getDimension() );
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public double getHardness() {
        return 1;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
    }

    public void setSignDirection( SignDirection signDirection ) {
        this.setState( "ground_sign_direction", signDirection.ordinal() );
    }

    public SignDirection getSignDirection() {
        return this.stateExists( "ground_sign_direction" ) ? SignDirection.values()[this.getIntState( "ground_sign_direction" )] : SignDirection.SOUTH;
    }

}
