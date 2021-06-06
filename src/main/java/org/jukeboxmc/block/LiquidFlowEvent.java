package org.jukeboxmc.block;

import org.jukeboxmc.event.CancelableBlockEvent;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class LiquidFlowEvent extends CancelableBlockEvent {

    private Block blockTo;
    private BlockLiquid source;
    private int newFlowDecay;

    public LiquidFlowEvent( Block blockTo, BlockLiquid source, int newFlowDecay ) {
        super( blockTo );
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
