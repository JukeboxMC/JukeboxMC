package org.jukeboxmc.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Config {

    private final ConfigType configType;
    private ConfigSection configSection;
    private Gson gson;
    private Yaml yaml;
    private Properties properties;

    private final File file;
    private final InputStream inputStream;

    @SneakyThrows
    public Config( File file, ConfigType configType ) {
        this.file = file;
        this.inputStream = null;
        this.configType = configType;
        this.configSection = new ConfigSection();
        if ( !this.file.exists() ) {
            this.file.createNewFile();
        }
        this.load();
    }

    @SneakyThrows
    public Config( InputStream inputStream, ConfigType configType ) {
        this.file = null;
        this.inputStream = inputStream;
        this.configType = configType;
        this.configSection = new ConfigSection();
        this.load();
    }

    @SneakyThrows
    public void load() {
        try ( InputStreamReader reader = new InputStreamReader( this.file == null ? this.inputStream : new FileInputStream( this.file ) ) ) {
            switch ( this.configType ) {
                case JSON -> {
                    GsonBuilder gson = new GsonBuilder();
                    gson.setPrettyPrinting();
                    gson.registerTypeAdapter( Double.class, (JsonSerializer<Double>) ( src, typeOfSrc, context ) -> {
                        if ( src == src.intValue() ) {
                            return new JsonPrimitive( src.intValue() );
                        } else if ( src == src.longValue() ) {
                            return new JsonPrimitive( src.longValue() );
                        }
                        return new JsonPrimitive( src );
                    } );
                    this.gson = gson.create();
                    this.configSection = new ConfigSection( this.gson.fromJson( reader, new TypeToken<LinkedHashMap<String, Object>>() {
                    }.getType() ) );
                }
                case YAML -> {
                    DumperOptions dumperOptions = new DumperOptions();
                    dumperOptions.setDefaultFlowStyle( DumperOptions.FlowStyle.BLOCK );
                    this.yaml = new Yaml( dumperOptions );
                    this.configSection = new ConfigSection( this.yaml.loadAs( reader, LinkedHashMap.class ) );
                }
                case PROPERTIES -> {
                    this.properties = new Properties();
                    this.properties.load( reader );
                }
            }
        }
    }

    public boolean exists( String key ) {
        return switch ( this.configType ) {
            case JSON, YAML -> this.configSection.exists( key );
            case PROPERTIES -> this.properties.getProperty( key ) != null;
        };
    }

    public void set( String key, Object object ) {
        switch ( this.configType ) {
            case JSON, YAML -> this.configSection.set( key, object );
            case PROPERTIES -> this.properties.setProperty( key, String.valueOf( object ) );
        }
    }

    public void remove( String key ) {
        switch ( this.configType ) {
            case JSON, YAML -> {
                this.configSection.remove( key );
                this.save();
            }
            case PROPERTIES -> {
                this.properties.remove( key );
                this.save();
            }
        }
    }

    public void addDefault( String key, Object value ) {
        if ( !this.exists( key ) ) {
            this.set( key, value );
            this.save();
        }
    }

    public Object get( String key ) {
        return switch ( this.configType ) {
            case JSON, YAML -> this.configSection.get( key );
            case PROPERTIES -> this.properties.getProperty( key );
        };
    }

    public List<String> getStringList( String key ) {
        return this.configSection.getStringList( key );
    }

    public String getString( String key ) {
        return this.configSection.getString( key );
    }

    public List<Integer> getIntegerList( String key ) {
        return this.configSection.getIntegerList( key );
    }

    public int getInt( String key ) {
        return this.configSection.getInt( key );
    }

    public List<Long> getLongList( String key ) {
        return this.configSection.getLongList( key );
    }

    public long getLong( String key ) {
        return this.configSection.getLong( key );
    }

    public List<Double> getDoubleList( String key ) {
        return this.configSection.getDoubleList( key );
    }

    public double getDouble( String key ) {
        return this.configSection.getDouble( key );
    }

    public List<Float> getFloatList( String key ) {
        return this.configSection.getFloatList( key );
    }

    public float getFloat( String key ) {
        return this.configSection.getFloat( key );
    }

    public List<Byte> getByteList( String key ) {
        return this.configSection.getByteList( key );
    }

    public byte getByte( String key ) {
        return this.configSection.getByte( key );
    }

    public List<Short> getShortList( String key ) {
        return this.configSection.getShortList( key );
    }

    public short getShort( String key ) {
        return this.configSection.getShort( key );
    }

    public List<Boolean> getBooleanList( String key ) {
        return this.configSection.getBooleanList( key );
    }

    public boolean getBoolean( String key ) {
        return this.configSection.getBoolean( key );
    }

    public Map<String, Object> getMap() {
        return this.configSection;
    }

    public Set<String> getKeys() {
        return this.configSection.keySet();
    }

    public Collection<Object> getValues() {
        return this.configSection.values();
    }

    public ConfigSection getConfigSection() {
        return configSection;
    }

    public String toJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.enableDefaultTyping().writeValueAsString( this );
        } catch ( JsonProcessingException e ) {
            throw new RuntimeException( e );
        }
    }

    @SneakyThrows
    public void save() {
        if ( this.file == null ) {
            throw new IOException( "This config can not be saved!" );
        }

        if ( !this.file.getParentFile().exists() ) {
            this.file.getParentFile().mkdirs();
        }

        try ( OutputStreamWriter writer = new OutputStreamWriter( new FileOutputStream( this.file ) ) ) {
            switch ( this.configType ) {
                case JSON -> this.gson.toJson( this.configSection, writer );
                case YAML -> this.yaml.dump( this.configSection, writer );
                case PROPERTIES -> this.properties.store( writer, "" );
            }
        }
    }

}
