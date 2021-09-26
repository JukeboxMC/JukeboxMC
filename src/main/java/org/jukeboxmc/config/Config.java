package org.jukeboxmc.config;

import com.google.gson.*;
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

    @SneakyThrows
    public Config( File path, String fileName, ConfigType configType ) {
        this.configType = configType;
        this.configSection = new ConfigSection();
        this.file = new File( path, fileName );
        if ( !this.file.getParentFile().exists() ) {
            this.file.getParentFile().mkdirs();
        }
        if ( !this.file.exists() ) {
            this.file.createNewFile();
        }

        if ( configType.equals( ConfigType.JSON ) ) {
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
            JsonElement parse = new JsonParser().parse( new FileReader( this.file ) );
            this.configSection = new ConfigSection( this.gson.fromJson( parse, new TypeToken<LinkedHashMap<String, Object>>() {
            }.getType() ) );
        } else if ( configType.equals( ConfigType.YAML ) ) {
            DumperOptions dumperOptions = new DumperOptions();
            dumperOptions.setDefaultFlowStyle( DumperOptions.FlowStyle.BLOCK );
            this.yaml = new Yaml( dumperOptions );
            this.configSection = new ConfigSection( this.yaml.loadAs( new FileReader( this.file ), LinkedHashMap.class ) );
        } else if ( configType.equals( ConfigType.PROPERTIES ) ) {
            this.properties = new Properties();
            this.properties.load( new BufferedReader( new InputStreamReader( new FileInputStream( this.file ) ) ) );
        }
    }

    public boolean exists( String key ) {
        switch ( this.configType ) {
            case JSON:
            case YAML:
                return this.configSection.exists( key );
            case PROPERTIES:
                return this.properties.getProperty( key ) != null;
        }
        return false;
    }

    public void set( String key, Object object ) {
        switch ( this.configType ) {
            case JSON:
            case YAML:
                this.configSection.set( key, object );
                break;
            case PROPERTIES:
                this.properties.setProperty( key, String.valueOf( object ) );
                break;
            default:
                break;
        }
    }

    public void remove( String key ) {
        switch ( this.configType ) {
            case JSON:
            case YAML:
                this.configSection.remove( key );
                this.save();
                break;
            case PROPERTIES:
                this.properties.remove( key );
                this.save();
                break;
        }
    }

    public void addDefault( String key, String value ) {
        if ( !this.exists( key ) ) {
            this.set( key, value );
            this.save();
        }
    }

    public Object get( String key ) {
        switch ( this.configType ) {
            case JSON:
            case YAML:
                return this.configSection.get( key );
            case PROPERTIES:
                return this.properties.getProperty( key );
        }
        return null;
    }

    public List<String> getStringList( String key ) {
        return this.configSection.getStringList( key );
    }

    public String getString( String key ) {
        return this.configSection.getString( key );
    }

    public void addDefault( String key, int value ) {
        if ( !this.exists( key ) ) {
            this.set( key, value );
            this.save();
        }
    }

    public List<Integer> getIntegerList( String key ) {
        return this.configSection.getIntegerList( key );
    }

    public int getInt( String key ) {
        return this.configSection.getInt( key );
    }

    public void addDefault( String key, long value ) {
        if ( !this.exists( key ) ) {
            this.set( key, value );
            this.save();
        }
    }

    public List<Long> getLongList( String key ) {
        return this.configSection.getLongList( key );
    }

    public long getLong( String key ) {
        return this.configSection.getLong( key );
    }

    public void addDefault( String key, double value ) {
        if ( !this.exists( key ) ) {
            this.set( key, value );
            this.save();
        }
    }

    public List<Double> getDoubleList( String key ) {
        return this.configSection.getDoubleList( key );
    }

    public double getDouble( String key ) {
        return this.configSection.getDouble( key );
    }

    public void addDefault( String key, float value ) {
        if ( !this.exists( key ) ) {
            this.set( key, value );
            this.save();
        }
    }

    public List<Float> getFloatList( String key ) {
        return this.configSection.getFloatList( key );
    }

    public float getFloat( String key ) {
        return this.configSection.getFloat( key );
    }

    public void addDefault( String key, byte value ) {
        if ( !this.exists( key ) ) {
            this.set( key, value );
            this.save();
        }
    }

    public List<Byte> getByteList( String key ) {
        return this.configSection.getByteList( key );
    }

    public byte getByte( String key ) {
        return this.configSection.getByte( key );
    }

    public void addDefault( String key, short value ) {
        if ( !this.exists( key ) ) {
            this.set( key, value );
            this.save();
        }
    }

    public List<Short> getShortList( String key ) {
        return this.configSection.getShortList( key );
    }

    public short getShort( String key ) {
        return this.configSection.getShort( key );
    }

    public void addDefault( String key, boolean value ) {
        if ( !this.exists( key ) ) {
            this.set( key, value );
            this.save();
        }
    }

    public List<Boolean> getBooleanList( String key ) {
        return this.configSection.getBooleanList( key );
    }

    public boolean getBoolean( String key ) {
        return this.configSection.getBoolean( key );
    }

    public Map<String, Object> getMap( String key ) {
        if ( this.configSection.containsKey( key ) && this.get( key ) instanceof Map ) {
            return (Map<String, Object>) this.get( key );
        }
        return new HashMap<>();
    }

    public Set<String> getKeys() {
        return this.configSection.keySet();
    }

    public Collection<Object> getValues() {
        return this.configSection.values();
    }

    public void save() {
        try ( FileWriter fileWriter = new FileWriter( this.file ) ) {
            switch ( this.configType ) {
                case JSON:
                    fileWriter.write( this.gson.toJson( this.configSection ) );
                    break;
                case YAML:
                    fileWriter.write( this.yaml.dump( this.configSection ) );
                    break;
                case PROPERTIES:
                    this.properties.store( fileWriter, "" );
                    break;
                default:
                    break;
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

}
