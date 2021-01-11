package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
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
    public void placeBlock( Player player, World world, BlockPosition placePosition, Item itemIndHand, BlockFace blockFace ) {
        this.setSandStoneType( SandStoneType.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getSandStoneType().ordinal() );
    }

    public void setSandStoneType( SandStoneType sandStoneType ) {
        this.setState( "sand_stone_type", sandStoneType.name().toLowerCase() );
    }

    public SandStoneType getSandStoneType() {
        return this.stateExists( "sand_stone_type" ) ? SandStoneType.valueOf( this.getStringState( "sand_stone_type" ).toUpperCase() ) : SandStoneType.DEFAULT;
    }

    public enum SandStoneType {
        DEFAULT,
        HEIROGLYPHS,
        CUT,
        SMOOTH
    }
}
