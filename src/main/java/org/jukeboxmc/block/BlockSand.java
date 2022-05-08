package org.jukeboxmc.block;

import org.jukeboxmc.block.type.SandType;
import org.jukeboxmc.item.ItemSand;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSand extends Block {

    public BlockSand() {
        super( "minecraft:sand" );
    }

    @Override
    public ItemSand toItem() {
        return new ItemSand( this.runtimeId );
    }

    @Override
    public BlockType getType() {
        return BlockType.SAND;
    }

    @Override
    public double getHardness() {
        return 0.6;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.SHOVEL;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    public BlockSand setSandType( SandType sandType ) {
       return this.setState( "sand_type", sandType.name().toLowerCase() );
    }

    public SandType getSandType() {
        return this.stateExists( "sand_type" ) ? SandType.valueOf( this.getStringState( "sand_type" ) ) : SandType.NORMAL;
    }

}
