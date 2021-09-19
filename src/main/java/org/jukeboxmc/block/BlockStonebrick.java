package org.jukeboxmc.block;

import org.jukeboxmc.block.type.StoneBrickType;
import org.jukeboxmc.item.ItemStonebrick;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStonebrick extends Block {

    public BlockStonebrick() {
        super( "minecraft:stonebrick" );
    }

    @Override
    public ItemStonebrick toItem() {
        return new ItemStonebrick( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.STONEBRICK;
    }

    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    public BlockStonebrick setStoneBrickType( StoneBrickType stoneBrickType ) {
        return this.setState( "stone_brick_type", stoneBrickType.name().toLowerCase() );
    }

    public StoneBrickType getStoneBrickType() {
        return this.stateExists( "stone_brick_type" ) ? StoneBrickType.valueOf( this.getStringState( "stone_brick_type" ) ) : StoneBrickType.DEFAULT;
    }

}
