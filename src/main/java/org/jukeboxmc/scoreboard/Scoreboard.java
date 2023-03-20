package org.jukeboxmc.scoreboard;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.ScoreInfo;
import com.nukkitx.protocol.bedrock.packet.RemoveObjectivePacket;
import com.nukkitx.protocol.bedrock.packet.SetDisplayObjectivePacket;
import com.nukkitx.protocol.bedrock.packet.SetScorePacket;
import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.entity.Entity;
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

    public ScoreboardDisplay addDisplay(@NotNull DisplaySlot slot, String objectiveName, String displayName ) {
        return this.addDisplay( slot, objectiveName, displayName, SortOrder.ASCENDING );
    }

    public @NotNull ScoreboardDisplay addDisplay(@NotNull DisplaySlot slot, String objectiveName, String displayName, SortOrder sortOrder ) {
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

    private @NotNull SetDisplayObjectivePacket constructDisplayPacket(@NotNull DisplaySlot slot, @NotNull ScoreboardDisplay display ) {
        SetDisplayObjectivePacket packetSetObjective = new SetDisplayObjectivePacket();
        packetSetObjective.setCriteria( "dummy" );
        packetSetObjective.setDisplayName( display.getDisplayName() );
        packetSetObjective.setObjectiveId( display.getObjectiveName() );
        packetSetObjective.setDisplaySlot( slot.name().toLowerCase() );
        packetSetObjective.setSortOrder( display.getSortOrder().ordinal() );
        return packetSetObjective;
    }

    private void broadcast( BedrockPacket packet ) {
        for ( Player viewer : this.viewers ) {
            viewer.getPlayerConnection().sendPacket( packet );
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

    long addOrUpdateEntity(@NotNull Entity entity, String objective, int score ) {
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

    private @NotNull SetScorePacket constructSetScore(long newId, @NotNull ScoreboardLine line ) {
        SetScorePacket setScorePacket = new SetScorePacket();
        setScorePacket.setAction( SetScorePacket.Action.SET );

        setScorePacket.getInfos().add( new ScoreInfo( newId, line.objective, line.score, line.line ) );
        return setScorePacket;
    }

    private @NotNull SetScorePacket constructSetScore() {
        SetScorePacket setScorePacket = new SetScorePacket();
        setScorePacket.setAction( SetScorePacket.Action.SET );

        List<ScoreInfo> entries = new ArrayList<>();
        Long2ObjectMap.FastEntrySet<ScoreboardLine> fastEntrySet = (Long2ObjectMap.FastEntrySet<ScoreboardLine>) this.scoreboardLines.long2ObjectEntrySet();
        ObjectIterator<Long2ObjectMap.Entry<ScoreboardLine>> fastIterator = fastEntrySet.fastIterator();
        while ( fastIterator.hasNext() ) {
            Long2ObjectMap.Entry<ScoreboardLine> entry = fastIterator.next();
            entries.add( new ScoreInfo( entry.getLongKey(), entry.getValue().objective, entry.getValue().score, entry.getValue().line ) );
        }

        setScorePacket.getInfos().addAll( entries );
        return setScorePacket;
    }

    public void showFor(@NotNull Player player ) {
        if( this.viewers.add( player ) ) {
            for ( Map.Entry<DisplaySlot, ScoreboardDisplay> entry : this.displays.entrySet() ) {
                player.getPlayerConnection().sendPacket( this.constructDisplayPacket( entry.getKey(), entry.getValue() ) );
            }
            player.getPlayerConnection().sendPacket( this.constructSetScore() );
        }
    }

    public void hideFor(@NotNull Player player ) {
        if( this.viewers.remove( player ) ) {
            LongList validScoreIDs = new LongArrayList();

            Long2ObjectMap.FastEntrySet<ScoreboardLine> fastSet = (Long2ObjectMap.FastEntrySet<ScoreboardLine>) this.scoreboardLines.long2ObjectEntrySet();
            ObjectIterator<Long2ObjectMap.Entry<ScoreboardLine>> fastIterator = fastSet.fastIterator();
            while ( fastIterator.hasNext() ) {
                validScoreIDs.add( fastIterator.next().getLongKey() );
            }

            player.getPlayerConnection().sendPacket( this.constructRemoveScores( validScoreIDs ) );

            for ( Map.Entry<DisplaySlot, ScoreboardDisplay> entry : this.displays.entrySet() ) {
                player.getPlayerConnection().sendPacket( this.constructRemoveDisplayPacket( entry.getValue() ) );
            }
        }
    }

    private @NotNull SetScorePacket constructRemoveScores(@NotNull LongList scoreIDs ) {
        SetScorePacket setScorePacket = new SetScorePacket();
        setScorePacket.setAction( SetScorePacket.Action.REMOVE );

        List<ScoreInfo> entries = new ArrayList<>();
        for ( long scoreID : scoreIDs ) {
            entries.add( new ScoreInfo( scoreID, "", 0 ) );
        }

        setScorePacket.getInfos().addAll( entries );
        return setScorePacket;
    }

    private @NotNull RemoveObjectivePacket constructRemoveDisplayPacket(@NotNull ScoreboardDisplay display ) {
        RemoveObjectivePacket removeObjectivePacket = new RemoveObjectivePacket();
        removeObjectivePacket.setObjectiveId( display.getObjectiveName() );
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

    private @NotNull SetScorePacket constructRemoveScores(long scoreId ) {
        SetScorePacket setScorePacket = new SetScorePacket();
        setScorePacket.setAction( SetScorePacket.Action.REMOVE );
        setScorePacket.getInfos().add( new ScoreInfo( scoreId, "", 0 ) );
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
        private final @NotNull String line;
        private final @NotNull String objective;

        private int score;
    }
}
