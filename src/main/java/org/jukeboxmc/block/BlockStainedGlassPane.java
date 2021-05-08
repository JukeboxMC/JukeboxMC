package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemStainedGlassPane;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStainedGlassPane extends Block {

    public BlockStainedGlassPane() {
        super("minecraft:stained_glass_pane");
    }

    @Override
    public boolean placeBlock(Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace) {
        world.setBlock(placePosition, this);
        return true;
    }

    @Override
    public ItemStainedGlassPane toItem() {
        return new ItemStainedGlassPane().setColor(this.getColor());
    }

    public BlockStainedGlassPane setColor(BlockColor color) {
        this.setState("color", color.name().toLowerCase());
        return this;
    }

    public BlockColor getColor() {
        return this.stateExists("color") ? BlockColor.valueOf(this.getStringState("color").toUpperCase()) : BlockColor.WHITE;
    }

}
