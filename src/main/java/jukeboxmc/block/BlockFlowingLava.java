package jukeboxmc.block;

import org.jukeboxmc.item.ItemLava;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFlowingLava extends BlockLava {

    public BlockFlowingLava() {
        super( "minecraft:flowing_lava" );
    }

    @Override
    public ItemLava toItem() {
        return new ItemLava();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LAVA;
    }

    @Override
    public boolean canBeReplaced( Block block ) {
        return true;
    }

    @Override
    public BlockLiquid getBlock( int liquidDepth ) {
        BlockFlowingLava blockFlowingLava = new BlockFlowingLava();
        blockFlowingLava.setLiquidDepth( liquidDepth );
        return blockFlowingLava;
    }
}
