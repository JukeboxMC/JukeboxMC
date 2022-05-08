package org.jukeboxmc.block;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtMapBuilder;
import com.nukkitx.nbt.NbtType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.BlockColor;
import org.jukeboxmc.blockentity.BlockEntityShulkerBox;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.inventory.ShulkerBoxInventory;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAir;
import org.jukeboxmc.item.ItemShulkerBox;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockShulkerBox extends BlockWaterlogable {

    public BlockShulkerBox() {
        super( "minecraft:shulker_box" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        boolean value = super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        if ( value ) {
            BlockEntityShulkerBox blockEntityShulkerBox = (BlockEntityShulkerBox) BlockEntityType.SHULKER_BOX.<BlockEntityShulkerBox>createBlockEntity( this ).setUndyed( false ).spawn();
            if ( itemIndHand.getNBT() != null && itemIndHand.getNBT().containsKey( "Items" ) ) {
                NbtMap nbt = itemIndHand.getNBT();
                List<NbtMap> items = nbt.getList( "Items", NbtType.COMPOUND );
                for ( NbtMap nbtMap : items ) {
                    Item item = blockEntityShulkerBox.toItem( nbtMap );
                    byte slot = nbtMap.getByte( "Slot", (byte) 127 );
                    if ( slot == 127 ) {
                        blockEntityShulkerBox.getShulkerBoxInventory().addItem( item, false );
                    } else {
                        blockEntityShulkerBox.getShulkerBoxInventory().setItem( slot, item, false );
                    }
                }
            }
        }
        return value;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        BlockEntityShulkerBox blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            blockEntity.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
            return true;
        }
        return false;
    }

    @Override
    public ItemShulkerBox toItem() {
        ItemShulkerBox itemShulkerBox = new ItemShulkerBox( this.runtimeId );
        BlockEntityShulkerBox blockEntity = this.getBlockEntity();
        if ( blockEntity == null ) {
            return itemShulkerBox;
        }
        ShulkerBoxInventory shulkerBoxInventory = blockEntity.getShulkerBoxInventory();
        NbtMapBuilder builder = NbtMap.builder();
        List<NbtMap> itemsCompoundList = new ArrayList<>();
        for ( int slot = 0; slot < shulkerBoxInventory.getSize(); slot++ ) {
            Item item = shulkerBoxInventory.getItem( slot );

            if ( item != null && !(item instanceof ItemAir ) ) {
                NbtMapBuilder itemCompound = NbtMap.builder();
                itemCompound.putByte( "Slot", (byte) slot );
                blockEntity.fromItem( item, itemCompound );

                itemsCompoundList.add( itemCompound.build() );
            }
        }
        builder.putList( "Items", NbtType.COMPOUND, itemsCompoundList );
        itemShulkerBox.setNBT( builder.build() );

        return itemShulkerBox;
    }

    @Override
    public List<Item> getDrops( Item itemInHand ) {
        return Collections.singletonList( this.toItem() );
    }

    @Override
    public BlockType getType() {
        return BlockType.SHULKER_BOX;
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntityShulkerBox getBlockEntity() {
        if(this.location == null) {
            return null;
        }
        return (BlockEntityShulkerBox) this.world.getBlockEntity( this.location, this.location.getDimension() );
    }

    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return true;
    }

    public BlockShulkerBox setColor( BlockColor color ) {
        return this.setState( "color", color.name().toLowerCase() );
    }

    public BlockColor getColor() {
        return this.stateExists( "color" ) ? BlockColor.valueOf( this.getStringState( "color" ) ) : BlockColor.WHITE;
    }
}
