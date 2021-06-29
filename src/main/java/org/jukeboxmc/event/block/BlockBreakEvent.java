package org.jukeboxmc.event.block;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBreakEvent extends BlockEvent implements Cancellable {

    private Player player;
    private Block block;
    private List<Item> drops;

    public BlockBreakEvent( Player player, Block block, List<Item> drops ) {
        super( block );

        this.player = player;
        this.block = block;
        this.drops = drops;
    }

    public Player getPlayer() {
        return this.player;
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