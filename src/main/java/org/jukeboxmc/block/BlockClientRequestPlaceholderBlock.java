package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemClientRequestPlaceholderBlock;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockClientRequestPlaceholderBlock extends Block {

    public BlockClientRequestPlaceholderBlock() {
        super( "minecraft:client_request_placeholder_block" );
    }

    @Override
    public ItemClientRequestPlaceholderBlock toItem() {
        return new ItemClientRequestPlaceholderBlock();
    }

    @Override
    public BlockType getType() {
        return BlockType.CLIENT_REQUEST_PLACEHOLDER_BLOCK;
    }
}