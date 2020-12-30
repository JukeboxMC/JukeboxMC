package org.jukeboxmc.item;

import lombok.ToString;
import org.jukeboxmc.block.Block;
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
public abstract class Item implements Cloneable {

    private String identifer;
    private int runtimeId;

    protected int slot = -1;

    protected int amount;
    protected int meta;
    protected String customName;
    protected String[] lore;
    protected NbtMap nbt;

    protected List<Block> canPlaceOn;
    protected List<Block> canDestroy;

    public Item( String identifer, int runtimeId ) {
        this( identifer, runtimeId, 0, null );
    }

    public Item( String identifer, int runtimeId, int meta, NbtMap nbt ) {
        this.identifer = identifer;
        this.runtimeId = runtimeId;
        this.amount = 1;
        this.meta = meta;
        this.nbt = nbt;
        this.canPlaceOn = new ArrayList<>();
        this.canDestroy = new ArrayList<>();
    }

    public ItemType getItemType() {
        for ( ItemType value : ItemType.values() ) {
            if ( value.getItemClass() == this.getClass() ) {
                return value;
            }
        }
        return ItemType.AIR;
    }

    public int getMaxAmount() {
        return 64;
    }

    public Block getBlock() {
        return BlockType.AIR.getBlock();
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
        return this.runtimeId;
    }

    public String getIdentifer() {
        return this.identifer;
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

    public void setMeta( int meta ) {
        this.meta = meta;
    }

    public NbtMap getNBT() {
        return this.nbt;
    }

    public void setNBT( NbtMap nbt ) {
        this.nbt = nbt;
    }

    public int getSlot() {
        return this.slot;
    }

    public void setSlot( int slot ) {
        this.slot = slot;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Item clone() {
        try {
            Item clone = (Item) super.clone();
            clone.runtimeId = this.runtimeId;
            clone.identifer = this.identifer;
            clone.amount = this.amount;
            clone.meta = this.meta;
            clone.nbt = this.nbt;
            clone.slot = this.slot;
            clone.customName = this.customName;
            clone.lore = this.lore;
            clone.canPlaceOn = this.canPlaceOn;
            clone.canDestroy = this.canDestroy;
            return clone;
        } catch ( CloneNotSupportedException e ) {
            e.printStackTrace();
        }
        return this;
    }

    public void toNetwork( NbtMapBuilder builder ) {
        builder.putByte( "Count", (byte) this.amount );
        builder.putShort( "Damage", (short) this.meta );
        builder.putString( "Name", this.identifer );
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
