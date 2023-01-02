package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.player.Player;

import java.util.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Inventory {

    protected final Set<Player> viewer;
    protected InventoryHolder holder;
    protected int size;
    protected Item[] content;
    protected long holderId = -1;

    public Inventory( InventoryHolder holder, int size ) {
        this.holder = holder;
        this.size = size;
        this.viewer = new LinkedHashSet<>();
        if ( holder instanceof Player player ) {
            this.holderId = player.getEntityId();
        }
        this.content = new Item[size];
        Arrays.fill( this.content, Item.create( ItemType.AIR ) );
    }

    public Inventory( InventoryHolder holder, int holderId, int size ) {
        this.holder = holder;
        this.size = size;
        this.viewer = new LinkedHashSet<>();
        this.holderId = holderId;
        this.content = new Item[size];
        Arrays.fill( this.content, Item.create( ItemType.AIR ) );
    }

    public abstract void sendContents( Player player );

    public abstract void sendContents( int slot, Player player );

    public abstract InventoryHolder getInventoryHolder();

    public abstract InventoryType getType();

    public ContainerType getWindowTypeId() {
        return ContainerType.INVENTORY;
    }

    public void addViewer( Player player ) {
        this.viewer.add( player );
    }

    public void removeViewer( Player player ) {
        this.viewer.removeIf( target -> target.getUUID().equals( player.getUUID() ) );
    }

    public Set<Player> getViewer() {
        return this.viewer;
    }

    public void setItem( int slot, Item item ) {
        this.setItem( slot, item, true );
    }

    public void setItem( int slot, Item item, boolean sendContent ) {
        if ( slot < 0 || slot >= this.size ) {
            return;
        }
        if ( item.getAmount() <= 0 || item.getType().equals( ItemType.AIR ) ) {
            this.content[slot] = Item.create( ItemType.AIR );
        } else {
            this.content[slot] = item;
        }

        if ( sendContent ) {
            for ( Player player : this.viewer ) {
                this.sendContents( slot, player );
            }
        }
    }

    public Item getItem( int slot ) {
        Item content = this.content[slot];
        return content != null ? content : Item.create( ItemType.AIR );
    }

    public boolean canAddItem( Item item ) {
        int amount = item.getAmount();

        for ( Item content : this.getContents() ) {
            if ( content == null || content.getType().equals( ItemType.AIR ) ) {
                return true;
            } else if ( content.equals( item ) ) {
                if ( content.getAmount() + item.getAmount() <= content.getMaxStackSize() ) {
                    return true;
                } else {
                    amount -= content.getMaxStackSize() - content.getAmount();
                    if ( amount <= 0 ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean addItem( Item item ) {
        return this.addItem( item, true );
    }

    public boolean addItem( Item item, boolean sendContents ) {
        if ( this.canAddItem( item ) ) {
            Item clone = item.clone();

            Item[] contents = this.getContents();
            for ( int i = 0; i < contents.length; i++ ) {
                if ( contents[i].equals( clone ) && contents[i].getAmount() <= contents[i].getMaxStackSize() ) {
                    if ( contents[i].getAmount() + clone.getAmount() <= contents[i].getMaxStackSize() ) {
                        contents[i].setAmount( contents[i].getAmount() + clone.getAmount() );
                        clone.setAmount( 0 );
                    } else {
                        int amountToDecrease = contents[i].getMaxStackSize() - contents[i].getAmount();
                        contents[i].setAmount( contents[i].getMaxStackSize() );
                        clone.setAmount( clone.getAmount() - amountToDecrease );
                    }

                    this.setItem( i, contents[i], sendContents );

                    if ( clone.getAmount() == 0 ) {
                        return true;
                    }
                }
            }

            for ( int i = 0; i < contents.length; i++ ) {
                if ( contents[i].getType().equals( ItemType.AIR ) ) {
                    this.setItem( i, clone, sendContents );
                    return true;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean addItem( Item item, int slot ) {
        if ( this.canAddItem( item ) ) {
            Item clone = item.clone();

            if ( this.content[slot].equals( clone ) && this.content[slot].getAmount() <= this.content[slot].getMaxStackSize() ) {
                if ( this.content[slot].getAmount() + clone.getAmount() <= this.content[slot].getMaxStackSize() ) {
                    this.content[slot].setAmount( this.content[slot].getAmount() + clone.getAmount() );
                    clone.setAmount( 0 );
                } else {
                    int amountToDecrease = this.content[slot].getMaxStackSize() - this.content[slot].getAmount();
                    this.content[slot].setAmount( this.content[slot].getMaxStackSize() );
                    clone.setAmount( clone.getAmount() - amountToDecrease );
                }

                this.setItem( slot, this.content[slot] );

                if ( clone.getAmount() == 0 ) {
                    return true;
                }
            }

            if ( this.content[slot].getType().equals( ItemType.AIR ) ) {
                this.setItem( slot, clone );
                return true;
            }
        } else {
            return false;
        }
        return true;
    }

    public void removeItem( int slot, Item item ) {
        Item content = this.getItem( slot );

        if ( content != null && content.getType() != ItemType.AIR ) {
            if ( content.getType() == item.getType() && content.getMeta() == item.getMeta() ) {
                content.setAmount( content.getAmount() - item.getAmount() );
                if ( content.getAmount() <= 0 ) {
                    this.setItem( slot, Item.create( ItemType.AIR ) );
                } else {
                    this.setItem( slot, content );
                }
            }
        }
    }

    public void removeItem( ItemType itemType, int meta, int amount ) {
        for ( int i = 0; i < this.size; i++ ) {
            Item content = this.getItem( i );
            if ( content != null && content.getType() != ItemType.AIR ) {
                if ( content.getType().equals( itemType ) && content.getMeta() == meta ) {
                    content.setAmount( content.getAmount() - amount );
                    if ( content.getAmount() <= 0 ) {
                        this.setItem( i, Item.create( ItemType.AIR ) );
                    } else {
                        this.setItem( i, content );
                    }
                    break;
                }
            }
        }
    }

    public void removeItem( ItemType itemType, int amount ) {
        this.removeItem( itemType, 0, amount );
    }

    public void clear( int slot ) {
        this.setItem( slot, Item.create( ItemType.AIR ) );
    }

    public void clear() {
        for ( int i = 0; i < this.size; i++ ) {
            Item item = this.getItem( i );
            if ( item != null && item.getType() != ItemType.AIR ) {
                this.clear( i );
            }
        }
    }

    public boolean contains( Item item ) {
        return Arrays.asList( this.content ).contains( item );
    }

    public int getSize() {
        return this.size;
    }

    public long getHolderId() {
        return this.holderId;
    }

    public Item[] getContents() {
        return this.content;
    }

    public List<ItemData> getItemDataContents() {
        List<ItemData> itemDataList = new ArrayList<>();
        for ( Item content : this.getContents() ) {
            itemDataList.add( content.toItemData() );
        }
        return itemDataList;
    }

}
