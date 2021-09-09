package jukeboxmc.console;

import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;
import org.jukeboxmc.Server;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class CommandCompleter implements Completer {

    private final Server server;

    public CommandCompleter( Server server ) {
        this.server = server;
    }

    @Override
    public void complete( LineReader lineReader, ParsedLine parsedLine, List<Candidate> candidates ) {
        //TODO
    }

    private void addOptions( Consumer<String> commandConsumer ) {
       //TODO
    }
}
