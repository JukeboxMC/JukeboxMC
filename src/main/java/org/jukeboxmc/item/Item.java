package org.jukeboxmc.item;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtMapBuilder;
import com.nukkitx.nbt.NbtType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
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
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.ItemPalette;
import org.jukeboxmc.world.Sound;

import java.util.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Item implements Cloneable {

    protected String identifier;
    protected int runtimeId;
    protected int blockRuntimeId;

    protected int amount;
    protected int meta;
    protected int durability;
    protected String displayname;
    protected List<String> lore;
    protected NbtMap nbt;

    protected Map<EnchantmentType, Enchantment> enchantments;
    protected List<String> canPlaceOn;
    protected List<String> canDestroy;

    public Item( String identifier ) {
        this( identifier, 0, 0, NbtMap.EMPTY );
    }

    public Item( String identifier, int meta, int blockRuntimeId, NbtMap nbt ) {
        this.identifier = identifier;
        this.runtimeId = ItemPalette.getRuntimeId( this.identifier );
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

    public static Item fromItemData( ItemData itemData ) {
        if ( itemData.getId() == 0 ) {
            return new ItemAir();
        }
        Item item = ItemType.getItemFormNetwork( itemData.getId(), itemData.getDamage() );
        item.setMeta( itemData.getDamage() );
        item.setAmount( itemData.getCount() );
        item.setNBT( itemData.getTag() == null ? NbtMap.EMPTY : itemData.getTag() );
        item.setBlockRuntimeId( itemData.getBlockRuntimeId() );
        item.setDurability( item.getNBT().getInt( "Damage", 0 ) );
        if ( item.getNBT().containsKey( "display" ) ) {
            NbtMap compound = item.getNBT().getCompound( "display" );
            if ( compound.containsKey( "Name" ) ) {
                item.setDisplayname( compound.getString( "Name" ) );
            }
            if ( compound.containsKey( "Lore" ) ) {
                item.setLore( compound.getList( "Lore", NbtType.STRING ) );
            }
        }
        return item;
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

    public ItemType getType() {
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
        return this.runtimeId;
    }

    public int getBlockRuntimeId() {
        return this.blockRuntimeId;
    }

    public Item setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        return this;
    }

    public Item setDisplayname( String displayname ) {
        this.displayname = displayname;
        return this;
    }

    public Item setLore( List<String> lore ) {
        this.lore = lore;
        return this;
    }

    public String getDisplayname() {
        return this.displayname;
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

    public NbtMap getNBT() {
        return this.nbt;
    }

    public Item setNBT( NbtMap nbt ) {
        this.nbt = nbt;
        return this;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj instanceof Item item ) {
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
            clone.displayname = this.displayname;
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
                ", runtimeId=" + runtimeId +
                ", blockRuntimeId=" + blockRuntimeId +
                ", amount=" + amount +
                ", meta=" + meta +
                ", durability=" + durability +
                ", displayname='" + displayname + '\'' +
                ", lore=" + lore +
                ", nbt=" + nbt +
                ", enchantments=" + enchantments +
                '}';
    }

    public ItemData toNetwork() {
        return ItemData.builder().
                netId( this.runtimeId ).
                id( ItemPalette.getRuntimeId( this.identifier ) ).
                blockRuntimeId( this.blockRuntimeId ).
                damage( this.meta ).
                count( this.amount ).
                tag( this.toNbt() ).
                canPlace( this.canPlaceOn.toArray( new String[0] ) ).
                canBreak( this.canDestroy.toArray( new String[0] ) ).
                build();
    }

    public ItemData toNetwork( int networkId ) {
        return ItemData.builder().
                netId( networkId ).
                id( ItemPalette.getRuntimeId( this.identifier ) ).
                blockRuntimeId( this.blockRuntimeId ).
                damage( this.meta ).
                count( this.amount ).
                tag( this.toCreativeNbt() ).
                canPlace( this.canPlaceOn.toArray( new String[0] ) ).
                canBreak( this.canDestroy.toArray( new String[0] ) ).
                build();
    }

    public NbtMap toNbt() {
        return this.toNbt(false);
    }

    public NbtMap toCreativeNbt() {
        NbtMapBuilder nbtBuilder = this.nbt == null ? NbtMap.builder() : this.nbt.toBuilder();
        NbtMapBuilder displayBuilder = NbtMap.builder();
        if ( this.displayname != null ) {
            displayBuilder.putString( "Name", this.displayname );
        }
        if ( !this.lore.isEmpty() ) {
            displayBuilder.putList( "Lore", NbtType.STRING, this.lore );
        }
        if ( !displayBuilder.isEmpty() ) {
            nbtBuilder.putCompound( "display", displayBuilder.build() );
        }

        if ( !this.enchantments.isEmpty() ) {
            List<NbtMap> enchantmentNBT = new ArrayList<>();
            for ( Enchantment enchantment : this.enchantments.values() ) {
                enchantmentNBT.add( NbtMap.builder()
                        .putShort( "id", enchantment.getId() )
                        .putShort( "lvl", enchantment.getLevel() )
                        .build()
                );
            }
            nbtBuilder.putList( "ench", NbtType.COMPOUND, enchantmentNBT );
        }

        return nbtBuilder.isEmpty() ? null : nbtBuilder.build();
    }

    public NbtMap toNbt( boolean withIdentifier ) {
        NbtMapBuilder nbtBuilder = this.nbt == null ? NbtMap.builder() : this.nbt.toBuilder();

        if ( withIdentifier ) {
            nbtBuilder.put( "Name", this.identifier );
        }
        nbtBuilder.putByte( "Count", (byte) this.amount );
        nbtBuilder.putInt( "Damage", this.durability );
        nbtBuilder.putByte( "WasPickedUp", (byte) 0 );

        NbtMapBuilder displayBuilder = NbtMap.builder();
        if ( this.displayname != null ) {
            displayBuilder.putString( "Name", this.displayname );
        }
        if ( !this.lore.isEmpty() ) {
            displayBuilder.putList( "Lore", NbtType.STRING, this.lore );
        }
        if ( !displayBuilder.isEmpty() ) {
            nbtBuilder.putCompound( "display", displayBuilder.build() );
        }

        if ( !this.enchantments.isEmpty() ) {
            List<NbtMap> enchantmentNBT = new ArrayList<>();
            for ( Enchantment enchantment : this.enchantments.values() ) {
                enchantmentNBT.add( NbtMap.builder()
                        .putShort( "id", enchantment.getId() )
                        .putShort( "lvl", enchantment.getLevel() )
                        .build()
                );
            }
            nbtBuilder.putList( "ench", NbtType.COMPOUND, enchantmentNBT );
        }

        return nbtBuilder.isEmpty() ? null : nbtBuilder.build();
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
        if ( this instanceof Durability value ) {
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

}
