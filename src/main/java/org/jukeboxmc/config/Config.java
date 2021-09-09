package org.jukeboxmc.config;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Config {

    private final Gson gson;
    private final File file;

    private final LinkedHashMap<String, Object> configMap;

    @SneakyThrows
    public Config( File path, String fileName ) {
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
        this.file = new File( path, fileName );

        if ( !this.file.getParentFile().exists() ) {
            this.file.getParentFile().mkdirs();
        }
        if ( !this.file.exists() ) {
            this.file.createNewFile();
        }

        JsonElement parse = new JsonParser().parse( new FileReader( this.file ) );
        LinkedHashMap<String, Object> configMap = this.gson.fromJson( parse, new TypeToken<LinkedHashMap<String, Object>>() {
        }.getType() );
        this.configMap = configMap != null ? configMap : new LinkedHashMap<>();
    }

    public boolean exists( String key ) {
        return this.configMap.containsKey( key );
    }

    public void set( String key, Object object ) {
        this.configMap.put( key, object );
    }

    public void remove( String key ) {
        this.configMap.remove( key );
        this.save();
    }

    public void addDefault( String key, String value ) {
        if ( !this.configMap.containsKey( key ) ) {
            this.configMap.put( key, value );
            this.save();
        }
    }

    public List<String> getStringList( String key ) {
        return (List<String>) this.configMap.get( key );
    }

    public String getString( String key ) {
        return (String) this.configMap.get( key );
    }

    public void addDefault( String key, int value ) {
        if ( !this.configMap.containsKey( key ) ) {
            this.configMap.put( key, value );
            this.save();
        }
    }

    public List<Integer> getIntegerList( String key ) {
        return (List<Integer>) this.configMap.get( key );
    }

    public int getInt( String key ) {
        return ( (Number) this.configMap.get( key ) ).intValue();
    }

    public void addDefault( String key, long value ) {
        if ( !this.configMap.containsKey( key ) ) {
            this.configMap.put( key, value );
            this.save();
        }
    }

    public List<Long> getLongList( String key ) {
        return (List<Long>) this.configMap.get( key );
    }

    public long getLong( String key ) {
        return ( (Number) this.configMap.get( key ) ).longValue();
    }

    public void addDefault( String key, double value ) {
        if ( !this.configMap.containsKey( key ) ) {
            this.configMap.put( key, value );
            this.save();
        }
    }

    public List<Double> getDoubleList( String key ) {
        return (List<Double>) this.configMap.get( key );
    }

    public double getDouble( String key ) {
        return ( (Number) this.configMap.get( key ) ).doubleValue();
    }

    public void addDefault( String key, float value ) {
        if ( !this.configMap.containsKey( key ) ) {
            this.configMap.put( key, value );
            this.save();
        }
    }

    public List<Float> getFloatList( String key ) {
        return (List<Float>) this.configMap.get( key );
    }

    public float getFloat( String key ) {
        return ( (Number) this.configMap.get( key ) ).floatValue();
    }

    public void addDefault( String key, byte value ) {
        if ( !this.configMap.containsKey( key ) ) {
            this.configMap.put( key, value );
            this.save();
        }
    }

    public List<Byte> getByteList( String key ) {
        return (List<Byte>) this.configMap.get( key );
    }

    public byte getByte( String key ) {
        return ( (Number) this.configMap.get( key ) ).byteValue();
    }

    public void addDefault( String key, short value ) {
        if ( !this.configMap.containsKey( key ) ) {
            this.configMap.put( key, value );
            this.save();
        }
    }

    public List<Short> getShortList( String key ) {
        return (List<Short>) this.configMap.get( key );
    }

    public short getShort( String key ) {
        return ( (Number) this.configMap.get( key ) ).shortValue();
    }

    public void addDefault( String key, boolean value ) {
        if ( !this.configMap.containsKey( key ) ) {
            this.configMap.put( key, value );
            this.save();
        }
    }

    public List<Boolean> getBooleanList( String key ) {
        return (List<Boolean>) this.configMap.get( key );
    }

    public boolean getBoolean( String key ) {
        return (boolean) this.configMap.get( key );
    }

    public Map<String, Object> getMap( String key ) {
        if ( this.configMap.containsKey( key ) && this.configMap.get( key ) instanceof Map ) {
            return (Map<String, Object>) this.configMap.get( key );
        }
        return new HashMap<>();
    }

    public Set<String> getKeys() {
        return this.configMap.keySet();
    }

    public Collection<Object> getValues() {
        return this.configMap.values();
    }

    public void save() {
        try {
            FileWriter fileWriter = new FileWriter( this.file );
            fileWriter.write( this.gson.toJson( this.configMap ) );
            fileWriter.flush();
            fileWriter.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

}
