package org.jukeboxmc.event.block;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.event.Cancelable;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPlaceEvent extends BlockEvent implements Cancelable {

    private Player player;
    private Block placedBlock;
    private Block replacedBlock;
    private Block clickedBlock;

    public BlockPlaceEvent(Player player, Block placedBlock, Block replacedBlock, Block clickedBlock) {
        super(placedBlock);

        this.player = player;
        this.placedBlock = placedBlock;
        this.replacedBlock = replacedBlock;
        this.clickedBlock = clickedBlock;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Block getPlacedBlock() {
        return this.placedBlock;
    }

    public void setPlacedBlock(Block placedBlock) {
        this.placedBlock = placedBlock;
    }

    public Block getReplacedBlock() {
        return this.replacedBlock;
    }

    public Block getClickedBlock() {
        return this.clickedBlock;
    }
}