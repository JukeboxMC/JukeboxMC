package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.UpdateReason;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.Difficulty;
import org.jukeboxmc.world.World;

public class BlockCake extends Block implements Waterlogable {

    public BlockCake(Identifier identifier) {
        super(identifier);
    }

    public BlockCake(Identifier identifier, NbtMap blockStates) {
        super(identifier, blockStates);
    }

    @Override
    public boolean placeBlock(Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace) {
        if (this.getSide(BlockFace.DOWN) instanceof BlockAir) {
            return false;
        }
        if (world.getBlock(placePosition) instanceof BlockWater blockWater && blockWater.getLiquidDepth() == 0) {
            world.setBlock(placePosition, Block.create(BlockType.WATER), 1, false);
        }
        world.setBlock(placePosition, this);
        return true;
    }

    @Override
    public long onUpdate(UpdateReason updateReason) {
        if (updateReason.equals(UpdateReason.NORMAL)) {
            if (this.getSide(BlockFace.DOWN) instanceof BlockAir) {
                this.location.getWorld().setBlock(this.location, Block.create(BlockType.AIR));
            }
        }
        return 0;
    }

    @Override
    public boolean interact(Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand) {
        if ((player.getHunger() < 20 || player.getGameMode().equals(GameMode.CREATIVE) || player.getServer().getDifficulty().equals(Difficulty.PEACEFUL))) {
            int biteCounter = this.getBiteCounter();
            if (biteCounter <= 6) {
                this.setBiteCounter(biteCounter = biteCounter + 1);
            }
            if (biteCounter > 6) {
                player.getWorld().setBlock(this.location, Block.create(BlockType.AIR));
            } else {
                player.addHunger(2);
                player.setSaturation(Math.min(player.getSaturation() + 0.4f, player.getHunger()));
            }
            return true;
        }
        return false;
    }

    @Override
    public int getWaterLoggingLevel() {
        return 1;
    }

    public BlockCake setBiteCounter(int value) {
        this.setState("bite_counter", Math.min(value, 6));
        return this;
    }

    public int getBiteCounter() {
        return this.stateExists("bite_counter") ? this.getIntState("bite_counter") : 0;
    }
}
