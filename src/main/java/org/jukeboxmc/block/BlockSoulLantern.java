package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemSoulLantern;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

public class BlockSoulLantern extends Block {

    public BlockSoulLantern() {
        super("minecraft:soul_lantern");
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        boolean hanging = blockFace != BlockFace.UP && this.isBlockAboveValid() && ( !this.isBlockUnderValid() || blockFace == BlockFace.DOWN );
        if ( !this.isBlockUnderValid() && !hanging ) {
            return false;
        }

        this.setHanging( hanging );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemSoulLantern toItem() {
        return new ItemSoulLantern();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SOUL_LANTERN;
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    private boolean isBlockAboveValid() {
        Block up = this.getSide( BlockFace.UP );
        if ( up instanceof BlockLeaves ) {
            return false;
        } else if ( up instanceof BlockFence || up instanceof BlockWall ) {
            return true;
        } else if ( up instanceof BlockSlab ) {
            return !( (BlockSlab) up ).isTopSlot();
        } else if ( up instanceof BlockStairs ) {
            return !( (BlockStairs) up ).isUpsideDown();
        } else return up.isSolid();
    }

    private boolean isBlockUnderValid() {
        Block down = this.getSide( BlockFace.DOWN );
        if ( down instanceof BlockLeaves ) {
            return false;
        } else if ( down instanceof BlockFence || down instanceof BlockWall ) {
            return true;
        } else if ( down instanceof BlockSlab ) {
            return ( (BlockSlab) down ).isTopSlot();
        } else if ( down instanceof BlockStairs ) {
            return ( (BlockStairs) down ).isUpsideDown();
        } else return down.isSolid();
    }

    public void setHanging( boolean value ) {
        this.setState( "hanging", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isHanging() {
        return this.stateExists( "hanging" ) && this.getByteState( "hanging" ) == 1;
    }
}