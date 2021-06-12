package org.jukeboxmc.item;

import lombok.ToString;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAir;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtMapBuilder;
import org.jukeboxmc.nbt.NbtType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public class Item implements Cloneable {

    private String identifier;
    protected int blockRuntimeId;

    protected int amount;
    protected int meta;
    protected String customName;
    protected String[] lore;
    protected NbtMap nbt;

    protected List<Block> canPlaceOn;
    protected List<Block> canDestroy;

    public Item( String identifier, int meta, int blockRuntimeId, NbtMap nbt ) {
        this.identifier = identifier;
        this.blockRuntimeId = blockRuntimeId;
        this.meta = meta;
        this.amount = 1;
        this.meta = meta;
        this.nbt = nbt;
        this.canPlaceOn = new ArrayList<>();
        this.canDestroy = new ArrayList<>();
    }

    public Item( String identifier, int meta, int blockRuntimeId ) {
        this( identifier, meta, blockRuntimeId, null );
    }

    public Item( String identifier, int blockRuntimeId ) {
        this( identifier, 0, blockRuntimeId, null );
    }

    public Item( String identifier ) {
        this( identifier, 0, 0, null );
    }

    public Item( ItemType itemType, int amount, int meta, NbtMap nbt ) {
        Item item = itemType.getItem();
        this.blockRuntimeId = item.getBlockRuntimeId();
        this.meta = meta;
        this.amount = amount;
        this.nbt = nbt;
        this.canPlaceOn = new ArrayList<>();
        this.canDestroy = new ArrayList<>();
    }

    public Item( ItemType itemType, int amount, int meta ) {
        this( itemType, amount, meta, null );
    }

    public Item( ItemType itemType, int amount ) {
        this( itemType, amount, 0, null );
    }

    public Item( ItemType itemType ) {
        this( itemType, 1, 0, null );
    }

    public ItemType getItemType() {
        return ItemType.AIR;
    }

    public int getMaxAmount() {
        return 64;
    }

    public Block getBlock() {
        if ( this.blockRuntimeId != 0 ) {
            return BlockType.getBlock( this.blockRuntimeId );
        }
        return new BlockAir();
    }

    public void setCustomName( String customName ) {
        this.customName = customName;
    }

    public void setLore( String... lore ) {
        this.lore = lore;
    }

    public String getCustomName() {
        return this.customName;
    }

    public String[] getLore() {
        return this.lore;
    }

    public List<Block> getCanPlaceOn() {
        return this.canPlaceOn;
    }

    public void setCanPlaceOn( List<Block> canPlaceOn ) {
        this.canPlaceOn = canPlaceOn;
    }

    public List<Block> getCanDestroy() {
        return this.canDestroy;
    }

    public void setCanDestroy( List<Block> canDestroy ) {
        this.canDestroy = canDestroy;
    }

    public int getRuntimeId() {
        if ( !ItemType.getItemIdByName().containsKey( this.identifier ) ) {
            return -158;
        }
        return ItemType.getItemIdByName().get( this.identifier );
    }

    public int getBlockRuntimeId() {
        return this.blockRuntimeId;
    }

    public void setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
    }

    public int getAmount() {
        return this.amount;
    }

    public Item setAmount( int amount ) {
        this.amount = amount;
        return this;
    }

    public int getMeta() {
        return this.meta;
    }

    public Item setMeta( int meta ) {
        this.meta = meta;
        return this;
    }

    public NbtMap getNBT() {
        return this.nbt;
    }

    public void setNBT( NbtMap nbt ) {
        this.nbt = nbt;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj instanceof Item ) {
            Item item = (Item) obj;
            return item.getRuntimeId() == this.getRuntimeId() && item.meta == this.meta && ( item.blockRuntimeId == this.blockRuntimeId || item.blockRuntimeId == 0 || this.blockRuntimeId == 0 );
        } else {
            return false;
        }
    }

    @Override
    public Item clone() {
        try {
            Item clone = (Item) super.clone();
            clone.amount = this.amount;
            clone.meta = this.meta;
            clone.nbt = this.nbt;
            clone.customName = this.customName;
            clone.lore = this.lore;
            clone.canPlaceOn = this.canPlaceOn;
            clone.canDestroy = this.canDestroy;
            clone.blockRuntimeId = this.blockRuntimeId;
            return clone;
        } catch ( CloneNotSupportedException e ) {
            e.printStackTrace();
        }
        return this;
    }

    public void toNetwork( NbtMapBuilder builder ) {
        builder.putByte( "Count", (byte) this.amount );
        builder.putShort( "Damage", (short) this.meta );
        builder.putByte( "WasPickedUp", (byte) 0 );

        if ( this.customName != null || this.lore != null ) {
            NbtMapBuilder displayBuilder = NbtMap.builder();

            if ( this.customName != null )
                displayBuilder.putString( "Name", this.customName );
            if ( this.lore != null )
                displayBuilder.putList( "Lore", NbtType.STRING, Arrays.asList( this.lore ) );

            builder.putCompound( "display", displayBuilder.build() );
        }
    }

}
