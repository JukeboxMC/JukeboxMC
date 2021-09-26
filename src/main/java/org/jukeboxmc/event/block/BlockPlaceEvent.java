package org.jukeboxmc.event.block;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPlaceEvent extends BlockEvent implements Cancellable {

    private final Player player;
    private Block placedBlock;
    private final Block replacedBlock;
    private final Block clickedBlock;

    /**
     * Creates a new {@link BlockPlaceEvent}
     *
     * @param player        who placed a block
     * @param placedBlock   which should be placed
     * @param replacedBlock which represents the original block before the block place
     * @param clickedBlock  which represents the block the player clicked at
     */
    public BlockPlaceEvent( Player player, Block placedBlock, Block replacedBlock, Block clickedBlock ) {
        super( placedBlock );

        this.player = player;
        this.placedBlock = placedBlock;
        this.replacedBlock = replacedBlock;
        this.clickedBlock = clickedBlock;
    }

    /**
     * Retrieves the player who placed a block
     *
     * @return a fresh {@link Player}
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Retrieves the block which should be placed
     *
     * @return a fresh {@link Block}
     */
    public Block getPlacedBlock() {
        return this.placedBlock;
    }

    /**
     * Modifies the block which should be placed
     *
     * @param placedBlock which should be placed
     */
    public void setPlacedBlock( Block placedBlock ) {
        this.placedBlock = placedBlock;
    }

    /**
     * Retrieves the block which was replaced
     *
     * @return a fresh {@link Block}
     */
    public Block getReplacedBlock() {
        return this.replacedBlock;
    }

    /**
     * Retrieves the block the player clicked at
     *
     * @return {@link Block}
     */
    public Block getClickedBlock() {
        return this.clickedBlock;
    }
}