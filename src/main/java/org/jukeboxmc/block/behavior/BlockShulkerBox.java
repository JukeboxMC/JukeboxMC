package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.nbt.NbtMapBuilder;
import org.cloudburstmc.nbt.NbtType;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.BlockColor;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.blockentity.BlockEntityShulkerBox;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.inventory.ShulkerBoxInventory;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemShulkerBox;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockShulkerBox extends Block {

    public BlockShulkerBox( Identifier identifier ) {
        super( identifier );
    }

    public BlockShulkerBox( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        boolean value = super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        if ( value ) {
            BlockEntityShulkerBox blockEntityShulkerBox = (BlockEntityShulkerBox) BlockEntity.<BlockEntityShulkerBox>create( BlockEntityType.SHULKER_BOX, this ).setUndyed( false ).spawn();
            if ( itemIndHand.getNbt() != null && itemIndHand.getNbt().containsKey( "Items" ) ) {
                NbtMap nbt = itemIndHand.getNbt();
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
        ItemShulkerBox itemShulkerBox = Item.create( ItemType.SHULKER_BOX );
        itemShulkerBox.setColor( this.getColor() );
        BlockEntityShulkerBox blockEntity = this.getBlockEntity();
        if ( blockEntity == null ) {
            return itemShulkerBox;
        }
        ShulkerBoxInventory shulkerBoxInventory = blockEntity.getShulkerBoxInventory();
        NbtMapBuilder builder = NbtMap.builder();
        List<NbtMap> itemsCompoundList = new ArrayList<>();
        for ( int slot = 0; slot < shulkerBoxInventory.getSize(); slot++ ) {
            Item item = shulkerBoxInventory.getItem( slot );

            if ( item != null && !( item.getType().equals( ItemType.AIR ) ) ) {
                NbtMapBuilder itemCompound = NbtMap.builder();
                itemCompound.putByte( "Slot", (byte) slot );
                blockEntity.fromItem( item, itemCompound );

                itemsCompoundList.add( itemCompound.build() );
            }
        }
        builder.putList( "Items", NbtType.COMPOUND, itemsCompoundList );
        itemShulkerBox.setNbt( builder.build() );

        return itemShulkerBox;
    }

    @Override
    public BlockEntityShulkerBox getBlockEntity() {
        return (BlockEntityShulkerBox) this.location.getWorld().getBlockEntity( this.location, this.location.getDimension() );
    }

    public BlockShulkerBox setColor( BlockColor color ) {
        return this.setState( "color", color.name().toLowerCase() );
    }

    public BlockColor getColor() {
        return this.stateExists( "color" ) ? BlockColor.valueOf( this.getStringState( "color" ) ) : BlockColor.WHITE;
    }
}
