package org.jukeboxmc.plugin;

import lombok.ToString;

import java.util.List;

/**
 * @author WaterdogPE
 * @version 1.0
 */
@ToString
public class PluginYAML {

    public String name;
    public String version;
    public String author;
    public String main;
    public PluginLoadOrder load = PluginLoadOrder.POSTWORLD;
    public List<String> authors;
    public List<String> depends;

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor( String author ) {
        this.author = author;
    }

    public String getMain() {
        return this.main;
    }

    public void setMain( String main ) {
        this.main = main;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion( String version ) {
        this.version = version;
    }

    public List<String> getAuthors() {
        return this.authors;
    }

    public void setAuthors( List<String> authors ) {
        this.authors = authors;
    }

    public List<String> getDepends() {
        return this.depends;
    }

    public void setDepends( List<String> depends ) {
        this.depends = depends;
    }

    public PluginLoadOrder getLoadOrder() {
        return this.load;
    }

    public void setLoad( PluginLoadOrder load ) {
        this.load = load;
    }
}
