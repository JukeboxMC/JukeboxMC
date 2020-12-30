package org.jukeboxmc.inventory;

import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAir;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.player.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Inventory {

    protected Entity holder;

    protected Item[] contents;
    protected int slots;

    private Set<Player> viewer = new HashSet<>();

    public Inventory( Entity holder, int slots ) {
        this.holder = holder;
        this.slots = slots;
        this.contents = new Item[slots];
    }

    public abstract WindowType getWindowType();

    public abstract void sendContents( Player player );

    public abstract void sendContents( int slot, Player player, boolean sendContents );

    public Entity getHolder() {
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
            item.setSlot( -1 );
        } else {
            item.setSlot( slot );
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

    public boolean addItem( Item item ) { //Does not work

        return true;
    }

    public void removeItem( Item item ) {
        for ( int i = 0; i < this.slots; i++ ) {
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
        for ( int i = 0; i < this.slots; i++ ) {
            Item item = this.getItem( i );
            if ( item != null && item.getItemType() != ItemType.AIR ) {
                this.clear( i );
            }
        }
    }

    public boolean contains( Item item ) {
        return Arrays.asList( this.contents ).contains( item ); //Iknow its not the best check TODO!
    }

    public int getSlots() {
        return this.slots;
    }

    public Item[] getContents() {
        return this.contents;
    }
}
