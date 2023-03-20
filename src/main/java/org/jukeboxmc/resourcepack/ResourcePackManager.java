package org.jukeboxmc.resourcepack;

import com.google.common.io.Files;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.math3.util.FastMath;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Kaooot
 * @version 1.0
 */
public class ResourcePackManager {

    private final Map<String, ResourcePack> resourcePacks = new HashMap<>();

    private final Logger logger;

    public ResourcePackManager( Logger logger ) {
        this.logger = logger;
    }

    public void loadResourcePacks() {
        File resourcePacksPath = new File( System.getProperty( "user.dir" ) + "/resource_packs" );

        if ( !resourcePacksPath.exists() ) {
            resourcePacksPath.mkdirs();
        }

        if ( !resourcePacksPath.isDirectory() ) {
            return;
        }

        for ( File file : Objects.requireNonNull( resourcePacksPath.listFiles() ) ) {
            if ( !file.isDirectory() ) {
                final String fileEnding = Files.getFileExtension( file.getName() );

                if ( fileEnding.equalsIgnoreCase( "zip" ) || fileEnding.equalsIgnoreCase( "mcpack" ) ) {
                    try ( ZipFile zipFile = new ZipFile( file ) ) {
                        String manifestFileName = "manifest.json";
                        ZipEntry manifestEntry = zipFile.getEntry( manifestFileName );

                        // due to the mcpack file extension
                        if ( manifestEntry == null ) {
                            manifestEntry = zipFile.stream().filter( zipEntry -> !zipEntry.isDirectory() && zipEntry.getName().toLowerCase().endsWith( manifestFileName ) ).filter( zipEntry -> {
                                File zipEntryFile = new File( zipEntry.getName() );

                                if ( !zipEntryFile.getName().equalsIgnoreCase( manifestFileName ) ) {
                                    return false;
                                }

                                return zipEntryFile.getParent() == null || zipEntryFile.getParentFile().getParent() == null;
                            } ).findFirst().orElseThrow( () ->
                                    new IllegalArgumentException( "The " + manifestFileName + " file could not be found" ) ); }

                        JsonObject manifest = new JsonParser()
                                .parse( new InputStreamReader( zipFile.getInputStream( manifestEntry ),
                                        StandardCharsets.UTF_8 ) ).getAsJsonObject();

                        if ( !this.isManifestValid( manifest ) ) {
                            throw new IllegalArgumentException( "The " + manifestFileName + " file is invalid" );
                        }

                        JsonObject manifestHeader = manifest.getAsJsonObject( "header" );
                        String resourcePackName = manifestHeader.get( "name" ).getAsString();
                        String resourcePackUuid = manifestHeader.get( "uuid" ).getAsString();
                        JsonArray resourcePackVersionArray = manifestHeader.get( "version" ).getAsJsonArray();
                        String resourcePackVersion = resourcePackVersionArray.toString()
                                .replace( "[", "" ).replace( "]", "" )
                                .replaceAll( ",", "." );
                        int resourcePackSize = FastMath.toIntExact( file.length() );
                        byte[] resourcePackSha256 = MessageDigest.getInstance( "SHA-256" )
                                .digest( java.nio.file.Files.readAllBytes( file.toPath() ) );

                        this.logger.info( "Read resource pack " + resourcePackName + " §rsuccessful from " + file.getName() );

                        final ResourcePack resourcePack = new ResourcePack( file, resourcePackName, resourcePackUuid,
                                resourcePackVersion, resourcePackSize, resourcePackSha256, new byte[0] );

                        this.resourcePacks.put( resourcePackUuid, resourcePack );
                    } catch ( final IOException | NoSuchAlgorithmException e ) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public ResourcePack retrieveResourcePackById( final String uuid ) {
        return this.resourcePacks.get( uuid );
    }

    public @NotNull Collection<ResourcePack> retrieveResourcePacks() {
        return this.resourcePacks.values();
    }

    private boolean isManifestValid( final @NotNull JsonObject manifest ) {
        if ( manifest.has( "format_version" ) && manifest.has( "header" ) &&
                manifest.has( "modules" ) ) {
            final JsonObject manifestHeader = manifest.get( "header" ).getAsJsonObject();

            if ( manifestHeader.has( "description" ) && manifestHeader.has( "name" ) &&
                    manifestHeader.has( "uuid" ) && manifestHeader.has( "version" ) ) {
                final JsonArray headerVersion = manifestHeader.getAsJsonArray( "version" );

                return headerVersion.size() == 3;
            }
        }

        return false;
    }
}