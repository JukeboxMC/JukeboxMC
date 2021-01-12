package org.jukeboxmc.inventory;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAir;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.player.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Inventory {

    protected InventoryHolder holder;

    protected Item[] contents;
    protected int slotSize;

    private Set<Player> viewer = new HashSet<>();

    public Inventory( InventoryHolder holder, int slotSize ) {
        this.holder = holder;
        this.slotSize = slotSize;
        this.contents = new Item[slotSize];
        for ( int i = 0; i < slotSize; i++ ) {
            this.contents[i] = new ItemAir();
        }
    }

    public abstract void sendContents( Player player );

    public abstract void sendContents( int slot, Player player, boolean sendContents );

    public WindowType getWindowType() {
        return WindowType.INVENTORY;
    }

    public InventoryHolder getHolder() {
        return this.holder;
    }

    public void addViewer( Player player ) {
        this.sendContents( player );
        this.viewer.add( player );
    }

    public void removeViewer( Player player ) {
        this.viewer.remove( player );
    }

    public void setItem( int slot, Item item ) {
        this.setItem( slot, item, true );
    }

    public void setItem( int slot, Item item, boolean sendContents ) {
        if ( item.getAmount() <= 0 || item == ItemType.AIR.getItem() ) {
            this.contents[slot] = ItemType.AIR.getItem();
        }

        this.contents[slot] = item;
        for ( Player player : this.viewer ) {
            this.sendContents( slot, player, sendContents );
        }
    }

    public Item getItem( int slot ) {
        Item content = this.contents[slot];
        return content != null ? content : ItemType.AIR.getItem();
    }

    public boolean canAddItem( Item item ) {
        Item clone = item.clone();

        for ( Item content : this.getContents() ) {
            if ( content instanceof ItemAir ) {
                return true;
            } else if ( content.equals( clone ) && content.getAmount() <= content.getMaxAmount() ) {
                return true;
            } else {
                int removeCount = content.getMaxAmount() - content.getAmount();
                clone.setAmount( clone.getAmount() - removeCount );
            }
            if ( clone.getAmount() == 0 ) {
                return true;
            }
        }
        return false;
    }

    public boolean addItem( Item item ) {
        if ( this.canAddItem( item ) ) {
            Item clone = item.clone();

            Item[] contents = this.getContents();
            for ( int i = 0; i < contents.length; i++ ) {
                if ( contents[i].equals( clone ) && contents[i].getAmount() <= contents[i].getMaxAmount() ) {
                    if ( contents[i].getAmount() + clone.getAmount() <= contents[i].getMaxAmount() ) {
                        contents[i].setAmount( contents[i].getAmount() + clone.getAmount() );
                        clone.setAmount( 0 );
                    } else {
                        int amountToDecrease = contents[i].getMaxAmount() - contents[i].getAmount();
                        contents[i].setAmount( contents[i].getMaxAmount() );
                        clone.setAmount( clone.getAmount() - amountToDecrease );
                    }

                    this.setItem( i, contents[i] );

                    if ( clone.getAmount() == 0 ) {
                        return true;
                    }
                }
            }

            for ( int i = 0; i < contents.length; i++ ) {
                if ( contents[i] instanceof ItemAir ) {
                    this.setItem( i, clone );
                    return true;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public void removeItem( Item item ) {
        for ( int i = 0; i < this.slotSize; i++ ) {
            Item content = this.getItem( i );

            if ( content != null && content.getItemType() != ItemType.AIR ) {
                if ( content.getItemType() == item.getItemType() && content.getMeta() == item.getMeta() ) {
                    content.setAmount( content.getAmount() - item.getAmount() );
                    this.setItem( i, content );
                }
            }
        }
    }

    public void clear( int slot ) {
        this.setItem( slot, ItemType.AIR.getItem() );
    }

    public void clear() {
        for ( int i = 0; i < this.slotSize; i++ ) {
            Item item = this.getItem( i );
            if ( item != null && item.getItemType() != ItemType.AIR ) {
                this.clear( i );
            }
        }
    }

    public boolean contains( Item item ) {
        return Arrays.asList( this.contents ).contains( item ); //Iknow its not the best check TODO!
    }

    public int getSlotSize() {
        return this.slotSize;
    }

    public Item[] getContents() {
        return this.contents;
    }

}
