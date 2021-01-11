package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStructureBlock extends Block {

    public BlockStructureBlock() {
        super( "minecraft:structure_block" );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getStructureBlockType().ordinal() );
    }

    public void setStructureBlockType( StructureBlockType structureBlockType ) {
        this.setState( "structure_block_type", structureBlockType.name().toLowerCase() );
    }

    public StructureBlockType getStructureBlockType() {
        return this.stateExists( "structure_block_type" ) ? StructureBlockType.valueOf( this.getStringState( "structure_block_type" ).toUpperCase() ) : StructureBlockType.DATA;
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
