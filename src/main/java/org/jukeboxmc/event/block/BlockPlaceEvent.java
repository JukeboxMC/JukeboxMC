package org.jukeboxmc.event.block;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.event.CancelablePlayerEvent;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPlaceEvent extends CancelablePlayerEvent {

    private Block placedBlock;
    private Block replacedBlock;
    private Block clickedBlock;

    public BlockPlaceEvent( Player player, Block placedBlock, Block replacedBlock, Block clickedBlock ) {
        super( player );
        this.placedBlock = placedBlock;
        this.replacedBlock = replacedBlock;
        this.clickedBlock = clickedBlock;
    }

    public Block getPlacedBlock() {
        return this.placedBlock;
    }

    public void setPlacedBlock( Block placedBlock ) {
        this.placedBlock = placedBlock;
    }

    public Block getReplacedBlock() {
        return this.replacedBlock;
    }

    public Block getClickedBlock() {
        return this.clickedBlock;
    }

}
