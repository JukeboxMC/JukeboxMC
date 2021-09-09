package jukeboxmc.entity.adventure;

import org.jukeboxmc.network.packet.AdventureSettingsPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class AdventureSettings {

    private Player player;

    //Flag
    private boolean worldImmutable = false;
    private boolean noPvP = false;
    private boolean autoJump = true;
    private boolean canFly = false;
    private boolean noClip = false;
    private boolean worldBuilder = false;
    private boolean flying = false;
    private boolean muted = false;

    //Flags2
    private boolean buildAndMine = true;
    private boolean useDoorsAndSwitches = true;
    private boolean openContainers = true;
    private boolean attackPlayers = true;
    private boolean attackMobs = true;
    private boolean operator = false;
    private boolean teleport = false;

    public AdventureSettings( Player player ) {
        this.player = player;
    }

    public AdventureSettings( int flags, int flags2 ) {
        // Flag 1
        this.worldImmutable = ( flags & AdventureType.FirstFlag.WORLD_IMMUTABLE.getId() ) != 0;
        this.noPvP = ( flags & AdventureType.FirstFlag.NO_PVP.getId() ) != 0;
        this.autoJump = ( flags & AdventureType.FirstFlag.AUTO_JUMP.getId() ) != 0;
        this.canFly = ( flags & AdventureType.FirstFlag.CAN_FLY.getId() ) != 0;
        this.noClip = ( flags & AdventureType.FirstFlag.NO_CLIP.getId() ) != 0;
        this.worldBuilder = ( flags & AdventureType.FirstFlag.WORLD_BUILDER.getId() ) != 0;
        this.flying = ( flags & AdventureType.FirstFlag.FLYING.getId() ) != 0;
        this.muted = ( flags & AdventureType.FirstFlag.MUTED.getId() ) != 0;

        // Flag 2
        this.buildAndMine = ( flags2 & AdventureType.SecondFlag.BUILD_AND_MINE.getId() ) != 0;
        this.useDoorsAndSwitches = ( flags2 & AdventureType.SecondFlag.USE_DOORS_AND_SWITCHES.getId() ) != 0;
        this.openContainers = ( flags2 & AdventureType.SecondFlag.OPEN_CONTAINERS.getId() ) != 0;
        this.attackPlayers = ( flags2 & AdventureType.SecondFlag.ATTACK_PLAYERS.getId() ) != 0;
        this.attackMobs = ( flags2 & AdventureType.SecondFlag.ATTACK_MOBS.getId() ) != 0;
        this.operator = ( flags2 & AdventureType.SecondFlag.OPERATOR.getId() ) != 0;
        this.teleport = ( flags2 & AdventureType.SecondFlag.TELEPORT.getId() ) != 0;
    }

    public void update() {
        AdventureSettingsPacket adventureSettingsPacket = new AdventureSettingsPacket();

        // Flags 1
        int flags = 0;
        if ( this.worldImmutable ) {
            flags |= AdventureType.FirstFlag.WORLD_IMMUTABLE.getId();
        }

        if ( this.noPvP ) {
            flags |= AdventureType.FirstFlag.NO_PVP.getId();
        }

        if ( this.autoJump ) {
            flags |= AdventureType.FirstFlag.AUTO_JUMP.getId();
        }

        if ( this.canFly ) {
            flags |= AdventureType.FirstFlag.CAN_FLY.getId();
        }

        if ( this.noClip ) {
            flags |= AdventureType.FirstFlag.NO_CLIP.getId();
        }

        if ( this.worldBuilder ) {
            flags |= AdventureType.FirstFlag.WORLD_BUILDER.getId();
        }

        if ( this.flying ) {
            flags |= AdventureType.FirstFlag.FLYING.getId();
        }

        if ( this.muted ) {
            flags |= AdventureType.FirstFlag.MUTED.getId();
        }

        // Flags 2
        int flags2 = 0;
        if ( this.buildAndMine ) {
            flags2 |= AdventureType.SecondFlag.BUILD_AND_MINE.getId();
        }

        if ( this.useDoorsAndSwitches ) {
            flags2 |= AdventureType.SecondFlag.USE_DOORS_AND_SWITCHES.getId();
        }

        if ( this.openContainers ) {
            flags2 |= AdventureType.SecondFlag.OPEN_CONTAINERS.getId();
        }

        if ( this.attackPlayers ) {
            flags2 |= AdventureType.SecondFlag.ATTACK_PLAYERS.getId();
        }

        if ( this.attackMobs ) {
            flags2 |= AdventureType.SecondFlag.ATTACK_MOBS.getId();
        }

        if ( this.operator ) {
            flags2 |= AdventureType.SecondFlag.OPERATOR.getId();
        }

        if ( this.teleport ) {
            flags2 |= AdventureType.SecondFlag.TELEPORT.getId();
        }

        adventureSettingsPacket.setFlags( flags );
        adventureSettingsPacket.setFlags2( flags2 );
        adventureSettingsPacket.setCommandPermission( 0 );
        adventureSettingsPacket.setPlayerPermission( 2 ); //(Operator) TODO PlayerPermission
        adventureSettingsPacket.setEntityId( this.player.getEntityId() );
        this.player.getPlayerConnection().sendPacket( adventureSettingsPacket );
    }

    public boolean isWorldImmutable() {
        return this.worldImmutable;
    }

    public void setWorldImmutable( boolean worldImmutable ) {
        this.worldImmutable = worldImmutable;
    }

    public boolean isNoPvP() {
        return this.noPvP;
    }

    public void setNoPvP( boolean noPvP ) {
        this.noPvP = noPvP;
    }

    public boolean isAutoJump() {
        return this.autoJump;
    }

    public void setAutoJump( boolean autoJump ) {
        this.autoJump = autoJump;
    }

    public boolean isCanFly() {
        return this.canFly;
    }

    public void setCanFly( boolean canFly ) {
        this.canFly = canFly;
    }

    public boolean isNoClip() {
        return this.noClip;
    }

    public void setNoClip( boolean noClip ) {
        this.noClip = noClip;
    }

    public boolean isWorldBuilder() {
        return this.worldBuilder;
    }

    public void setWorldBuilder( boolean worldBuilder ) {
        this.worldBuilder = worldBuilder;
    }

    public boolean isFlying() {
        return this.flying;
    }

    public void setFlying( boolean flying ) {
        this.flying = flying;
    }

    public boolean isMuted() {
        return this.muted;
    }

    public void setMuted( boolean muted ) {
        this.muted = muted;
    }

    public boolean isBuildAndMine() {
        return this.buildAndMine;
    }

    public void setBuildAndMine( boolean buildAndMine ) {
        this.buildAndMine = buildAndMine;
    }

    public boolean isUseDoorsAndSwitches() {
        return this.useDoorsAndSwitches;
    }

    public void setUseDoorsAndSwitches( boolean useDoorsAndSwitches ) {
        this.useDoorsAndSwitches = useDoorsAndSwitches;
    }

    public boolean isOpenContainers() {
        return this.openContainers;
    }

    public void setOpenContainers( boolean openContainers ) {
        this.openContainers = openContainers;
    }

    public boolean isAttackPlayers() {
        return this.attackPlayers;
    }

    public void setAttackPlayers( boolean attackPlayers ) {
        this.attackPlayers = attackPlayers;
    }

    public boolean isAttackMobs() {
        return this.attackMobs;
    }

    public void setAttackMobs( boolean attackMobs ) {
        this.attackMobs = attackMobs;
    }

    public boolean isOperator() {
        return this.operator;
    }

    public void setOperator( boolean operator ) {
        this.operator = operator;
    }

    public boolean isTeleport() {
        return this.teleport;
    }

    public void setTeleport( boolean teleport ) {
        this.teleport = teleport;
    }
}
