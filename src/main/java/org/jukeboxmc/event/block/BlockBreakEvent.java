package org.jukeboxmc.event.block;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.event.CancelablePlayerEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBreakEvent extends CancelablePlayerEvent {

    private Block block;
    private List<Item> drops;

    public BlockBreakEvent( Player player, Block block, List<Item> drops ) {
        super( player );
        this.block = block;
        this.drops = drops;
    }

    public Block getBlock() {
        return this.block;
    }

    public void setBlock( Block block ) {
        this.block = block;
    }

    public List<Item> getDrops() {
        return this.drops;
    }

    public void setDrops( List<Item> drops ) {
        this.drops = drops;
    }
}
