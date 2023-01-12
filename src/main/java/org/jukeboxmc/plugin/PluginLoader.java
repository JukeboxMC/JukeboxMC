package org.jukeboxmc.plugin;

import org.jukeboxmc.logger.Logger;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.yaml.snakeyaml.Yaml;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author WaterdogPE
 * @version 1.0
 */
public class PluginLoader {

    private final Logger logger;
    private final PluginManager pluginManager;

    public PluginLoader( Logger logger, PluginManager pluginManager ) {
        this.logger = logger;
        this.pluginManager = pluginManager;
    }

    protected static boolean isJarFile( Path file ) {
        return file.getFileName().toString().endsWith( ".jar" );
    }

    protected Plugin loadPluginJAR( PluginYAML pluginConfig, File pluginJar ) {
        PluginClassLoader loader;
        try {
            loader = new PluginClassLoader( this.pluginManager, this.getClass().getClassLoader(), pluginJar );
            this.pluginManager.pluginClassLoaders.put( pluginConfig.getName(), loader );
        } catch ( MalformedURLException e ) {
            this.logger.error( "Error while creating class loader(plugin=" + pluginConfig.getName() + ")" + e.getMessage() );
            return null;
        }

        try {
            Class<?> mainClass = loader.loadClass( pluginConfig.getMain() );
            if ( !Plugin.class.isAssignableFrom( mainClass ) ) {
                return null;
            }

            Class<? extends Plugin> castedMain = mainClass.asSubclass( Plugin.class );
            Plugin plugin = castedMain.getDeclaredConstructor().newInstance();
            plugin.init( pluginConfig, this.pluginManager.getServer(), pluginJar );
            return plugin;
        } catch ( Exception e ) {
            this.logger.error( "Error while loading plugin main class(main=" + pluginConfig.getMain() + ",plugin=" + pluginConfig.getName() + ")" + e.getMessage() );
        }
        return null;
    }

    protected PluginYAML loadPluginData( File file, Yaml yaml ) {
        try ( JarFile pluginJar = new JarFile( file ) ) {
            JarEntry configEntry = pluginJar.getJarEntry( "plugin.yml" );
            if ( configEntry == null ) {
                Enumeration<JarEntry> entries = pluginJar.entries();

                PluginYAML pluginYAML = new PluginYAML();
                while ( entries.hasMoreElements() ) {
                    JarEntry entry = entries.nextElement();
                    if ( entry.getName().endsWith( ".class" ) ) {
                        ClassReader classReader = new ClassReader( new DataInputStream( pluginJar.getInputStream( entry ) ) );
                        if ( classReader.getSuperName().equals( "org/jukeboxmc/plugin/Plugin" ) ) {
                            AtomicReference<String> pluginName = new AtomicReference<>();
                            AtomicReference<String> version = new AtomicReference<>();
                            AtomicReference<String> author = new AtomicReference<>();
                            AtomicReference<List<String>> depends = new AtomicReference<>();
                            AtomicReference<String> pluginLoadOrder = new AtomicReference<>(PluginLoadOrder.POSTWORLD.name());
                            classReader.accept( new ClassVisitor( Opcodes.ASM7 ) {
                                @Override
                                public AnnotationVisitor visitAnnotation( String descriptor, boolean visible ) {
                                    switch ( descriptor ) {
                                        case "Lorg/jukeboxmc/plugin/annotation/PluginName;" -> {
                                            return new AnnotationVisitor(Opcodes.ASM7) {
                                                @Override
                                                public void visit(String key, Object value) {
                                                    pluginName.set( (String) value );
                                                }
                                            };
                                        }
                                        case "Lorg/jukeboxmc/plugin/annotation/Version;" -> {
                                            return new AnnotationVisitor(Opcodes.ASM7) {
                                                @Override
                                                public void visit(String key, Object value) {
                                                    version.set( (String) value );
                                                }
                                            };
                                        }
                                        case "Lorg/jukeboxmc/plugin/annotation/Author;" -> {
                                            return new AnnotationVisitor(Opcodes.ASM7) {
                                                @Override
                                                public void visit(String key, Object value) {
                                                    author.set( (String) value );
                                                }
                                            };
                                        }
                                        case "Lorg/jukeboxmc/plugin/annotation/Depends;" -> {
                                            return new AnnotationVisitor(Opcodes.ASM7) {
                                                @Override
                                                public AnnotationVisitor visitArray(String name) {
                                                    depends.set(new ArrayList<>());
                                                    return new AnnotationVisitor(Opcodes.ASM7) {
                                                        @Override
                                                        public void visit(String name, Object value) {
                                                            depends.get().add((String) value);
                                                        }
                                                    };
                                                }
                                            };
                                        }
                                        case "Lorg/jukeboxmc/plugin/annotation/Startup;" -> {
                                            return new AnnotationVisitor(Opcodes.ASM7) {
                                                @Override
                                                public void visitEnum(String name, String descriptor, String value) {
                                                    pluginLoadOrder.set(value);
                                                }
                                            };
                                        }
                                    }
                                    return super.visitAnnotation( descriptor, visible );
                                }
                            }, 0 );

                            pluginYAML.setMain( classReader.getClassName().replace( "/", "." ) );
                            pluginYAML.setName( pluginName.get() );
                            pluginYAML.setVersion( version.get() );
                            pluginYAML.setAuthor( author.get() );
                            pluginYAML.setDepends( depends.get() );
                            pluginYAML.setLoad( PluginLoadOrder.valueOf( pluginLoadOrder.get() ) );
                        }
                    }
                }
                if ( pluginYAML.getName() != null && pluginYAML.getVersion() != null && pluginYAML.getAuthor() != null && pluginYAML.getMain() != null ) {
                    return pluginYAML;
                }
                this.logger.warn( "Jar file " + file.getName() + " doesnt contain a plugin.yml!" );
                return null;
            }

            try ( InputStream fileStream = pluginJar.getInputStream( configEntry ) ) {
                PluginYAML pluginConfig = yaml.loadAs( fileStream, PluginYAML.class );
                if ( pluginConfig.getMain() != null && pluginConfig.getName() != null ) {
                    // Valid plugin.yml, main and name set
                    return pluginConfig;
                }
            }
            this.logger.warn( "Invalid plugin.yml for " + file.getName() + ": main and/or name property missing" );
        } catch ( IOException e ) {
            this.logger.error( "Error while reading plugin directory" + e );
        }
        return null;
    }

}
