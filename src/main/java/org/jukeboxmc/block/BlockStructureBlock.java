package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemStructureBlock;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStructureBlock extends BlockWaterlogable {

    public BlockStructureBlock() {
        super( "minecraft:structure_block" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setStructureBlockType( StructureBlockType.values()[itemIndHand.getMeta()] );
        return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
    }

    @Override
    public ItemStructureBlock toItem() {
        return new ItemStructureBlock();
    }

    @Override
    public BlockType getType() {
        return BlockType.STRUCTURE_BLOCK;
    }

    public void setStructureBlockType( StructureBlockType structureBlockType ) {
        this.setState( "structure_block_type", structureBlockType.name().toLowerCase() );
    }

    public StructureBlockType getStructureBlockType() {
        return this.stateExists( "structure_block_type" ) ? StructureBlockType.valueOf( this.getStringState( "structure_block_type" ) ) : StructureBlockType.DATA;
    }

    public enum StructureBlockType {
        DATA,
        SAVE,
        LOAD,
        CORNER,
        INVALID,
        EXPORT
    }
}
