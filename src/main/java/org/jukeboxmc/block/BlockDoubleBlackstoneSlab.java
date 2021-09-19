package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemBlackstoneSlab;
import org.jukeboxmc.item.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

import java.util.List;

public class BlockDoubleBlackstoneSlab extends BlockSlab {

    public BlockDoubleBlackstoneSlab() {
        super("minecraft:blackstone_double_slab");
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemBlackstoneSlab toItem() {
        return new ItemBlackstoneSlab();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BLACKSTONE_SLAB;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    @Override
    public List<Item> getDrops( Item itemInHand ) {
        return super.getDrops( itemInHand, 2 );
    }

}