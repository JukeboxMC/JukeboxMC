package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemRespawnAnchor;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

public class BlockRespawnAnchor extends Block {

    public BlockRespawnAnchor() {
        super( "minecraft:respawn_anchor" );
    }


    @Override
    public ItemRespawnAnchor toItem() {
        return new ItemRespawnAnchor();
    }

    @Override
    public BlockType getType() {
        return BlockType.RESPAWN_ANCHOR;
    }

    @Override
    public double getHardness() {
        return 50;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public ItemTierType getTierType() {
        return ItemTierType.DIAMOND;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    public void setRespawnAnchorCharge( int value ) { //0-4
        this.setState( "respawn_anchor_charge", value );
    }

    public int getRespawnAnchorCharge() {
        return this.stateExists( "respawn_anchor_charge" ) ? this.getIntState( "respawn_anchor_charge" ) : 0;
    }
}