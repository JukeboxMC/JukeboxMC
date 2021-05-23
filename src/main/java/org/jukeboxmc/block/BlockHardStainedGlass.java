package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.BlockColor;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemHardStainedGlass;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockHardStainedGlass extends Block {

    public BlockHardStainedGlass() {
        super( "minecraft:hard_stained_glass" );
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setColor( BlockColor.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemHardStainedGlass toItem() {
        return new ItemHardStainedGlass();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.HARD_STAINED_GLASS;
    }


    public void setColor( BlockColor color ) {
        this.setState( "color", color.name().toLowerCase() );
    }

    public BlockColor getColor() {
        return this.stateExists( "color" ) ? BlockColor.valueOf( this.getStringState( "color" ) ) : BlockColor.WHITE;
    }
}
