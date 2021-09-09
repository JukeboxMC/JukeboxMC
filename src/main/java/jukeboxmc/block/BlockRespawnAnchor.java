package jukeboxmc.block;

import org.jukeboxmc.item.ItemRespawnAnchor;

public class BlockRespawnAnchor extends Block {

    public BlockRespawnAnchor() {
        super( "minecraft:respawn_anchor" );
    }


    @Override
    public ItemRespawnAnchor toItem() {
        return new ItemRespawnAnchor();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.RESPAWN_ANCHOR;
    }

    public void setRespawnAnchorCharge( int value ) { //0-4
        this.setState( "respawn_anchor_charge", value );
    }

    public int getRespawnAnchorCharge() {
        return this.stateExists( "respawn_anchor_charge" ) ? this.getIntState( "respawn_anchor_charge" ) : 0;
    }
}