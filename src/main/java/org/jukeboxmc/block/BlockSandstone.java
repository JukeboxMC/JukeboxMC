package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.SandStoneType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemSandstone;
import org.jukeboxmc.item.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSandstone extends Block {

    public BlockSandstone() {
        super( "minecraft:sandstone" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemSandstone toItem() {
        return new ItemSandstone( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SANDSTONE;
    }

    @Override
    public double getHardness() {
        return 0.8;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    public BlockSandstone setSandStoneType( SandStoneType sandStoneType ) {
        this.setState( "sand_stone_type", sandStoneType.name().toLowerCase() );
        return this;
    }

    public SandStoneType getSandStoneType() {
        return this.stateExists( "sand_stone_type" ) ? SandStoneType.valueOf( this.getStringState( "sand_stone_type" ) ) : SandStoneType.DEFAULT;
    }
}
