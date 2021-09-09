package jukeboxmc.event.block;

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

    /**
     * Creates a new {@link BlockBreakEvent}
     *
     * @param player who has broken the given block
     * @param block  which was broken by the player
     * @param drops  which are the drops of the block affected by the item which was used
     */
    public BlockBreakEvent( Player player, Block block, List<Item> drops ) {
        super( block );

        this.player = player;
        this.block = block;
        this.drops = drops;
    }

    /**
     * Retrieves the player who comes with this event
     *
     * @return a fresh {@link Player}
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Modifies the block which was broken
     *
     * @param block which was broken
     */
    public void setBlock( Block block ) {
        this.block = block;
    }

    /**
     * Retrieves the drops of the block
     *
     * @return a fresh {@link List<Item>}
     */
    public List<Item> getDrops() {
        return this.drops;
    }

    /**
     * Modifies the drops of the block which was broken
     *
     * @param drops which should be updated
     */
    public void setDrops( List<Item> drops ) {
        this.drops = drops;
    }
}