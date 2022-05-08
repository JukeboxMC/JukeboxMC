package org.jukeboxmc.block;

import org.jukeboxmc.block.type.FlowerType;
import org.jukeboxmc.item.ItemRedFlower;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedFlower extends Block {

    public BlockRedFlower() {
        super( "minecraft:red_flower" );
    }

    @Override
    public ItemRedFlower toItem() {
        return new ItemRedFlower( this.runtimeId );
    }

    @Override
    public BlockType getType() {
        return BlockType.RED_FLOWER;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    public BlockRedFlower setFlowerType( FlowerType flowerType ) {
        return this.setState( "flower_type", flowerType.name().toLowerCase() );
    }

    public FlowerType getFlowerType() {
        return this.stateExists( "flower_type" ) ? FlowerType.valueOf( this.getStringState( "flower_type" ) ) : FlowerType.TULIP_RED;
    }

}
