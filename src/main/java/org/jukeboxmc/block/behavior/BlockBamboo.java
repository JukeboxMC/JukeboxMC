package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.BambooLeafSize;
import org.jukeboxmc.block.data.BambooStalkThickness;
import org.jukeboxmc.block.data.UpdateReason;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.event.block.BlockGrowEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBamboo extends Block {

    public BlockBamboo(Identifier identifier) {
        super(identifier);
    }

    public BlockBamboo(Identifier identifier, NbtMap blockStates) {
        super(identifier, blockStates);
    }

    @Override
    public boolean placeBlock(Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace) {
        Block blockDown = this.getSide(BlockFace.DOWN);
        BlockType blockDownType = blockDown.getType();
        if (!blockDownType.equals(BlockType.BAMBOO) && !blockDownType.equals(BlockType.BAMBOO_SAPLING)) {
            if (!blockDownType.equals(BlockType.GRASS) && !blockDownType.equals(BlockType.DIRT) && !blockDownType.equals(BlockType.SAND) && !blockDownType.equals(BlockType.GRAVEL) && !blockDownType.equals(BlockType.PODZOL)) {
                return false;
            }
            if (world.getBlock(placePosition) instanceof BlockWater) {
                return false;
            }
            world.setBlock(placePosition, Block.create(BlockType.BAMBOO_SAPLING));
            return true;
        }

        boolean canGrow = true;
        if (blockDownType.equals(BlockType.BAMBOO_SAPLING)) {
            this.setBambooLeafSize(BambooLeafSize.SMALL_LEAVES);
        }
        if (blockDown instanceof BlockBamboo blockBamboo) {
            canGrow = !blockBamboo.hasAge();
            boolean thick = blockBamboo.getBambooStalkThickness().equals(BambooStalkThickness.THICK);
            if (!thick){
                boolean setThick = true;
                for (int i = 2; i <= 3; i++) {
                    if (this.getSide(BlockFace.DOWN, 0, i).getType().equals(BlockType.BAMBOO)){
                        setThick = true;
                    }
                }
                if (setThick) {
                    this.setBambooStalkThickness(BambooStalkThickness.THICK);
                    this.setBambooLeafSize(BambooLeafSize.LARGE_LEAVES);
                    blockBamboo.setBambooLeafSize(BambooLeafSize.SMALL_LEAVES);
                    blockBamboo.setBambooStalkThickness(BambooStalkThickness.THICK);
                    blockBamboo.setAge(true);
                    world.setBlock(blockBamboo.getLocation(), blockBamboo);
                    while ((blockDown = blockDown.getSide(BlockFace.DOWN)) instanceof BlockBamboo) {
                        blockBamboo = (BlockBamboo) blockDown;
                        blockBamboo.setBambooStalkThickness(BambooStalkThickness.THICK);
                        blockBamboo.setBambooLeafSize(BambooLeafSize.NO_LEAVES);
                        blockBamboo.setAge(true);
                        world.setBlock(blockBamboo.getLocation(), blockBamboo);
                    }
                } else {
                    this.setBambooLeafSize(BambooLeafSize.SMALL_LEAVES);
                    blockBamboo.setAge(true);
                    world.setBlock(blockBamboo.getLocation(), blockBamboo);
                }
            } else {
                this.setBambooStalkThickness(BambooStalkThickness.THICK);
                this.setBambooLeafSize(BambooLeafSize.LARGE_LEAVES);
                this.setAge(false);
                blockBamboo.setBambooLeafSize(BambooLeafSize.LARGE_LEAVES);
                blockBamboo.setAge(true);
                world.setBlock(blockBamboo.getLocation(), blockBamboo);
                if (blockBamboo.getSide(BlockFace.DOWN) instanceof BlockBamboo) {
                    blockBamboo = (BlockBamboo) blockBamboo.getSide(BlockFace.DOWN);
                    blockBamboo.setBambooLeafSize(BambooLeafSize.SMALL_LEAVES);
                    blockBamboo.setAge(true);
                    world.setBlock(blockBamboo.getLocation(), blockBamboo);
                    if (blockBamboo.getSide(BlockFace.DOWN) instanceof BlockBamboo) {
                        blockBamboo = (BlockBamboo) blockBamboo.getSide(BlockFace.DOWN);
                        blockBamboo.setBambooLeafSize(BambooLeafSize.NO_LEAVES);
                        blockBamboo.setAge(true);
                        world.setBlock(blockBamboo.getLocation(), blockBamboo);
                    }
                }
            }
        } else if (!blockDownType.equals(BlockType.BAMBOO) &&
                !blockDownType.equals(BlockType.GRASS) &&
                !blockDownType.equals(BlockType.DIRT) &&
                !blockDownType.equals(BlockType.SAND) &&
                !blockDownType.equals(BlockType.GRAVEL) &&
                !blockDownType.equals(BlockType.PODZOL) &&
                !blockDownType.equals(BlockType.BAMBOO_SAPLING)) {
            return false;
        }
        int height = canGrow ? countHeight() : 0;

        if (!canGrow || height >= 15 || height >= 11 && ThreadLocalRandom.current().nextFloat() < 0.25f) {
            this.setAge(true);
        }

        world.setBlock(this.location, this);
        return true;
    }

    @Override
    public long onUpdate(UpdateReason updateReason) {
       if (updateReason.equals(UpdateReason.NORMAL)) {
           Block blockDown = this.getSide(BlockFace.DOWN);
           if (!blockDown.getType().equals(BlockType.BAMBOO) &&
                   !blockDown.getType().equals(BlockType.BAMBOO_SAPLING) &&
                   !blockDown.getType().equals(BlockType.GRASS) &&
                   !blockDown.getType().equals(BlockType.DIRT) &&
                   !blockDown.getType().equals(BlockType.SAND) &&
                   !blockDown.getType().equals(BlockType.GRAVEL) &&
                   !blockDown.getType().equals(BlockType.GRAVEL)) {
               this.location.getWorld().scheduleBlockUpdate(this,0 );
           }
           return 0;
       } else if (updateReason.equals(UpdateReason.SCHEDULED)) {
           this.breakNaturally();
       } else if (updateReason.equals(UpdateReason.RANDOM)) {
           Block blockUp = this.getSide(BlockFace.UP);
           if (!this.hasAge() && blockUp.getType().equals(BlockType.AIR) && ThreadLocalRandom.current().nextInt(3) == 0) {
               this.grow(blockUp);
           }
           return 0;
       }
        return 0;
    }

    public boolean grow(Block block) {
        BlockBamboo blockBamboo = Block.create(BlockType.BAMBOO);
        if (this.getBambooStalkThickness().equals(BambooStalkThickness.THICK)) {
            blockBamboo.setBambooStalkThickness(BambooStalkThickness.THICK, false);
            blockBamboo.setBambooLeafSize(BambooLeafSize.LARGE_LEAVES, false);
        } else {
            blockBamboo.setBambooLeafSize(BambooLeafSize.SMALL_LEAVES, false);
        }
        BlockGrowEvent blockGrowEvent = new BlockGrowEvent(block, blockBamboo);
        Server.getInstance().getPluginManager().callEvent(blockGrowEvent);
        if (!blockGrowEvent.isCancelled()) {
            BlockBamboo newState = (BlockBamboo) blockGrowEvent.getNewState();
            newState.setLocation(new Location(this.location.getWorld(), this.location.getBlockX(), block.getLocation().getBlockY(), this.location.getBlockZ()));
            newState.placeBlock(null, this.location.getWorld(), null, newState.location, new Vector(0.5f, 0.5f,0.5f), Item.create(ItemType.AIR), BlockFace.DOWN);
            return true;
        }
        return false;
    }

    public int countHeight() {
        int count = 0;
        Optional<Block> opt;
        Block down = this;
        while ((opt = down.getSide(BlockFace.DOWN).firstInLayer(b-> b.getType() == BlockType.BAMBOO)).isPresent()) {
            down = opt.get();
            if (++count >= 16) {
                break;
            }
        }
        return count;
    }

    public void setBambooLeafSize(BambooLeafSize bambooLeafSize) {
        this.setState("bamboo_leaf_size", bambooLeafSize.name().toLowerCase());
    }

    public void setBambooLeafSize(BambooLeafSize bambooLeafSize, boolean update) {
        this.setState("bamboo_leaf_size", bambooLeafSize.name().toLowerCase(), update);
    }

    public BambooLeafSize getBambooLeafSize() {
        return this.stateExists("bamboo_leaf_size") ? BambooLeafSize.valueOf(this.getStringState("bamboo_leaf_size")) : BambooLeafSize.NO_LEAVES;
    }

    public void setAge(boolean value) {
        this.setState("age_bit", value ? (byte) 1 : (byte) 0);
    }

    public void setAge(boolean value, boolean update) {
        this.setState("age_bit", value ? (byte) 1 : (byte) 0, update);
    }

    public boolean hasAge() {
        return this.stateExists("age_bit") && this.getByteState("age_bit") == 1;
    }

    public void setBambooStalkThickness(BambooStalkThickness bambooStalkThickness) {
        this.setState("bamboo_stalk_thickness", bambooStalkThickness.name().toLowerCase());
    }

    public void setBambooStalkThickness(BambooStalkThickness bambooStalkThickness, boolean update) {
        this.setState("bamboo_stalk_thickness", bambooStalkThickness.name().toLowerCase(), update);
    }

    public BambooStalkThickness getBambooStalkThickness() {
        return this.stateExists("bamboo_stalk_thickness") ? BambooStalkThickness.valueOf(this.getStringState("bamboo_stalk_thickness")) : BambooStalkThickness.THIN;
    }
}
