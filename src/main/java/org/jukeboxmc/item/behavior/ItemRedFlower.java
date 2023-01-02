package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockRedFlower;
import org.jukeboxmc.block.data.FlowerType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedFlower extends Item {

    private final BlockRedFlower block;

    public ItemRedFlower( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.RED_FLOWER );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemRedFlower( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.RED_FLOWER );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public ItemRedFlower setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public ItemRedFlower setFlowerType( FlowerType flowerType ) {
        this.blockRuntimeId = this.block.setFlowerType( flowerType ).getRuntimeId();
        return this;
    }

    public FlowerType getFlowerType() {
        return this.block.getFlowerType();
    }
}
