package jukeboxmc.block;

import org.jukeboxmc.item.ItemEndGateway;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEndGateway extends Block {

    public BlockEndGateway() {
        super( "minecraft:end_gateway" );
    }

    @Override
    public ItemEndGateway toItem() {
        return new ItemEndGateway();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.END_GATEWAY;
    }

}
