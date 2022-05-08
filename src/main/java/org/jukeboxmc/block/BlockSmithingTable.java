package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemSmithingTable;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSmithingTable extends Block {

    public BlockSmithingTable() {
        super( "minecraft:smithing_table" );
    }

    @Override
    public ItemSmithingTable toItem() {
        return new ItemSmithingTable();
    }

    @Override
    public BlockType getType() {
        return BlockType.SMITHING_TABLE;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( player.getSmithingTableInventory(), blockPosition );
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
