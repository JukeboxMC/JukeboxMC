package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.blockentity.BlockEntityEnchantmentTable;
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
public class BlockEnchantingTable extends Block {

    public BlockEnchantingTable( Identifier identifier ) {
        super( identifier );
    }

    public BlockEnchantingTable( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        boolean value = super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace );
        if ( value ) {
            BlockEntity.create( BlockEntityType.ENCHANTMENT_TABLE, this ).spawn();
        }
        return value;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        BlockEntityEnchantmentTable blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            blockEntity.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
            return true;
        }
        return false;
    }

    @Override
    public BlockEntityEnchantmentTable getBlockEntity() {
        return (BlockEntityEnchantmentTable) this.location.getWorld().getBlockEntity( this.location, this.location.getDimension() );
    }
}
