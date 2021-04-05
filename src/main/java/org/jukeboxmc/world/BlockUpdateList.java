package org.jukeboxmc.world;

import org.jukeboxmc.math.BlockPosition;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author geNAZt
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockUpdateList {

    private Element element;

    public synchronized void addElement( long timeValue, BlockPosition blockPosition ) {
        if ( this.element == null ) {
            this.element = new Element( timeValue, null, new LinkedList<BlockPosition>() {{
                add( blockPosition );
            }} );
        } else {
            Element element = this.element;
            Element previousElement = null;

            while ( element != null && element.getTimeValue() < timeValue ) {
                previousElement = element;
                element = element.getNextElement();
            }

            if ( element == null ) {
                previousElement.setNextElement( new Element( timeValue, null, new LinkedList<BlockPosition>() {{
                    add( blockPosition );
                }} ) );
            } else {
                if ( element.getTimeValue() != timeValue ) {
                    Element nextElement = new Element( timeValue, element, new LinkedList<BlockPosition>() {{
                        add( blockPosition );
                    }} );

                    if ( previousElement != null ) {
                        previousElement.setNextElement( nextElement );
                    } else {
                        this.element = nextElement;
                    }
                } else {
                    element.getPositionQueue().add( blockPosition );
                }
            }
        }
    }

    public synchronized long getNextTaskTime() {
        if ( this.element != null ) {
            return this.element.getTimeValue();
        } else {
            return Long.MAX_VALUE;
        }
    }

    public synchronized BlockPosition getNextElement() {
        if ( this.element == null ) {
            return null;
        }

        while ( this.element != null && this.element.getPositionQueue().size() == 0 ) {
            this.element = this.element.getNextElement();
        }

        if ( this.element == null ) {
            return null;
        }

        BlockPosition blockPosition = this.element.getPositionQueue().poll();
        while ( this.element.getPositionQueue().size() == 0 ) {
            this.element = this.element.getNextElement();
            if ( this.element == null ) {
                break;
            }
        }
        return blockPosition;
    }

    public synchronized int size( long timeValue ) {
        Element element = this.element;
        if ( element == null ) {
            return 0;
        }

        do {
            if ( element.getTimeValue() == timeValue ) {
                return element.getPositionQueue().size();
            }
        } while ( ( element = element.getNextElement() ) != null );
        return 0;
    }

    public synchronized boolean contains( BlockPosition blockPosition ) {
        Element element = this.element;
        if ( element == null ) {
            return false;
        }

        do {
            if ( element.getPositionQueue().contains( blockPosition ) ) {
                return true;
            }
        } while ( ( element = element.getNextElement() ) != null );
        return false;
    }

    public static class Element {

        private long timeValue;
        private Element nextElement;
        private Queue<BlockPosition> positionQueue;

        public Element( long timeValue, Element nextElement, Queue<BlockPosition> positionQueue ) {
            this.timeValue = timeValue;
            this.nextElement = nextElement;
            this.positionQueue = positionQueue;
        }

        public long getTimeValue() {
            return this.timeValue;
        }

        public void setTimeValue( long timeValue ) {
            this.timeValue = timeValue;
        }

        public Element getNextElement() {
            return this.nextElement;
        }

        public void setNextElement( Element nextElement ) {
            this.nextElement = nextElement;
        }

        public Queue<BlockPosition> getPositionQueue() {
            return this.positionQueue;
        }

        public void setPositionQueue( Queue<BlockPosition> positionQueue ) {
            this.positionQueue = positionQueue;
        }
    }
}