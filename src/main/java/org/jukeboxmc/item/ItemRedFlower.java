package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRedFlower;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.FlowerType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedFlower extends Item {

    public ItemRedFlower( int blockRuntimeId ) {
        super( 38, blockRuntimeId );
    }

    @Override
    public BlockRedFlower getBlock() {
        return (BlockRedFlower) BlockType.getBlock(this.blockRuntimeId);
    }

    public void setFlowerType( FlowerType flowerType ) {
        this.setMeta( flowerType.ordinal() );
    }

    public FlowerType getFlowerType() {
        return FlowerType.values()[this.getMeta()];
    }

}
