package org.jukeboxmc.block;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import lombok.ToString;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.nbt.NbtMapBuilder;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.packet.UpdateBlockPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.behavior.Waterlogable;
import org.jukeboxmc.block.data.BlockProperties;
import org.jukeboxmc.block.data.UpdateReason;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.item.EntityItem;
import org.jukeboxmc.event.block.BlockBreakEvent;
import org.jukeboxmc.item.*;
import org.jukeboxmc.item.enchantment.Enchantment;
import org.jukeboxmc.item.enchantment.EnchantmentType;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.potion.*;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.util.RuntimeBlockDefination;
import org.jukeboxmc.world.World;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString ( exclude = { "blockProperties" } )
public class Block implements Cloneable {

    public static final Object2ObjectMap<Identifier, Object2ObjectMap<NbtMap, Integer>> STATES = new Object2ObjectLinkedOpenHashMap<>();

    protected int runtimeId;
    protected Identifier identifier;
    protected NbtMap blockStates;
    protected BlockType blockType;
    protected Location location;
    protected int layer;
    protected BlockProperties blockProperties;

    public Block( Identifier identifier ) {
        this( identifier, null );
    }

    public Block( Identifier identifier, NbtMap blockStates ) {
        this.identifier = identifier;
        this.blockStates = blockStates;
        this.blockType = BlockRegistry.getBlockType( identifier );
        this.blockProperties = BlockRegistry.getBlockProperties( identifier );

        if ( !STATES.containsKey( this.identifier ) ) {
            Object2ObjectMap<NbtMap, Integer> toRuntimeId = new Object2ObjectLinkedOpenHashMap<>();
            for ( NbtMap blockMap : BlockPalette.searchBlocks( blockMap -> blockMap.getString( "name" ).toLowerCase().equals( this.identifier.getFullName() ) ) ) {
                toRuntimeId.put( blockMap.getCompound( "states" ), BlockPalette.getRuntimeId( blockMap ) );
            }
            STATES.put( this.identifier, toRuntimeId );
        }

        if ( blockStates == null ) {
            List<NbtMap> states = new LinkedList<>( STATES.get( this.identifier ).keySet() );
            blockStates = states.isEmpty() ? NbtMap.EMPTY : states.get( 0 );
        }

        this.blockStates = blockStates;
        this.runtimeId = STATES.get( this.identifier ).get( this.blockStates );
    }

    public static <T extends Block> T create( BlockType blockType ) {
        if ( BlockRegistry.blockClassExists( blockType ) ) {
            try {
                Constructor<? extends Block> constructor = BlockRegistry.getBlockClass( blockType ).getConstructor( Identifier.class );
                return (T) constructor.newInstance( BlockRegistry.getIdentifier( blockType ) );
            } catch ( Exception e ) {
                throw new RuntimeException( e );
            }
        }
        return (T) new Block( BlockRegistry.getIdentifier( blockType ) );
    }

    public static <T extends Block> T create( BlockType blockType, NbtMap blockStates ) {
        if ( BlockRegistry.blockClassExists( blockType ) ) {
            try {
                Constructor<? extends Block> constructor = BlockRegistry.getBlockClass( blockType ).getConstructor( Identifier.class, NbtMap.class );
                return (T) constructor.newInstance( BlockRegistry.getIdentifier( blockType ), blockStates );
            } catch ( Exception e ) {
                throw new RuntimeException( e );
            }
        }
        return (T) new Block( BlockRegistry.getIdentifier( blockType ), blockStates );
    }

    public static <T extends Block> T create( Identifier identifier ) {
        BlockType blockType = BlockRegistry.getBlockType( identifier );
        if ( BlockRegistry.blockClassExists( blockType ) ) {
            try {
                Constructor<? extends Block> constructor = BlockRegistry.getBlockClass( blockType ).getConstructor( Identifier.class );
                return (T) constructor.newInstance( identifier );
            } catch ( Exception e ) {
                throw new RuntimeException( e );
            }
        }
        return (T) new Block( identifier, null );
    }

