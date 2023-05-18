package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.blockentity.BlockEntitySkull;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSkull extends Block implements Waterlogable{

    public BlockSkull( Identifier identifier ) {
        super( identifier );
    }

    public BlockSkull( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        this.setBlockFace( blockFace );
        BlockEntity.<BlockEntitySkull>create( BlockEntityType.SKULL, this )
                .setRotation( (byte) ( (int) Math.floor( ( player.getYaw() * 16 / 360 ) + 0.5 ) & 0xF ) )
                .setSkullMeta( (byte) itemInHand.getMeta() )
                .spawn();
        if (world.getBlock(placePosition) instanceof BlockWater blockWater && blockWater.getLiquidDepth() == 0) {
            world.setBlock(placePosition.add(0, 1, 0), Block.create(BlockType.WATER), 1, false);
        }
        world.setBlock(placePosition, this);
        return true;
    }

    @Override
    public BlockEntitySkull getBlockEntity() {
        return (BlockEntitySkull) this.location.getWorld().getBlockEntity( this.location, this.location.getDimension() );
    }

    @Override
    public int getWaterLoggingLevel() {
        return 1;
    }

    public BlockSkull setNoDrop(boolean value ) {
        return this.setState( "no_drop_bit", value ? 1 : 0 );
    }

    public boolean isNoDrop() {
        return this.stateExists( "no_drop_bit" ) && this.getIntState( "no_drop_bit" ) == 1;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
