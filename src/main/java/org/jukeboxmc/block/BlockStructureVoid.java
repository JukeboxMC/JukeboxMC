package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStructureVoid extends Block {

    public BlockStructureVoid() {
        super( "minecraft:structure_void" );
    }

    public void setStructureVoidType( StructureVoidType structureVoidType ) {
        this.setState( "structure_void_type", structureVoidType.toString() );
    }

    public StructureVoidType getStructureVoidType() {
        return this.stateExists( "structure_void_type" ) ? StructureVoidType.valueOf( this.getStringState( "structure_void_type" ).toUpperCase() ) : StructureVoidType.VOID;
    }

    public enum StructureVoidType {
        VOID,
        AIR
    }
}
