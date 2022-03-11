package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAir;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.enchantment.Enchantment;
import org.jukeboxmc.item.enchantment.EnchantmentType;
import org.jukeboxmc.item.type.Durability;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtMapBuilder;
import org.jukeboxmc.nbt.NbtType;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.utils.BedrockResourceLoader;
import org.jukeboxmc.world.Sound;

import java.util.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Item implements Cloneable {

    protected String identifier;
    protected int blockRuntimeId;

    protected int amount;
    protected int meta;
    protected int durability;
    protected String customName;
    protected List<String> lore;
    protected NbtMap nbt;

    protected Map<EnchantmentType, Enchantment> enchantments;
    protected List<Block> canPlaceOn;
    protected List<Block> canDestroy;

    public Item( String identifier ) {
        this( identifier, 0, 0, NbtMap.EMPTY );
    }

    public Item( String identifier, int meta, int blockRuntimeId, NbtMap nbt ) {
        this.identifier = identifier;
        this.blockRuntimeId = blockRuntimeId;
        this.meta = meta;
        this.durability = 0;
        this.amount = 1;
        this.lore = Collections.emptyList();
        this.nbt = nbt;
        this.enchantments = new HashMap<>();
        this.canPlaceOn = new ArrayList<>();
        this.canDestroy = new ArrayList<>();
    }

    public Item( String identifier, int meta, int blockRuntimeId ) {
        this( identifier, meta, blockRuntimeId, NbtMap.EMPTY );
    }

    public Item( String identifier, int blockRuntimeId ) {
        this( identifier, 0, blockRuntimeId, NbtMap.EMPTY );
    }

    public Item( ItemType itemType, int amount, int meta, NbtMap nbt ) {
        Item item = itemType.getItem();
        this.blockRuntimeId = item.getBlockRuntimeId();
        this.meta = meta;
        this.durability = 0;
        this.amount = amount;
        this.nbt = nbt;
        this.lore = Collections.emptyList();
        this.canPlaceOn = new ArrayList<>();
        this.canDestroy = new ArrayList<>();
    }

    public Item( ItemType itemType, int amount, int meta ) {
        this( itemType, amount, meta, NbtMap.EMPTY );
    }

    public Item( ItemType itemType, int amount ) {
        this( itemType, amount, 0, NbtMap.EMPTY );
    }

    public Item( ItemType itemType ) {
        this( itemType, 1, 0, NbtMap.EMPTY );
    }

    public boolean useOnBlock( Player player, Block block, Location placeLocation ) {
        return false;
    }

    public boolean useInAir( Player player, Vector clickVector ) {
        return false;
    }

    public boolean onUse( Player player ) {
        return false;
    }

    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        return false;
    }

    public void addToHand( Player player ) {

    }

    public void removeFromHand( Player player ) {

    }

    public String getIdentifier() {
        return this.identifier;
    }

    public ItemType getItemType() {
        for ( Map.Entry<ItemType, Item> entry : ItemType.getCachedItems().entrySet() ) {
            if ( entry.getValue().getIdentifier().equalsIgnoreCase( this.identifier ) ) {
                return entry.getKey();
            }
        }
        return ItemType.AIR;
    }

    public ItemToolType getItemToolType() {
        return ItemToolType.NONE;
    }

    public ItemTierType getTierType() {
        return ItemTierType.NONE;
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

    public int getRuntimeId() {
        if ( !BedrockResourceLoader.getItemIdByName().containsKey( this.identifier ) ) {
            return -158;
        }
        return BedrockResourceLoader.getItemIdByName().get( this.identifier );
    }

    public int getBlockRuntimeId() {
        return this.blockRuntimeId;
    }

    public Item setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        return this;
    }

    public Item setCustomName( String customName ) {
        this.customName = customName;
        return this;
    }

    public Item setLore( List<String> lore ) {
        this.lore = lore;
        return this;
    }

    public String getCustomName() {
        return this.customName;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public int getAmount() {
        return this.amount;
    }

    public Item setAmount( int amount ) {
        if ( amount > this.getMaxAmount() ) {
            this.amount = this.getMaxAmount();
            return this;
        }
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

    public int getDurability() {
        return this.durability;
    }

    public Item setDurability( int durability ) {
        this.durability = durability;
        return this;
    }

    public Item decreaseAmount() {
        return this.setAmount( this.getAmount() - 1 );
    }

    public Item addEnchantment( EnchantmentType enchantmentType, int level ) {
        Enchantment enchantment = enchantmentType.getEnchantment();
        this.enchantments.put( enchantmentType, enchantment.setLevel( (short) ( level > enchantment.getMaxLevel() ? 1 : level ) ) );
        return this;
    }

    public Enchantment getEnchantment( EnchantmentType enchantmentType ) {
        return this.enchantments.get( enchantmentType );
    }

    public Collection<Enchantment> getEntchantments() {
        return this.enchantments.values();
    }

    public void updateItem( Player player, int amount ) {
        this.updateItem( player, amount, true );
    }

    public void updateItem( Player player, int amount, boolean playSound ) {
        if ( player.getGameMode().equals( GameMode.SURVIVAL ) ) {
            if ( this.calculateDurability( amount ) ) {
                player.getInventory().setItemInHand( new ItemAir() );
                if ( playSound ) {
                    player.playSound( Sound.RANDOM_BREAK, 1, 1 );
                }
            } else {
                player.getInventory().setItemInHand( this );
            }
        }
    }

    public boolean calculateDurability( int durability ) {
        if ( this instanceof Durability ) {
            this.durability += durability;
            return this.durabilityAndCheckAmount( this.durability );
        }
        return false;
    }

    private boolean durabilityAndCheckAmount( int durability ) {
        if ( this instanceof Durability ) {
            Durability value = (Durability) this;
            int maxDurability = value.getMaxDurability();
            int intdurability = durability;
            if ( intdurability >= maxDurability ) {
                if ( --this.amount <= 0 ) {
                    return true;
                }
                intdurability = 0;
            }
            this.durability = intdurability;
        }
        return false;
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

    public boolean equalsExact( Item item ) {
        return this.equals( item ) && this.amount == item.amount;
    }

    @Override
    public Item clone() {
        try {
            Item clone = (Item) super.clone();
            clone.identifier = this.identifier;
            clone.amount = this.amount;
            clone.meta = this.meta;
            clone.durability = this.durability;
            clone.nbt = this.nbt;
            clone.customName = this.customName;
            clone.lore = this.lore;
            clone.canPlaceOn = this.canPlaceOn;
            clone.canDestroy = this.canDestroy;
            clone.blockRuntimeId = this.blockRuntimeId;
            clone.enchantments = this.enchantments;
            return clone;
        } catch ( CloneNotSupportedException e ) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public String toString() {
        return "Item{" +
                "identifier='" + identifier + '\'' +
                ", blockRuntimeId=" + blockRuntimeId +
                ", amount=" + amount +
                ", meta=" + meta +
                '}';
    }

    public NbtMapBuilder toNetwork() {
        return this.toNetwork( false );
    }

    public NbtMapBuilder toNetwork( boolean withName ) {
        NbtMapBuilder builder = this.nbt == null ? NbtMap.EMPTY.toBuilder() : this.nbt.toBuilder();
        if ( withName ) {
            builder.putString( "Name", this.identifier );
        }
        builder.putByte( "Count", (byte) this.amount );
        builder.putInt( "Damage", this.durability );
        builder.putByte( "WasPickedUp", (byte) 0 );

        if ( !this.enchantments.isEmpty() ) {
            List<NbtMap> enchantmentNBT = new ArrayList<>();
            for ( Enchantment enchantment : this.enchantments.values() ) {
                enchantmentNBT.add( NbtMap.builder()
                        .putShort( "id", enchantment.getId() )
                        .putShort( "lvl", enchantment.getLevel() )
                        .build()
                );
            }
            builder.putList( "ench", NbtType.COMPOUND, enchantmentNBT );
        }

        NbtMapBuilder displayBuilder = NbtMap.builder();
        if ( this.customName != null ) {
            displayBuilder.putString( "Name", this.customName );
        }
        if ( !this.lore.isEmpty() ) {
            displayBuilder.putList( "Lore", NbtType.STRING, this.lore );
        }

        if ( this.customName != null || !this.lore.isEmpty() ) {
            builder.putCompound( "display", displayBuilder.build() );
        }
        return builder;
    }

}
