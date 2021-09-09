package jukeboxmc.event.block;

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

    /**
     * Creates a new {@link BlockLiquidFlowEvent}
     *
     * @param blockTo      which is the block the liquid will flow into
     * @param source       represents the liquid source which caused the flow
     * @param newFlowDecay which stands for the flow decay
     */
    public BlockLiquidFlowEvent( Block blockTo, BlockLiquid source, int newFlowDecay ) {
        super( blockTo );

        this.blockTo = blockTo;
        this.source = source;
        this.newFlowDecay = newFlowDecay;
    }

    /**
     * Retrieves the block the {@link BlockLiquid} will flow into
     *
     * @return a fresh {@link Block}
     */
    public Block getBlockTo() {
        return this.blockTo;
    }

    /**
     * Retrieves the source which caused the flow
     *
     * @return a fresh {@link BlockLiquid}
     */
    public BlockLiquid getSource() {
        return this.source;
    }

    /**
     * Retrieves the new flow decay
     *
     * @return a fresh flow decay value
     */
    public int getNewFlowDecay() {
        return this.newFlowDecay;
    }
}