    public static <T extends Block> T create( Identifier identifier, NbtMap blockStates ) {
        BlockType blockType = BlockRegistry.getBlockType( identifier );
        if ( BlockRegistry.blockClassExists( blockType ) ) {
            try {
                Constructor<? extends Block> constructor = BlockRegistry.getBlockClass( blockType ).getConstructor( Identifier.class, NbtMap.class );
                return (T) constructor.newInstance( identifier, blockStates );
            } catch ( Exception e ) {
                throw new RuntimeException( e );
            }
        }
        return (T) new Block( identifier, blockStates );
    }

    public int getRuntimeId() {
        return this.runtimeId;
    }

    public Identifier getIdentifier() {
        return this.identifier;
    }

    public NbtMap getBlockStates() {
        return this.blockStates;
    }

    public void setBlockStates( NbtMap blockStates ) {
        this.blockStates = blockStates;
    }

    public BlockType getType() {
        return this.blockType;
    }

    public World getWorld() {
        return this.location.getWorld();
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation( Location location ) {
        this.location = location;
    }

    public int getLayer() {
        return this.layer;
    }

    public void setLayer( int layer ) {
        this.layer = layer;
    }

    public boolean checkValidity() {
        return this.location != null && this.location.getWorld() != null && this.location.getWorld().getBlock( this.location.getBlockX(), this.location.getBlockY(), this.location.getBlockZ(), this.layer, this.location.getDimension() ).getRuntimeId() == this.runtimeId;
    }

    public <B extends Block> B setState( String state, Object value ) {
        return this.setState( state, value, true );
    }

    public <B extends Block> B setState( String state, Object value, boolean update ) {
        if ( !this.blockStates.containsKey( state ) ) {
            throw new AssertionError( "State " + state + " was not found in block " + this.identifier );
        }
        if ( this.blockStates.get( state ).getClass() != value.getClass() ) {
            throw new AssertionError( "State " + state + " type is not the same for value  " + value );
        }

        boolean valid = this.checkValidity();

        NbtMapBuilder nbtMapBuilder = this.blockStates.toBuilder();
        nbtMapBuilder.put( state, value );
        for ( Map.Entry<NbtMap, Integer> entry : STATES.get( this.identifier ).entrySet() ) {
            NbtMap blockMap = entry.getKey();
            if ( blockMap.equals( nbtMapBuilder ) ) {
                this.blockStates = blockMap;
            }
        }
        this.runtimeId = STATES.get( this.identifier ).get( this.blockStates );

        if ( valid && update ) {
            this.sendUpdate();
            this.location.getChunk().setBlock( this.location.getBlockX(), this.location.getBlockY(), this.location.getBlockZ(), this.layer, this );
        }
        return (B) this;
    }

    public boolean stateExists( String value ) {
        return this.blockStates.containsKey( value );
    }

    public String getStringState( String value ) {
        return this.blockStates.getString( value ).toUpperCase();
    }

    public byte getByteState( String value ) {
        return this.blockStates.getByte( value );
    }

    public boolean getBooleanState( String value ) {
        return this.blockStates.getByte( value ) == 1;
    }

    public int getIntState( String value ) {
        return this.blockStates.getInt( value );
    }

    public Block getSide( Direction direction ) {
        return this.getSide( direction, 0 );
    }

    public Block getSide( BlockFace blockFace ) {
        return this.getSide( blockFace, 0 );
    }

    public Block getSide( Direction direction, int layer ) {
        return switch ( direction ) {
            case SOUTH -> this.getRelative( new Vector( 0, 0, 1 ), layer );
            case NORTH -> this.getRelative( new Vector( 0, 0, -1 ), layer );
            case EAST -> this.getRelative( new Vector( 1, 0, 0 ), layer );
            case WEST -> this.getRelative( new Vector( -1, 0, 0 ), layer );
        };
    }

    public Block getSide( BlockFace blockFace, int layer ) {
        return switch ( blockFace ) {
            case DOWN -> this.getRelative( new Vector( 0, -1, 0 ), layer );
            case UP -> this.getRelative( new Vector( 0, 1, 0 ), layer );
            case SOUTH -> this.getRelative( new Vector( 0, 0, 1 ), layer );
            case NORTH -> this.getRelative( new Vector( 0, 0, -1 ), layer );
            case EAST -> this.getRelative( new Vector( 1, 0, 0 ), layer );
            case WEST -> this.getRelative( new Vector( -1, 0, 0 ), layer );
        };
    }

    public Block getRelative( Vector position, int layer ) {
        int x = this.location.getBlockX() + position.getBlockX();
        int y = this.location.getBlockY() + position.getBlockY();
        int z = this.location.getBlockZ() + position.getBlockZ();
        return this.location.getWorld().getBlock( x, y, z, layer, this.location.getDimension() );
    }

    public Item toItem() {
        return Item.create( this.identifier );
    }

    public AxisAlignedBB getBoundingBox() {
        return new AxisAlignedBB(
                this.location.getX(),
                this.location.getY(),
                this.location.getZ(),
                this.location.getX() + 1,
                this.location.getY() + 1,
                this.location.getZ() + 1
        );
    }

    public void breakBlock( Player player, Item item ) {
        if ( player.getGameMode().equals( GameMode.SPECTATOR ) ) {
            this.sendUpdate( player );
            return;
        }

        List<Item> itemDropList;
        if ( item.getEnchantment( EnchantmentType.SILK_TOUCH ) != null ) {
            itemDropList = Collections.singletonList( this.toItem() );
        } else {
            itemDropList = this.getDrops( item );
        }

        BlockBreakEvent blockBreakEvent = new BlockBreakEvent( player, this, itemDropList );
        Server.getInstance().getPluginManager().callEvent( blockBreakEvent );

        if ( blockBreakEvent.isCancelled() ) {
            player.getInventory().sendItemInHand();
            this.sendUpdate( player );
            return;
        }

        Location breakLocation = this.location;
        this.onBlockBreak( breakLocation );

        if ( player.getGameMode().equals( GameMode.SURVIVAL ) ) {
            if ( item instanceof Durability ) {
                item.updateItem( player, 1 );
            }

            player.exhaust( 0.025f );

            List<EntityItem> itemDrops = new ArrayList<>();
            for ( Item droppedItem : blockBreakEvent.getDrops() ) {
                if ( !droppedItem.getType().equals( ItemType.AIR ) ) {
                    itemDrops.add( player.getWorld().dropItem( droppedItem, breakLocation.clone(), null ) );
                }
            }
            if ( !itemDrops.isEmpty() ) {
                itemDrops.forEach( Entity::spawn );
            }
        }

        this.playBreakSound();
        breakLocation.getWorld().sendLevelEvent( breakLocation, LevelEvent.PARTICLE_DESTROY_BLOCK, this.runtimeId );
    }

    public void playBreakSound() {
        this.location.getWorld().playSound( this.location, SoundEvent.BREAK, this.runtimeId );
    }

    public void onBlockBreak( Vector breakPosition ) {
        this.location.getWorld().setBlock( breakPosition, Block.create( BlockType.AIR ), 0, breakPosition.getDimension() );
    }

    public void sendUpdate() {
        UpdateBlockPacket updateBlockPacket = new UpdateBlockPacket();
        updateBlockPacket.setDefinition( new RuntimeBlockDefination( this.runtimeId ) );
        updateBlockPacket.setBlockPosition( this.location.toVector3i() );
        updateBlockPacket.getFlags().addAll( UpdateBlockPacket.FLAG_ALL_PRIORITY );
        updateBlockPacket.setDataLayer( this.layer );
        this.location.getWorld().sendChunkPacket( this.location.getChunkX(), this.location.getChunkZ(), updateBlockPacket );
    }

    public void sendUpdate( Player player ) {
        if ( this.location == null ) {
            return;
        }
        UpdateBlockPacket updateBlockPacket = new UpdateBlockPacket();
        updateBlockPacket.setDefinition( new RuntimeBlockDefination( this.runtimeId ) );
        updateBlockPacket.setBlockPosition( this.location.toVector3i() );
        updateBlockPacket.getFlags().addAll( UpdateBlockPacket.FLAG_ALL_PRIORITY );
        updateBlockPacket.setDataLayer( this.layer );
        player.getPlayerConnection().sendPacket( updateBlockPacket );
    }

    public double getBreakTime( Item item, Player player ) {
        double hardness = this.getHardness();
        if ( hardness == 0 ) {
            return 0;
        }

        BlockType blockType = this.getType();
        boolean correctTool = this.correctTool0( this.getToolType(),
                item.getToolType() ) ||
                item.getToolType().equals( ToolType.SHEARS ) &&
                        ( blockType.equals( BlockType.WEB ) ||
                                blockType.equals( BlockType.LEAVES ) ||
                                blockType.equals( BlockType.LEAVES2 ) );
        boolean canBreakWithHand = this.canBreakWithHand();
        ToolType itemToolType = item.getToolType();
        TierType itemTier = item.getTierType();
        int efficiencyLoreLevel = Optional.ofNullable( item.getEnchantment( EnchantmentType.EFFICIENCY ) ).map( Enchantment::getLevel ).orElse( (short) 0 );
        int miningFatigueLevel = Optional.ofNullable( player.<MiningFatigueEffect>getEffect( EffectType.MINING_FATIGUE ) ).map( Effect::getAmplifier ).orElse( 0 );
        int hasteEffectLevel = Optional.ofNullable( player.<HasteEffect>getEffect( EffectType.HASTE ) ).map( Effect::getAmplifier ).orElse( 0 );
        int conduitPowerLevel = Optional.ofNullable( player.<ConduitPowerEffect>getEffect( EffectType.CONDUIT_POWER ) ).map( Effect::getAmplifier ).orElse( 0 );
        hasteEffectLevel += conduitPowerLevel;

        boolean insideOfWaterWithoutAquaAffinity = player.isInWater() && conduitPowerLevel <= 0 &&
                Optional.ofNullable( player.getArmorInventory().getHelmet().getEnchantment( EnchantmentType.AQUA_AFFINITY ) ).map( Enchantment::getLevel ).map( l -> l >= 1 ).orElse( false );

        boolean outOfWaterButNotOnGround = ( !player.isInWater() ) && ( !player.isOnGround() );
        return breakTime0( item, hardness, correctTool, canBreakWithHand, blockType, itemToolType, itemTier, efficiencyLoreLevel, hasteEffectLevel, miningFatigueLevel, insideOfWaterWithoutAquaAffinity, outOfWaterButNotOnGround );
    }

    private double breakTime0( Item item, double blockHardness, boolean correctTool, boolean canHarvestWithHand,
                               BlockType blockType, ToolType itemToolType, TierType itemTierType, int efficiencyLoreLevel,
                               int hasteEffectLevel, int miningFatigueLevel, boolean insideOfWaterWithoutAquaAffinity, boolean outOfWaterButNotOnGround ) {
        double baseTime;
        if ( canHarvest( item ) || canHarvestWithHand ) {
            baseTime = 1.5 * blockHardness;
        } else {
            baseTime = 5.0 * blockHardness;
        }
        double speed = 1.0 / baseTime;
        if ( correctTool ) {
            speed *= this.toolBreakTimeBonus0( itemToolType, itemTierType, blockType );
        }
        speed += this.speedBonusByEfficiencyLore0( efficiencyLoreLevel );
        speed *= this.speedRateByHasteLore0( hasteEffectLevel );
        if ( insideOfWaterWithoutAquaAffinity ) speed *= 0.2;
        if ( outOfWaterButNotOnGround ) speed *= 0.2;
        return 1.0 / speed;
    }

    private double toolBreakTimeBonus0( ToolType itemToolType, TierType itemTierType, BlockType blockType ) {
        if ( itemToolType.equals( ToolType.SWORD ) ) return blockType.equals( BlockType.WEB ) ? 15.0 : 1.0;
        if ( itemToolType.equals( ToolType.SHEARS ) ) {
            if ( blockType.isWool( blockType ) ||
                    blockType.equals( BlockType.LEAVES ) ||
                    blockType.equals( BlockType.LEAVES2 ) ) {
                return 5.0;
            } else if ( blockType.equals( BlockType.WEB ) ) {
                return 15.0;
            }
            return 1.0;
        }
        if ( itemToolType.equals( ToolType.NONE ) ) return 1.0;
        return switch ( itemTierType ) {
            case WOODEN -> 2.0;
            case STONE -> 4.0;
            case IRON -> 6.0;
            case DIAMOND -> 8.0;
            case NETHERITE -> 9.0;
            case GOLD -> 12.0;
            default -> 1.0;
        };
    }

    private double speedBonusByEfficiencyLore0( int efficiencyLoreLevel ) {
        if ( efficiencyLoreLevel == 0 ) return 0;
        return efficiencyLoreLevel * efficiencyLoreLevel + 1;
    }

    private double speedRateByHasteLore0( int hasteLoreLevel ) {
        return 1.0 + ( 0.2 * hasteLoreLevel );
    }

    public boolean canHarvest( Item item ) {
        return this.getTierType().equals( TierType.NONE ) || this.getToolType().equals( ToolType.NONE ) || this.correctTool0( this.getToolType(), item.getToolType() ) && item.getTierType().ordinal() >= this.getTierType().ordinal();
    }

    private boolean correctTool0( ToolType blockItemToolType, ToolType itemToolType ) {
        return ( blockItemToolType.equals( ToolType.SWORD ) && itemToolType.equals( ToolType.SWORD ) ) ||
                ( blockItemToolType.equals( ToolType.SHOVEL ) && itemToolType.equals( ToolType.SHOVEL ) ) ||
                ( blockItemToolType.equals( ToolType.PICKAXE ) && itemToolType.equals( ToolType.PICKAXE ) ) ||
                ( blockItemToolType.equals( ToolType.AXE ) && itemToolType.equals( ToolType.AXE ) ) ||
                ( blockItemToolType.equals( ToolType.HOE ) && itemToolType.equals( ToolType.HOE ) ) ||
                ( blockItemToolType.equals( ToolType.SHEARS ) && itemToolType.equals( ToolType.SHEARS ) ) ||
                blockItemToolType == ToolType.NONE;
    }

    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        world.setBlock( placePosition, this, 0, player.getDimension(), true );
        return true;
    }

    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        return false;
    }

    public void playPlaceSound( Vector placePosition ) {
        this.location.getWorld().playSound( placePosition, SoundEvent.PLACE, this.getRuntimeId() );
    }

    public BlockEntity getBlockEntity() {
        return null;
    }

    public boolean canBreakWithHand() {
        return this.blockProperties.isCanBreakWithHand();
    }

    public boolean isSolid() {
        return this.blockProperties.isSolid();
    }

    public boolean isTransparent() {
        return this.blockProperties.isTransparent();
    }

    public double getHardness() {
        return this.blockProperties.getHardness();
    }

    public boolean canPassThrough() {
        return this.blockProperties.isCanPassThrough();
    }

    public ToolType getToolType() {
        return this.blockProperties.getToolType();
    }

    public TierType getTierType() {
        return this.blockProperties.getTierType();
    }

    public boolean isRandomTicking() {
        return this.blockProperties.isRandomTicking();
    }

    public boolean canBeReplaced( Block block ) {
        return false;
    }

    public long onUpdate( UpdateReason updateReason ) {
        return -1;
    }

    public void enterBlock( Player player ) {
    }

    public void leaveBlock( Player player ) {
    }

    public boolean canBeFlowedInto() {
        return false;
    }

    public final boolean canWaterloggingFlowInto() {
        return this.canBeFlowedInto() || ( this instanceof Waterlogable waterlogable && waterlogable.getWaterLoggingLevel() > 1 );
    }

    public List<Item> getDrops( Item item ) {
        if ( item == null || ( this.isCorrectToolType( item ) && this.isCorrectTierType( item ) ) ) {
            return Collections.singletonList( this.toItem() );
        }
        return Collections.emptyList();
    }

    public boolean isCorrectToolType( Item item ) {
        return item.getToolType().ordinal() >= this.getToolType().ordinal();
    }

    public boolean isCorrectTierType( Item item ) {
        return item.getTierType().ordinal() >= this.getTierType().ordinal();
    }

    public boolean isWater() {
        return this.getType().equals( BlockType.WATER ) || this.getType().equals( BlockType.FLOWING_WATER );
    }

    public boolean isLava() {
        return this.getType().equals( BlockType.LAVA ) || this.getType().equals( BlockType.FLOWING_LAVA );
    }

    @Override
    public Block clone() {
        try {
            Block block = (Block) super.clone();
            block.runtimeId = this.runtimeId;
            block.identifier = this.identifier;
            block.blockStates = this.blockStates;
            block.blockType = this.blockType;
            block.location = this.location;
            block.layer = this.layer;
            block.blockProperties = this.blockProperties;
            return block;
        } catch ( CloneNotSupportedException e ) {
            throw new AssertionError();
        }
    }
}
