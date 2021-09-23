package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemCartographyTable;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCartographyTable extends Block {

    public BlockCartographyTable() {
        super( "minecraft:cartography_table" );
    }

    @Override
    public ItemCartographyTable toItem() {
        return new ItemCartographyTable();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CARTOGRAPGHY_TABLE;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( player.getCartographyTableInventory(), blockPosition );
        return true;
    }

    @Override
    public double getHardness() {
        return 2.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
    }
}
