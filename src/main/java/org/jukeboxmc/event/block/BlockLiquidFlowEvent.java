package org.jukeboxmc.event.block;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockLiquid;
import org.jukeboxmc.event.Cancellable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLiquidFlowEvent extends BlockEvent implements Cancellable {

    private Block blockTo;
    private BlockLiquid source;
    private int newFlowDecay;

    public BlockLiquidFlowEvent(Block blockTo, BlockLiquid source, int newFlowDecay) {
        super(blockTo);

        this.blockTo = blockTo;
        this.source = source;
        this.newFlowDecay = newFlowDecay;
    }

    public Block getBlockTo() {
        return this.blockTo;
    }

    public BlockLiquid getSource() {
        return this.source;
    }

    public int getNewFlowDecay() {
        return this.newFlowDecay;
    }
}