package org.jukeboxmc.block;

public class BlockRespawnAnchor extends Block {

    public BlockRespawnAnchor() {
        super( "minecraft:respawn_anchor" );
    }

    public void setRespawnAnchorCharge( int value ) { //0-4
        this.setState( "respawn_anchor_charge", value );
    }

    public int getRespawnAnchorCharge() {
        return this.stateExists( "respawn_anchor_charge" ) ? this.getIntState( "respawn_anchor_charge" ) : 0;
    }
}