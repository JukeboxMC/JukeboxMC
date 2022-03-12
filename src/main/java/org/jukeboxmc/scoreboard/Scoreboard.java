package org.jukeboxmc.scoreboard;

import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.RemoveObjectivePacket;
import org.jukeboxmc.network.packet.SetObjectivePacket;
import org.jukeboxmc.network.packet.SetScorePacket;
import org.jukeboxmc.player.Player;

import java.util.*;

/**
 * @author GoMint
 * @version 1.0
 */
public class Scoreboard {

    private long scoreIdCounter = 0;
    private final Long2ObjectMap<ScoreboardLine> scoreboardLines = new Long2ObjectArrayMap<>();

    private final Set<Player> viewers = new HashSet<>();

    private final Map<DisplaySlot, ScoreboardDisplay> displays = new EnumMap<>( DisplaySlot.class );

    public ScoreboardDisplay addDisplay( DisplaySlot slot, String objectiveName, String displayName ) {
        return this.addDisplay( slot, objectiveName, displayName, SortOrder.ASCENDING );
    }

    public ScoreboardDisplay addDisplay( DisplaySlot slot, String objectiveName, String displayName, SortOrder sortOrder ) {
        ScoreboardDisplay scoreboardDisplay = this.displays.get( slot );
        if( scoreboardDisplay == null ) {
            scoreboardDisplay = new ScoreboardDisplay( this, objectiveName, displayName, sortOrder, new LinkedHashMap<>() );
            this.displays.put( slot, scoreboardDisplay );

            this.broadcast( this.constructDisplayPacket( slot, scoreboardDisplay ) );
        }

        return scoreboardDisplay;
    }

    public void removeDisplay( DisplaySlot slot ) {
        ScoreboardDisplay display = this.displays.remove( slot );
        if( display != null ) {
            LongList validScoreIDs = new LongArrayList();

            Long2ObjectMap.FastEntrySet<ScoreboardLine> fastSet = (Long2ObjectMap.FastEntrySet<ScoreboardLine>) this.scoreboardLines.long2ObjectEntrySet();
            ObjectIterator<Long2ObjectMap.Entry<ScoreboardLine>> fastIterator = fastSet.fastIterator();
            while ( fastIterator.hasNext() ) {
                Long2ObjectMap.Entry<ScoreboardLine> entry = fastIterator.next();
                if( entry.getValue().objective.equals( display.getObjectiveName() ) ) {
                    validScoreIDs.add( entry.getLongKey() );
                }
            }

            for ( long scoreID : validScoreIDs ) {
                this.scoreboardLines.remove( scoreID );
            }

            this.broadcast( this.constructRemoveScores( validScoreIDs ) );
            this.broadcast( this.constructRemoveDisplayPacket( display ) );
        }
    }

    private SetObjectivePacket constructDisplayPacket( DisplaySlot slot, ScoreboardDisplay display ) {
        SetObjectivePacket packetSetObjective = new SetObjectivePacket();
        packetSetObjective.setCriteriaName( "dummy" );
        packetSetObjective.setDisplayName( display.getDisplayName() );
        packetSetObjective.setObjectiveName( display.getObjectiveName() );
        packetSetObjective.setDisplaySlot( slot.name().toLowerCase() );
        packetSetObjective.setSortOrder( display.getSortOrder().ordinal() );
        return packetSetObjective;
    }

    private void broadcast( Packet packet ) {
        for ( Player viewer : this.viewers ) {
            viewer.sendPacket( packet );
        }
    }

    long addOrUpdateLine( String line, String objective, int score ) {
        Long2ObjectMap.FastEntrySet<ScoreboardLine> fastEntrySet = (Long2ObjectMap.FastEntrySet<ScoreboardLine>) this.scoreboardLines.long2ObjectEntrySet();
        ObjectIterator<Long2ObjectMap.Entry<ScoreboardLine>> fastIterator = fastEntrySet.fastIterator();
        while ( fastIterator.hasNext() ) {
            Long2ObjectMap.Entry<ScoreboardLine> entry = fastIterator.next();
            if( entry.getValue().type == 3 && entry.getValue().line.equals( line ) && entry.getValue().objective.equals( objective ) ) {
                return entry.getLongKey();
            }
        }

        long newId = this.scoreIdCounter++;
        ScoreboardLine scoreboardLine = new ScoreboardLine( (byte) 3, 0, line, objective, score );
        this.scoreboardLines.put( newId, scoreboardLine );

        this.broadcast( this.constructSetScore( newId, scoreboardLine ) );
        return newId;
    }

    long addOrUpdateEntity( Entity entity, String objective, int score ) {
        // Check if we already have this registered
        Long2ObjectMap.FastEntrySet<ScoreboardLine> fastEntrySet = (Long2ObjectMap.FastEntrySet<ScoreboardLine>) this.scoreboardLines.long2ObjectEntrySet();
        ObjectIterator<Long2ObjectMap.Entry<ScoreboardLine>> fastIterator = fastEntrySet.fastIterator();
        while ( fastIterator.hasNext() ) {
            Long2ObjectMap.Entry<ScoreboardLine> entry = fastIterator.next();
            if( entry.getValue().entityId == entity.getEntityId() && entry.getValue().objective.equals( objective ) ) {
                return entry.getLongKey();
            }
        }

        long newId = this.scoreIdCounter++;
        ScoreboardLine scoreboardLine = new ScoreboardLine( (byte) ( ( entity instanceof Player ) ? 1 : 2 ), entity.getEntityId(), "", objective, score );
        this.scoreboardLines.put( newId, scoreboardLine );

        this.broadcast( this.constructSetScore( newId, scoreboardLine ) );

        return newId;
    }

