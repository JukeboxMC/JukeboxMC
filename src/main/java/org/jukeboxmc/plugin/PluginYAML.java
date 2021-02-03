package org.jukeboxmc.plugin;

import java.util.List;

/**
 * @author WaterdogPE
 * @version 1.0
 */
public class PluginYAML {

    public String name;
    public String version;
    public String author;
    public String main;

    public List<String> depends;

    public String getAuthor() {
        return this.author;
    }

    public String getMain() {
        return this.main;
    }

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }

    public List<String> getDepends() {
        return this.depends;
    }

}