    private SetScorePacket constructSetScore( long newId, ScoreboardLine line ) {
        SetScorePacket setScorePacket = new SetScorePacket();
        setScorePacket.setType( (byte) 0 );

        setScorePacket.setEntries( new ArrayList<>() {{
            add( new SetScorePacket.ScoreEntry( newId, line.objective, line.score, line.type, line.line, line.entityId ) );
        }} );

        return setScorePacket;
    }

    private SetScorePacket constructSetScore() {
        SetScorePacket setScorePacket = new SetScorePacket();
        setScorePacket.setType( (byte) 0 );

        List<SetScorePacket.ScoreEntry> entries = new ArrayList<>();
        Long2ObjectMap.FastEntrySet<ScoreboardLine> fastEntrySet = (Long2ObjectMap.FastEntrySet<ScoreboardLine>) this.scoreboardLines.long2ObjectEntrySet();
        ObjectIterator<Long2ObjectMap.Entry<ScoreboardLine>> fastIterator = fastEntrySet.fastIterator();
        while ( fastIterator.hasNext() ) {
            Long2ObjectMap.Entry<ScoreboardLine> entry = fastIterator.next();
            entries.add( new SetScorePacket.ScoreEntry( entry.getLongKey(), entry.getValue().objective, entry.getValue().score, entry.getValue().type, entry.getValue().line, entry.getValue().entityId ) );
        }

        setScorePacket.setEntries( entries );
        return setScorePacket;
    }

    public void showFor( Player player ) {
        if( this.viewers.add( player ) ) {
            for ( Map.Entry<DisplaySlot, ScoreboardDisplay> entry : this.displays.entrySet() ) {
                player.sendPacket( this.constructDisplayPacket( entry.getKey(), entry.getValue() ) );
            }
            player.sendPacket( this.constructSetScore() );
        }
    }

    public void hideFor( Player player ) {
        if( this.viewers.remove( player ) ) {
            LongList validScoreIDs = new LongArrayList();

            Long2ObjectMap.FastEntrySet<ScoreboardLine> fastSet = (Long2ObjectMap.FastEntrySet<ScoreboardLine>) this.scoreboardLines.long2ObjectEntrySet();
            ObjectIterator<Long2ObjectMap.Entry<ScoreboardLine>> fastIterator = fastSet.fastIterator();
            while ( fastIterator.hasNext() ) {
                validScoreIDs.add( fastIterator.next().getLongKey() );
            }

            player.sendPacket( this.constructRemoveScores( validScoreIDs ) );

            for ( Map.Entry<DisplaySlot, ScoreboardDisplay> entry : this.displays.entrySet() ) {
                player.sendPacket( this.constructRemoveDisplayPacket( entry.getValue() ) );
            }
        }
    }

    private SetScorePacket constructRemoveScores( LongList scoreIDs ) {
        SetScorePacket setScorePacket = new SetScorePacket();
        setScorePacket.setType( (byte) 1 );

        List<SetScorePacket.ScoreEntry> entries = new ArrayList<>();
        for ( long scoreID : scoreIDs ) {
            entries.add( new SetScorePacket.ScoreEntry( scoreID, "", 0 ) );
        }

        setScorePacket.setEntries( entries );
        return setScorePacket;
    }

    private RemoveObjectivePacket constructRemoveDisplayPacket( ScoreboardDisplay display ) {
        RemoveObjectivePacket removeObjectivePacket = new RemoveObjectivePacket();
        removeObjectivePacket.setObjectiveName( display.getObjectiveName() );
        return removeObjectivePacket;
    }

    public void updateScore( long scoreId, int score ) {
        ScoreboardLine line = this.scoreboardLines.get( scoreId );
        if( line != null ) {
            line.setScore( score );

            this.broadcast( this.constructSetScore( scoreId, line ) );
        }
    }

    public void removeScoreEntry( long scoreId ) {
        ScoreboardLine line = this.scoreboardLines.remove( scoreId );
        if( line != null ) {
            this.broadcast( this.constructRemoveScores( scoreId ) );
        }
    }

    private SetScorePacket constructRemoveScores( long scoreId ) {
        SetScorePacket setScorePacket = new SetScorePacket();
        setScorePacket.setType( (byte) 1 );
        setScorePacket.setEntries( new ArrayList<>() {{
            add( new SetScorePacket.ScoreEntry( scoreId, "", 0 ) );
        }} );
        return setScorePacket;
    }

    public int getScore( long scoreId ) {
        ScoreboardLine line = this.scoreboardLines.remove( scoreId );
        if( line != null ) {
            return line.getScore();
        }

        return 0;
    }

    @Data
    @AllArgsConstructor
    private static class ScoreboardLine {
        private final byte type;
        private final long entityId;
        private final String line;
        private final String objective;

        private int score;
    }
}
